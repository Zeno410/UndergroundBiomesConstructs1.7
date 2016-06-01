package exterminatorJeff.undergroundBiomes.common;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import Zeno410Utils.PlaneLocation;
import Zeno410Utils.Accessor;
import Zeno410Utils.Bomb;
import exterminatorJeff.undergroundBiomes.api.BiomeGenUndergroundBase;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundCacheBlock;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator;
import exterminatorJeff.undergroundBiomes.worldGen.GenLayerUnderground;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;
import exterminatorJeff.undergroundBiomes.worldGen.UBChunkProvider;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomeSet;
import exterminatorJeff.undergroundBiomes.worldGen.VillageStoneChanger;


import Zeno410Utils.Zeno410Logger;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;
import java.util.logging.Logger;

import java.lang.reflect.*;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class WorldGenManager {

    public static Logger logger = new Zeno410Logger("WorldGenManager").logger();

    public final int dimension;

    private GenLayer genUndergroundBiomes;

    private GenLayer undergroundBiomeIndexLayer;

    private UBiomeCache biomeCache;

    private final VillageStoneChanger villageStoneChanger;

    private final BiomeUndergroundDecorator villageStoneSource;

    private AccessChunkProvider accessChunkProvider = new AccessChunkProvider();

    private AccessChunkProviderServer accessChunkProviderServer = new AccessChunkProviderServer();

    private Accessor<ChunkProviderServer,IChunkProvider> providerFromChunkServer =
            new Accessor<ChunkProviderServer,IChunkProvider>(IChunkProvider.class);

    private long seed;

    private HashSet<PlaneLocation> alreadyGenerated = new HashSet<PlaneLocation>();

    private IChunkProvider chunkProvider;

    private UndergroundBiomeSet biomeSet;

    public boolean ubOn() {return ubOn;}

    private final boolean ubOn;

    public WorldGenManager(long par1, int _dimension,OreUBifier oreUBifier, UndergroundBiomeSet biomeSet, boolean ubOn){

        dimension = _dimension;
        seed = par1;

        this.biomeCache = new UBiomeCache(this);
        this.biomeSet = biomeSet;

        GenLayer[] gen = GenLayerUnderground.initializeAllBiomeGenerators(par1,UndergroundBiomes.biomeSize(),biomeSet);
        this.genUndergroundBiomes = gen[0];
        this.undergroundBiomeIndexLayer = gen[1];

        while(!arrayHasValues(this.undergroundBiomeIndexLayer.getInts(0, 0, 16, 16))){
            gen = GenLayerUnderground.initializeAllBiomeGenerators(seed++, UndergroundBiomes.biomeSize(),biomeSet);
            this.genUndergroundBiomes = gen[0];
            this.undergroundBiomeIndexLayer = gen[1];
        }

        // examine gen layer
        /*try {
          new AccessGenLayer().report(undergroundBiomeIndexLayer);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
         */

        villageStoneSource = new BiomeUndergroundDecorator(this,oreUBifier);
        villageStoneChanger = new VillageStoneChanger();

        this.ubOn = ubOn;

    }

    public boolean inChunkGenerationAllowed() {
        return UndergroundBiomes.instance().inChunkGenerationAllowed(dimension);
    }

    public void preBiomeDecorate(DecorateBiomeEvent.Pre event) {
        if (this.ubOn() == false) return;

        BiomeGenUndergroundBase[] undergroundBiomesForGeneration = null;
        undergroundBiomesForGeneration = loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, event.chunkX,event.chunkZ, 16, 16);
        villageStoneChanger.setStoneCode(
                getUndergroundBiomeGenAt(event.chunkX, event.chunkZ).fillerBlockCodes);
    }

    public void prePopulateChunk(PopulateChunkEvent.Pre event) {
        if (this.ubOn() == false) return;

        BiomeGenUndergroundBase[] undergroundBiomesForGeneration = null;
        undergroundBiomesForGeneration = loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, event.chunkX,event.chunkZ, 16, 16);
        villageStoneChanger.setStoneCode(
                getUndergroundBiomeGenAt(event.chunkX, event.chunkZ).fillerBlockCodes);
    }

    public void onVillageSelectBlock(GetVillageBlockID e){
        villageStoneChanger.onVillageSelectBlock(e);
    }

    public void onVillageSelectMeta(GetVillageBlockMeta e){
        villageStoneChanger.onVillageSelectMeta(e);
    }

    public void onBiomeDecorate(DecorateBiomeEvent.Post event){
        this.villageStoneSource.decorate(event.world, event.rand, event.chunkX, event.chunkZ);
    }

    public void onBiomeDecorate(PopulateChunkEvent.Post event){
        this.villageStoneSource.decorate(event.world, event.rand, event.chunkX*16, event.chunkZ*16);
    }

    public void onBiomeReplaceOres(DecorateBiomeEvent.Post event){
        this.villageStoneSource.replaceChunkOres(event.chunkX, event.chunkZ,event.world);
        this.villageStoneSource.doRedos(event.world);
    }

    public void redoOres(int x, int z, World world){
        this.villageStoneSource.replaceChunkOres(x, z,world);
        this.villageStoneSource.doRedos(world);
    }

    public BiomeGenUndergroundBase getUndergroundBiomeGenAt(int par1, int par2)
    {
        return this.biomeCache.getUndergroundBiomeGetAt(par1, par2);
    }

    public BiomeUndergroundCacheBlock chunkBiomeCache(int xPosition, int zPosition) {
        return biomeCache.getUndergroundBiomeCacheBlock(xPosition, zPosition);
    }

    public BiomeGenUndergroundBase[] loadUndergroundBlockGeneratorData(BiomeGenUndergroundBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        return this.getUndergroundBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
    }

    public BiomeGenUndergroundBase[] getUndergroundBiomeGenAt(BiomeGenUndergroundBase[] biomesArrayPar, int par2, int par3, int par4, int par5)
    {
            IntCache.resetIntCache();

            BiomeGenUndergroundBase[] biomesArray = biomesArrayPar;

            if (biomesArray == null || biomesArray.length < par4 * par5)
            {
                biomesArray = new BiomeGenUndergroundBase[par4 * par5];
            }

            if (par4 == 16 && par5 == 16 ){ //&& (par2 & 15) == 0 && (par3 & 15) == 0){

                BiomeGenUndergroundBase[] var9 = this.biomeCache.getCachedUndergroundBiomes(par2, par3);
                System.arraycopy(var9, 0, biomesArray, 0, par4 * par5);

                return biomesArray;

            }else{
                throw new RuntimeException();
        }

    }

    public BiomeGenUndergroundBase[] cacheUndergroundBiomeGenAt(BiomeGenUndergroundBase[] biomesArrayPar, int par2, int par3, int par4, int par5){
            BiomeGenUndergroundBase[] biomesArray = biomesArrayPar;

            if (biomesArray == null || biomesArray.length < par4 * par5){
                IntCache.resetIntCache();
                biomesArray = new BiomeGenUndergroundBase[par4 * par5];
            }
            int[] var7 = this.undergroundBiomeIndexLayer.getInts(par2, par3, par4, par5);
                for (int var8 = 0; var8 < par4 * par5; ++var8){
                    biomesArray[var8] = biomeSet.biomeList[var7[var8]];
                    //if (biomesArray[var8].strata[0]==null) throw new RuntimeException();
                }
           return biomesArray;
    }

    public void setGenerated(int x, int z) {
        this.alreadyGenerated.add(new PlaneLocation(x,z));
    }

    public void decorateIfNeeded(DecorateBiomeEvent.Post event) {
        PlaneLocation target = new PlaneLocation(event.chunkX/16, event.chunkZ/16);
        if (alreadyGenerated.contains(target))  {
             // take it out of the generated list
            alreadyGenerated.remove(target);
            //this.onBiomeReplaceOres(event);
        } else {
            // needs generation
           onBiomeDecorate(event);
        }
    }

    public void decorateIfNeeded(PopulateChunkEvent.Post event) {
        PlaneLocation target = new PlaneLocation(event.chunkX, event.chunkZ);
        if (alreadyGenerated.contains(target))  {
             // take it out of the generated list
            alreadyGenerated.remove(target);
            //this.onBiomeReplaceOres(event);
        } else {
            // needs generation
           onBiomeDecorate(event);
        }
    }

    private boolean arrayHasValues(int[] ints){
        for(int i : ints){
            if(i != 0){
                return true;
            }
        }
        return false;
    }

    public ChunkPosition findUndergroundBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
    {
        IntCache.resetIntCache();
        int var6 = par1 - par3 >> 2;
        int var7 = par2 - par3 >> 2;
        int var8 = par1 + par3 >> 2;
        int var9 = par2 + par3 >> 2;
        int var10 = var8 - var6 + 1;
        int var11 = var9 - var7 + 1;
        int[] var12 = this.genUndergroundBiomes.getInts(var6, var7, var10, var11);
        ChunkPosition var13 = null;
        int var14 = 0;

        for (int var15 = 0; var15 < var12.length; ++var15)
        {
            int var16 = var6 + var15 % var10 << 2;
            int var17 = var7 + var15 / var10 << 2;
            BiomeGenUndergroundBase var18 = biomeSet.biomeList[var12[var15]];

            if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0))
            {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
            }
        }

        return var13;
    }//

    public IChunkProvider UBChunkProvider(IChunkProvider currentProvider) {
        return new UBChunkProvider(currentProvider,this.villageStoneSource,this.dimension);
    }

    public boolean chunkExists(int x,int z) {
        return chunkProvider.chunkExists(x, z);
    }

    public IChunkProvider servedChunkProvider(WorldServer world) {
        return providerFromChunkServer.get(accessChunkProviderServer.chunkProviderServer(world));
    }

    public void setChunkProvider(World world) {
        if (this.ubOn() == false) return;
        IChunkProvider currentProvider = accessChunkProvider.iChunkProvider(world);
        if (currentProvider instanceof UBChunkProvider) {
            // we have already UB'd the chunkProvider
        } else {
            if (currentProvider instanceof ChunkProviderGenerate){

        //logger.info(world.toString() + " " + currentProvider.toString() + " "  );

           accessChunkProvider.setIChunkProvider(world,
                    new UBChunkProvider(currentProvider,this.villageStoneSource,this.dimension));
            }
        }
    }

    public void setServedChunkProvider(WorldServer world) {
        if (this.ubOn() == false) return;
        ChunkProviderServer currentServer = this.accessChunkProviderServer.chunkProviderServer(world);
        IChunkProvider currentProvider = this.providerFromChunkServer.get(currentServer);
        if (currentProvider instanceof UBChunkProvider) {
            // we have already UB'd the chunkProvider
        } else {
            if ((currentProvider instanceof ChunkProviderGenerate)
                    ||(currentProvider.getClass().getName().contains("ChunkProviderBOP")
                    ||(currentProvider.getClass().getName().contains("ChunkProviderTwilightForest")
                    ||(currentProvider.getClass().getName().contains("BWG4ChunkProvider")
                    ||(currentProvider.getClass().getName().contains("ChunkProviderRTG")))))){
                providerFromChunkServer.setField(currentServer,
                    new UBChunkProvider(currentProvider,this.villageStoneSource,this.dimension));
            }
        }
    }

    public IChunkProvider chunkProvider(World world) {
        IChunkProvider currentProvider = accessChunkProvider.iChunkProvider(world);
        return currentProvider;
    }

    static class AccessChunkProvider {
        Field iChunkProviderField;
        AccessChunkProvider() {
            try {setIChunkProviderField();}
            catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        private void setIChunkProviderField() throws IllegalAccessException{
            Field [] fields = World.class.getDeclaredFields();
            for (int i = 0; i < fields.length;i ++) {
                if (IChunkProvider.class.isAssignableFrom(fields[i].getType())) {
                    iChunkProviderField = fields[i];
                    iChunkProviderField.setAccessible(true);
                    return;
                }
            }
            throw new RuntimeException();
        }

        public IChunkProvider iChunkProvider(World world) {
            try {
                //logger.info( "providing chunk "+world.getClass().getName());
                Object result = iChunkProviderField.get(world);
                if (result== null) {
                    //logger.info( "no provider "+world.getClass().getName());
                }
                 return (IChunkProvider)result;
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void setIChunkProvider(World world,IChunkProvider chunkProvider) {
            try {
                //logger.info( "saving provider "+world.getClass().getName()+ " "+ chunkProvider.getClass().getName());
                iChunkProviderField.set(world, chunkProvider);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    //

    static class AccessChunkProviderServer{
        Field iChunkProviderServerField;
        AccessChunkProviderServer() {
            try {setIChunkProviderField();}
            catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        private void setIChunkProviderField() throws IllegalAccessException{
            Field [] fields = WorldServer.class.getDeclaredFields();
            for (int i = 0; i < fields.length;i ++) {
                if (fields[i].getType() == ChunkProviderServer.class) {
                    iChunkProviderServerField = fields[i];
                    iChunkProviderServerField.setAccessible(true);
                }
            }
        }

        public ChunkProviderServer chunkProviderServer(WorldServer world) {
            try {
                //logger.info( "providing chunk "+world.getClass().getName());
                 return (ChunkProviderServer)(iChunkProviderServerField.get(world));
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void setIChunkProvider(WorldServer world,ChunkProviderServer chunkProvider) {
            try {
                //logger.info( "saving provider "+world.getClass().getName()+ " "+ chunkProvider.getClass().getName());
                iChunkProviderServerField.set(world, chunkProvider);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    static class AccessGenLayer extends GenLayer {
        AccessGenLayer() {super(0);}
        public int[] getInts(int i, int j, int k, int l) {return null;}

        public void report(GenLayer examinedLayer) throws IllegalAccessException{
            Field [] fields = this.getClass().getSuperclass().getDeclaredFields();
            Field parentField = null;
            //logger.info( "fieldcount "+fields.length);
            for (int i = 0; i < fields.length;i ++) {
                //logger.info( fields[i].getName());
                if (GenLayer.class.isAssignableFrom(fields[i].getClass())) {
                    parentField.setAccessible(true);
                }
            }
            int level = 0;
            while (examinedLayer!= null) {
                //logger.info( examinedLayer.getClass().getName());
                level ++;
                examinedLayer = (GenLayer)(parentField.get(examinedLayer));
                if (level > 100) break;
            }
        }
    }

}

