package exterminatorJeff.undergroundBiomes.common;

import Zeno410Utils.Acceptor;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.WorldEvent;


import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;

import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBDimensionalStrataColumnProvider;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;
import exterminatorJeff.undergroundBiomes.worldGen.UBBlockProvider;

import exterminatorJeff.undergroundBiomes.constructs.util.Consumable;
import exterminatorJeff.undergroundBiomes.constructs.util.DimensionSet;
import Zeno410Utils.Zeno410Logger;

import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;
import exterminatorJeff.undergroundBiomes.worldGen.UndergroundBiomeSet;
import java.util.logging.Logger;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
/**
 *
 * @author Zeno410
 */
public class DimensionManager {

    public static Logger logger = new Zeno410Logger("DimensionManager").logger();

    private Map<Integer,WorldGenManager> worldGenManagers = new HashMap<Integer,WorldGenManager>();

    private boolean serverAdjusted  = false;

    private boolean dimensionSpecificSeeds = false;

    private boolean inChunkGeneration;
    public boolean inChunkGeneration() {return inChunkGeneration;}
    private Acceptor<Boolean> inChunkGeneratorFollower = new Acceptor<Boolean>() {

        @Override
        public void accept(Boolean accepted) {
            inChunkGeneration = accepted;
            logger.info("Dimensional in-chunk "+accepted);
        }

    };

    private final DimensionSet.Include includeDimensionIDs;
    private final DimensionSet.Exclude excludeDimensionIDs;
    private final Consumable<String> includeDimensions;
    private final Consumable<String> excludeDimensions;

    private final DimensionSet.Include inChunkGenerationIncludeIDs;
    private final DimensionSet.Exclude inChunkGenerationExcludeIDs;
    private final Consumable<String> inChunkGenerationInclude;
    private final Consumable<String> inChunkGenerationExclude;

    private List<Integer> inChunkDimensionIDs = new ArrayList<Integer>();

    private UndergroundBiomeSet cachedBiomeSet;
    private UndergroundBiomeSet biomeSet() {
        if (cachedBiomeSet == null) cachedBiomeSet = new UndergroundBiomeSet();
        return cachedBiomeSet;
    }

    private final OreUBifier oreUBifier;

    private WorldGenManager villageWorldGenManager;  // has to be cached because not availabe for block call

    public long dimensionSeed(int dimension) {
        if (dimensionSpecificSeeds) return UndergroundBiomes.worldSeed + dimension*100;
        return UndergroundBiomes.worldSeed;
    }
    public WorldGenManager worldGenManager(int dimension) {
        WorldGenManager result = worldGenManagers.get(dimension);
        if (result == null) {
            // hasn't been made yet; make and store it
            oreUBifier.renewBlockReplacers();
            result = new WorldGenManager(dimensionSeed(dimension),dimension,oreUBifier,biomeSet());
            worldGenManagers.put(dimension, result);
        }
        return result;
    }

    public DimensionManager(UndergroundBiomesSettings settings,OreUBifier oreUBifier) {
        dimensionSpecificSeeds = settings.dimensionSpecificSeeds.value();

        inChunkGeneration = settings.inChunkGeneration.value();
        settings.inChunkGeneration.informOnChange(inChunkGeneratorFollower);

        excludeDimensions = new Consumable<String>(settings.excludeDimensions.value());
        includeDimensions = new Consumable<String>(settings.includeDimensions.value());

        includeDimensionIDs = new DimensionSet.Include(includeDimensions.use());
        excludeDimensionIDs = new DimensionSet.Exclude(excludeDimensions.use());

        inChunkGenerationExclude = new Consumable<String>(settings.inChunkGenerationExclude.value());
        inChunkGenerationInclude = new Consumable<String>(settings.inChunkGenerationInclude.value());

        inChunkGenerationIncludeIDs = new DimensionSet.Include(inChunkGenerationInclude.use());
        inChunkGenerationExcludeIDs = new DimensionSet.Exclude(inChunkGenerationExclude.use());

        // set API hooks

        UBAPIHook.ubAPIHook.dimensionalStrataColumnProvider = new StrataColumnProvider();
        this.oreUBifier = oreUBifier;
    }

    public void onBiomeDecorate(DecorateBiomeEvent.Post event)    {
        // this is the method to change stones if inChunkGeneration is off
        // onWorldLoad modifies existing ChunkProviderGenerator if it is on
        
        //skip if UB is off
        if (UndergroundBiomes.instance().settings().ubActive.value() == false) return;
        int id = event.world.provider.dimensionId;


        // do nothing if we're not supposed to UB this dimension
        if (!includeDimensionIDs.isIncluded(id, excludeDimensionIDs)) return;

        // Sometimes can get called before onWorldLoad, wtf?
        WorldGenManager worldGen = worldGenManager(id);
        if (worldGen == null) {
            System.out.println("UndergroundBiomes warning: onBiomeDecorate before onWorldLoad! Ignoring.");
            return;
        }
        //logger.info("decorating dimension "+ id );
        
                // do nothing if being handled by in-chunk method
        if (inChunkDimensionIDs.contains(id)) {
            worldGen.decorateIfNeeded(event);
        } else {
            worldGen.onBiomeDecorate(event);
        }
    }


    public void setupGenerators(WorldEvent.Load event) {

        int id;
        try {
            MinecraftServer server = MinecraftServer.getServer();
            if (server == null) return;
            if (serverAdjusted) return;
            // nothing if off
            WorldServer [] serverList = server.worldServers;
            for (int i = 0 ; i < serverList.length; i++) {
                //logger.info("server "+i);
                if (serverList[i] == null) continue;

                id = serverList[i].provider.dimensionId;
                WorldGenManager worldGen = worldGenManager(id);
                //logger.info("adjusting dimension "+ id + " " +serverList[i].toString());
                //logger.info(worldGen.chunkProvider(serverList[i]).toString());
                //logger.info(worldGen.servedChunkProvider(serverList[i]).toString());

                // do nothing if we're not supposed to UB this dimension
                if (!includeDimensionIDs.isIncluded(id, excludeDimensionIDs)) continue;

                //serverList[i].getGameRules().setOrCreateGameRule("mobGriefing", "true");
                logger.info("UB dimension setup "+inChunkGeneration);
                // do nothing if we're not supposed to use inchunk generation in this dimension
                if (!inChunkGeneration()) return;
                if (!inChunkGenerationIncludeIDs.isIncluded(id, inChunkGenerationExcludeIDs)) continue;

                //logger.info("setting chunk provider for "+serverList[i].toString());

                serverList[i].provider.worldChunkMgr.cleanupCache();
                worldGen.setChunkProvider(serverList[i]);
                worldGen.setServedChunkProvider(serverList[i]);
                this.inChunkDimensionIDs.add(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         serverAdjusted = true;
     }

    public void unload() {
        // clears for a world unload
        inChunkDimensionIDs = new ArrayList<Integer>();
        serverAdjusted = false;
        worldGenManagers = new HashMap<Integer,WorldGenManager>();
        cachedBiomeSet = null;
    }

    public boolean inChunkGenerationAllowed(int id) {
        if (!inChunkGeneration()) return false;

        // no if we're not supposed to UB this dimension
        if (!includeDimensionIDs.isIncluded(id, excludeDimensionIDs)) return false;

        // no if we're not supposed to use inchunk generation in this dimension
        if (!inChunkGenerationIncludeIDs.isIncluded(id, inChunkGenerationExcludeIDs)) return false;

        return true;
    }

    // this is not affected by whether inChunkGeneration is on
    @SubscribeEvent
    public void preBiomeDecorate(DecorateBiomeEvent.Pre event) {
        // this currently just saves the filler block to the VillageStoneChanger
        int id = event.world.provider.dimensionId;
        villageWorldGenManager = worldGenManager(id);
        if (UndergroundBiomes.instance().gotWorldSeed()) {
            if (UndergroundBiomes.replaceCobblestone()) villageWorldGenManager.preBiomeDecorate(event);
        }
    }

    @SubscribeEvent
    public void prePopulateChunk(PopulateChunkEvent.Pre event) {
        // this currently just saves the filler block to the VillageStoneChanger
        int id = event.world.provider.dimensionId;
        villageWorldGenManager = worldGenManager(id);
        if (UndergroundBiomes.instance().gotWorldSeed()) {
            if (UndergroundBiomes.replaceCobblestone()) villageWorldGenManager.prePopulateChunk(event);
        }
    }

    @SubscribeEvent
    public void onVillageSelectBlock(GetVillageBlockID e){
        if (villageWorldGenManager == null) return;
        if (UndergroundBiomes.instance().gotWorldSeed()) villageWorldGenManager.onVillageSelectBlock(e);
    }

    @SubscribeEvent
    public void onVillageSelectMeta(GetVillageBlockMeta e){
        if (villageWorldGenManager == null) return;
        if (UndergroundBiomes.instance().gotWorldSeed()) villageWorldGenManager.onVillageSelectMeta(e);
    }

    private class StrataColumnProvider implements UBDimensionalStrataColumnProvider {
        public UBStrataColumnProvider ubStrataColumnProvider(int dimension) {
            return new UBBlockProvider(worldGenManager(dimension));
        }
    }

    public void redoOres(int x, int z, World world) {
        WorldGenManager worldGenManager = worldGenManagers.get(world.provider.dimensionId);
        if (worldGenManager != null) {
            worldGenManager.redoOres(x, z, world);
        } else UndergroundBiomes.logger.info("no manager for "+world.toString());
    }

}
