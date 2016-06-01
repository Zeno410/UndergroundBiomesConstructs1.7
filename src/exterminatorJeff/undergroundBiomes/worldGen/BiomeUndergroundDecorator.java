package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.UBStoneCodes;
import exterminatorJeff.undergroundBiomes.api.BiomeGenUndergroundBase;
import Zeno410Utils.Accessor;
import Zeno410Utils.BlockLocation;
import Zeno410Utils.BlockLocationProbe;
import Zeno410Utils.BlockState;
import Zeno410Utils.Zeno410Logger;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.world.World;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.WorldGenManager;

import exterminatorJeff.undergroundBiomes.api.NamedVanillaBlock;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.intermod.BiomeGenUBBadlands;
import exterminatorJeff.undergroundBiomes.intermod.BiomeGenUBCliffs;
import exterminatorJeff.undergroundBiomes.intermod.BiomeGenUBDesertMountains;
import exterminatorJeff.undergroundBiomes.intermod.BiomeGenUBRockMountains;
import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBadlands;
import highlands.biome.BiomeGenCliffs;
import highlands.biome.BiomeGenDesertMountains;
import highlands.biome.BiomeGenRockMountains;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import biomesoplenty.api.biome.BOPInheritedBiome;

public class BiomeUndergroundDecorator {

    private final WorldGenManager worldGen;
    private final OreUBifier oreUBifier;
    private final CurrentWorldMemento.Manager currentWorldManager = new CurrentWorldMemento.Manager();
    private final ArrayList<BiomeDecoratorCorrector> correctors = new ArrayList<BiomeDecoratorCorrector> ();

    public static Logger logger = new Zeno410Logger("BiomeUndergroundDecorator").logger();
    private final HashSet<BlockLocation> beingGenerated = new HashSet<BlockLocation>();
    private static HashSet<BlockLocation> needsRedo = new HashSet<BlockLocation>();
    public static void noMoreRedos() {
        needsRedo.clear();
    }

    private static BlockLocationProbe probe = new BlockLocationProbe(0,100,0);
    public static void needsRedo(int worldX, int worldZ, World world) {
        probe.setX(worldX>>4);
        probe.setY(worldZ>>4);
        probe.setZ(world.provider.dimensionId);
        if (needsRedo.contains(probe)) return;
        needsRedo.add(probe.forStorage());
    }
    public static void redoFinished(int worldX, int worldZ, World world) {
        needsRedo.remove(new BlockLocation(worldX>>4,worldZ>>4,world.provider.dimensionId));
    }
    public void doRedos(World redone) {
        ArrayList<BlockLocation> willRedo = new ArrayList<BlockLocation>();
        for(BlockLocation location: needsRedo) {
            if (location.z() == redone.provider.dimensionId) {
                willRedo.add(location);
            }
        }
        for (BlockLocation location: willRedo) {
            this.replaceChunkOres(location.x()<<4, location.y()<<4, redone);
            //logger.info("reredo ores chunk "+(location.x()<<4)+" "+(location.y()<<4));
        }
        for (BlockLocation location: willRedo) {
            redoFinished(location.x(), location.x(), redone);
        }
    }

    public BiomeUndergroundDecorator(WorldGenManager worldGen,OreUBifier oreUBifier) {
        this.worldGen = worldGen;
        this.oreUBifier = oreUBifier;
        arrangeHighlandsCompatibility();
        setupCorrectors();
        correctBiomeDecorators();
    }

    public void decorate(World par1World, Random par2Random, int x, int z){
            replaceBlocksForUndergroundBiome(x, z,par1World);
    }

    public void replaceBlocksForUndergroundBiome(int par_x, int par_z, World currentWorld) {
        if (worldGen.ubOn() == false ) return;
        this.eraseBogusCurrentWorlds();
        BlockLocation chunkLocation = new BlockLocation(par_x,par_z, currentWorld.provider.dimensionId);
        // abort if this chunk is already being generated
        if (this.beingGenerated.contains(chunkLocation)) return;
        if (this.beingGenerated.size() ==0) {

        }
        beingGenerated.add(chunkLocation);
        int generationHeight = UndergroundBiomes.generateHeight();
        BiomeGenUndergroundBase[] undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);

        for(int x = par_x; x < par_x + 16; x++)
        {
            for(int z = par_z; z < par_z + 16; z++)
            {
                BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[(x-par_x) + (z-par_z) * 16];
                int variation = (int) (currentBiome.strataNoise.noise(x/55.533, z/55.533, 3, 1, 0.5) * 10 - 5);
                UBStoneCodes defaultColumnStone = currentBiome.fillerBlockCodes;

                Chunk chunk = currentWorld.getChunkFromBlockCoords(x, z);
                ExtendedBlockStorage[] ebsArray = chunk.getBlockStorageArray();
                ExtendedBlockStorage ebs = null;

                for(int y = 1; y < generationHeight; y++) {
                    ebs = ebsArray[y >> 4];
                    if (ebs == null) continue;
                    Block currentBlock = ebs.getBlockByExtId(x & 15, y & 15, z & 15);

                    if(NamedVanillaBlock.stone.matches(currentBlock)){
                        UBStoneCodes strata = currentBiome.getStrataBlockAtLayer(y + variation);
                        ebs.func_150818_a(x & 15, y & 15, z & 15, strata.block);
                        ebs.setExtBlockMetadata(x & 15, y & 15, z & 15, strata.metadata);
                        chunk.isModified = true;
                        continue;
                    }
                    if (1>0) continue;
                    // skip if air;
                    if (Block.isEqualTo(Blocks.air, currentBlock)) continue;
                    if (Block.isEqualTo(Blocks.water, currentBlock)) continue;
                    if (Block.isEqualTo(currentBlock, UndergroundBiomes.igneousStone)) continue;
                    if (Block.isEqualTo(currentBlock, UndergroundBiomes.metamorphicStone)) continue;
                    int metadata = currentWorld.getBlockMetadata(x, y, z);
                    if (this.oreUBifier.replaces(currentBlock,metadata)) {
                        UBStoneCodes baseStrata = currentBiome.getStrataBlockAtLayer(y + variation);
                        BlockState replacement = oreUBifier.replacement(currentBlock, metadata,baseStrata,defaultColumnStone);
                        currentWorld.setBlock(x, y, z, replacement.block, replacement.metadata, 0);
                        continue;
                    }
                    if (Block.isEqualTo(currentBlock, Blocks.coal_ore)) throw new RuntimeException();
                    // only replace cobble if in config
                    // currently off: unacceptable results
                    /*
                    if (!(UndergroundBiomes.replaceCobblestone)) continue;
                    if (currentBlockID == Block.cobblestone.blockID) {
                        BlockCodes strataCobble =
                                currentBiome.fillerBlockCodes.onDrop;
                        currentWorld.setBlock(x, y, z, strataCobble.blockID, strataCobble.metadata, 0x02);
                    }*/
                }
            }
        }
        beingGenerated.remove(chunkLocation);
    }
    
    public void replaceOresForUndergroundBiome(int par_x, int par_z, World currentWorld) {
        // currently not used
        if (worldGen.ubOn() == false ) return;
        if (this.oreUBifier.replacementActive()==false) return;
        BlockLocation chunkLocation = new BlockLocation(par_x,par_z, currentWorld.provider.dimensionId);
        // abort if this chunk is already being generated
        if (this.beingGenerated.contains(chunkLocation)) return;
        beingGenerated.add(chunkLocation);
        int generationHeight = UndergroundBiomes.generateHeight();
        BiomeGenUndergroundBase[] undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);
        for(int x = par_x; x < par_x + 16; x++)
        {
            for(int z = par_z; z < par_z + 16; z++)
            {
                BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[(x-par_x) + (z-par_z) * 16];
                int variation = (int) (currentBiome.strataNoise.noise(x/55.533, z/55.533, 3, 1, 0.5) * 10 - 5);
                UBStoneCodes defaultColumnStone = currentBiome.fillerBlockCodes;
                for(int y = 1; y < generationHeight; y++) {
                    Block currentBlock = currentWorld.getBlock(x, y, z);
                    if (x == 963) if (z== 963) {
                        //logger.info(""+y+" "+currentBlock.toString());
                    }
                    // skip if air;
                    if (Block.isEqualTo(Blocks.air, currentBlock)) continue;
                    if (Block.isEqualTo(Blocks.water, currentBlock)) continue;
                    if (Block.isEqualTo(Blocks.stone, currentBlock)) continue;
                    if (Block.isEqualTo(UndergroundBiomes.igneousStone, currentBlock)) continue;
                    if (Block.isEqualTo(UndergroundBiomes.metamorphicStone, currentBlock)) continue;
                    int metadata = currentWorld.getBlockMetadata(x, y, z);
                    if (this.oreUBifier.replaces(currentBlock,metadata)) {
                        UBStoneCodes baseStrata = currentBiome.getStrataBlockAtLayer(y + variation);
                        BlockState replacement = oreUBifier.replacement(currentBlock, metadata,baseStrata,defaultColumnStone);
                        currentWorld.setBlock(x, y, z, replacement.block, replacement.metadata, 2);
                    }

                    // only replace cobble if in config
                    // currently off: unacceptable results
                    /*
                    if (!(UndergroundBiomes.replaceCobblestone)) continue;
                    if (currentBlockID == Block.cobblestone.blockID) {
                        BlockCodes strataCobble =
                                currentBiome.fillerBlockCodes.onDrop;
                        currentWorld.setBlock(x, y, z, strataCobble.blockID, strataCobble.metadata, 0x02);
                    }*/
                }

                if (x == 963) if (z== 963) {
                    BlockMetadataBase.test963 = true;
                }
            }
        }
        beingGenerated.remove(chunkLocation);
        redoFinished(par_x, par_z, currentWorld);

    }

    public void replaceChunkOres(int par_x, int par_z, World currentWorld) {
        if (worldGen.ubOn() == false ) return;
        if (this.oreUBifier.replacementActive()==false) return;
        BlockLocation chunkLocation = new BlockLocation(par_x,par_z, currentWorld.provider.dimensionId);
        // abort if this chunk is already being generated
        if (this.beingGenerated.contains(chunkLocation)) {
            if (UndergroundBiomes.crashOnProblems()) throw new RuntimeException();
        }
        CurrentWorldMemento memento = this.currentWorldManager.memento();
        beingGenerated.add(chunkLocation);
        int generationHeight = UndergroundBiomes.generateHeight();
        BiomeGenUndergroundBase[] undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);

        Chunk chunk = currentWorld.getChunkFromBlockCoords(par_x, par_z);
        ExtendedBlockStorage [] storage = chunk.getBlockStorageArray();

        for(int chunkx = 0; chunkx < 16; chunkx++)
        {
            for(int chunkz = 0; chunkz < 16; chunkz++)
            {
                BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[(chunkx) + (chunkz) * 16];
                int x = par_x+chunkx;
                int z = par_z+chunkz;
                int variation = (int) (currentBiome.strataNoise.noise(x/55.533, z/55.533, 3, 1, 0.5) * 10 - 5);
                UBStoneCodes defaultColumnStone = currentBiome.fillerBlockCodes;
                for(int y = 1; y < generationHeight; y++) {
                    ExtendedBlockStorage extendedblockstorage = storage[y >> 4];
                    if (extendedblockstorage == null) continue;
                    int inLevelY = y&15;
                    int blockID = extendedblockstorage.getBlockLSBArray()[inLevelY << 8 | chunkz << 4 | chunkx] & 255;
                    if (extendedblockstorage.getBlockMSBArray()!= null) {
                         blockID |= extendedblockstorage.getBlockMSBArray().get(chunkx, inLevelY, chunkz) << 8;
                    }
                    OreUBifier.BlockReplacer replacer = this.oreUBifier.blockReplacer(blockID);

                    if (replacer != null) {
                        int metadata = extendedblockstorage.getExtBlockMetadata(chunkx, inLevelY, chunkz);
                        OreUBifier.BlockStateReplacer blockStateReplacer = replacer.replacer(metadata);
                        if (blockStateReplacer != null) {
                            UBStoneCodes baseStrata = currentBiome.getStrataBlockAtLayer(y + variation);
                            BlockState replacement = blockStateReplacer.replacement(baseStrata,defaultColumnStone);
                            if (replacement != null) {
                                extendedblockstorage.func_150818_a(chunkx, inLevelY, chunkz, replacement.block);
                                extendedblockstorage.setExtBlockMetadata(chunkx, inLevelY, chunkz, replacement.metadata);
                            }
                        }
                    }
                }
            }
        }
        beingGenerated.remove(chunkLocation);
        memento.restore();
        currentWorld.getChunkProvider().unloadQueuedChunks();
        redoFinished(par_x, par_z, currentWorld);
    }

    public void replaceChunkOres(IChunkProvider provider, int par_x, int par_z) {
        if (worldGen.ubOn() == false ) return;
        if (this.oreUBifier.replacementActive()==false) return;
        CurrentWorldMemento memento = this.currentWorldManager.memento();
        int generationHeight = UndergroundBiomes.generateHeight();
        BiomeGenUndergroundBase[] undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);

        Chunk chunk = provider.provideChunk(par_x, par_z);
        ExtendedBlockStorage [] storage = chunk.getBlockStorageArray();

        for(int chunkx = 0; chunkx < 16; chunkx++)
        {
            for(int chunkz = 0; chunkz < 16; chunkz++)
            {
                BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[(chunkx) + (chunkz) * 16];
                int x = par_x+chunkx;
                int z = par_z+chunkz;
                int variation = (int) (currentBiome.strataNoise.noise(x/55.533, z/55.533, 3, 1, 0.5) * 10 - 5);
                UBStoneCodes defaultColumnStone = currentBiome.fillerBlockCodes;
                for(int y = 1; y < generationHeight; y++) {
                    ExtendedBlockStorage extendedblockstorage = storage[y >> 4];
                    if (extendedblockstorage == null) continue;
                    int inLevelY = y&15;
                    int blockID = extendedblockstorage.getBlockLSBArray()[inLevelY << 8 | chunkz << 4 | chunkx] & 255;
                    if (extendedblockstorage.getBlockMSBArray()!= null) {
                         blockID |= extendedblockstorage.getBlockMSBArray().get(chunkx, inLevelY, chunkz) << 8;
                    }
                    OreUBifier.BlockReplacer replacer = this.oreUBifier.blockReplacer(blockID);

                    if (replacer != null) {
                        int metadata = extendedblockstorage.getExtBlockMetadata(chunkx, inLevelY, chunkz);
                        OreUBifier.BlockStateReplacer blockStateReplacer = replacer.replacer(metadata);
                        if (blockStateReplacer != null) {
                            UBStoneCodes baseStrata = currentBiome.getStrataBlockAtLayer(y + variation);
                            BlockState replacement = blockStateReplacer.replacement(baseStrata,defaultColumnStone);
                            if (replacement != null) {
                                extendedblockstorage.func_150818_a(chunkx, inLevelY, chunkz, replacement.block);
                                extendedblockstorage.setExtBlockMetadata(chunkx, inLevelY, chunkz, replacement.metadata);
                            }
                        }
                    }
                }
            }
        }
        memento.restore();
    }

    public void replaceChunkBlocks (Chunk chunk, int par_x, int par_z, int dimension) {
        if (worldGen.ubOn() == false ) return;
        worldGen.setGenerated(par_x, par_z);
        par_x = par_x *16; par_z = par_z *16;
        // abort if this chunk is already being generated
        BlockLocation chunkLocation = new BlockLocation(par_x,par_z, dimension);
        if (this.beingGenerated.contains(chunkLocation)) return;
        beingGenerated.add(chunkLocation);
        int generationHeight = UndergroundBiomes.generateHeight();
        BiomeGenUndergroundBase[] undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);

        for(int inChunkX = 0; inChunkX < 16; inChunkX++) {
            for(int inChunkZ =0; inChunkZ < 16; inChunkZ++){
                BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[(inChunkX) + (inChunkZ) * 16];
                int variation = (int) (currentBiome.strataNoise.noise((inChunkX+par_x)/55.533, (inChunkZ+par_z)/55.533, 3, 1, 0.5) * 10 - 5);
                for(int y = 1; y < generationHeight; y++) {
                    Block currentBlock = chunk.getBlock(inChunkX, y, inChunkZ);
                    if(NamedVanillaBlock.stone.matches(currentBlock)){
                        TileEntity entity = chunk.getTileEntityUnsafe(inChunkX, y, inChunkZ);
                        if (entity == null ) {
                            UBStoneCodes strata = currentBiome.getStrataBlockAtLayer(y + variation);
                            chunk.func_150807_a(inChunkX, y, inChunkZ, strata.block, strata.metadata);
                        //continue;
                        }
                        // we can't replace ores because they haven't been placed yet
                        // that is offsourced to replaceOre, which will be called at the appropriate time
                    }
                }
            }
        }
        beingGenerated.remove(chunkLocation);
    }

    public UBStoneCodes fillerBlock(int par_x, int par_z) {

        BiomeGenUndergroundBase[]undergroundBiomesForGeneration= new BiomeGenUndergroundBase[256];
        undergroundBiomesForGeneration = worldGen.loadUndergroundBlockGeneratorData(undergroundBiomesForGeneration, par_x, par_z, 16, 16);
        BiomeGenUndergroundBase currentBiome = undergroundBiomesForGeneration[8 + 8 * 16];

        return currentBiome.fillerBlockCodes;

    }

    public void eraseBogusCurrentWorlds() {
        // don't do this if the user requests not to
        if (UndergroundBiomes.instance().settings().clearVarsForRecursiveGeneration.value() == false) return;
        try {
            for (BiomeGenBase biomeGenBase : BiomeGenBase.getBiomeGenArray()) {
                biomeGenBase.theBiomeDecorator.currentWorld = null;
            }
        } catch (NullPointerException e) {
        }
    }

    public static class Request {
        public final int x;
        public final int z;
        public final World world;
        public Request(int x, int z, World world) {
            this.x = x; this.z = z; this.world = world;
        }
    }

    private void correctBiomeDecorators() {
        if (UndergroundBiomes.instance().settings().imposeUBStone.value() == false) return;
        BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0 ;i < biomes.length; i++) {
            BiomeGenBase biome = biomes[i];
            if (biome != null) {
                logger.info(biome.biomeName + " " + biome.toString());
                BiomeDecorator currentDecorator = biome.theBiomeDecorator;
                for (BiomeDecoratorCorrector corrector: this.correctors) {
                    BiomeDecorator newDecorator = corrector.corrected(biome,currentDecorator);
                    if (newDecorator != currentDecorator) {
                        // the corrector wants a change
                        logger.info("changing");
                        biome.theBiomeDecorator = newDecorator;
                        // we're done
                        break;
                    }
                }
            }
        }
    }

    private void setupCorrectors() {
        this.correctors.add(new VanillaDecoratorCorrector());
        try {
            this.correctors.add(new HighlandsDecoratorCorrector());
        } catch (java.lang.NoClassDefFoundError e) {
            // no Highlands; nothing to do;
        }
        try {
            this.correctors.add(new BoPDecoratorCorrector());
        } catch (java.lang.NoClassDefFoundError e) {
            // no BoP; nothing to do;
        }

    }

    private class VanillaDecoratorCorrector implements BiomeDecoratorCorrector {
        Class standardDecoratorClass = BiomeDecorator.class;

        public BiomeDecorator corrected(BiomeGenBase biome, BiomeDecorator currentDecorator) {
            if ((currentDecorator.getClass().equals(standardDecoratorClass))) {
                return new CorrectedBiomeDecorator(currentDecorator);
            }
            return currentDecorator;
        }
    }

    private class BoPDecoratorCorrector implements BiomeDecoratorCorrector {
        Class standardDecoratorClass = BiomeDecorator.class;

        public BiomeDecorator corrected(BiomeGenBase biome, BiomeDecorator currentDecorator) {
            if ((currentDecorator.getClass().getName().contains("BoP"))&&!(currentDecorator instanceof CorrectedBiomeDecorator))  {
                return new CorrectedBiomeDecorator(currentDecorator);
            }
            return currentDecorator;
        }
    }

    private class HighlandsDecoratorCorrector implements BiomeDecoratorCorrector {
        Class standardDecoratorClass = BiomeDecoratorHighlands.class;

        public BiomeDecorator corrected(BiomeGenBase biome, BiomeDecorator currentDecorator) {
            //if ((currentDecorator.getClass().equals(standardDecoratorClass))) {
            if ((currentDecorator instanceof BiomeDecoratorHighlands)&&!(currentDecorator instanceof CorrectedBiomeDecorator)) {
                logger.info("corrected decorator for "+biome.biomeName);

                return new CorrectedBiomeDecoratorHighlands(biome,(BiomeDecoratorHighlands)currentDecorator);
            }
            return currentDecorator;
        }
    }

    private void arrangeHighlandsCompatibility() {
        try {
            BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
            for (int i = 0 ;i < biomes.length; i++) {
                BiomeGenBase biome = biomes[i];
                if (biome != null) {
                    if (biome instanceof BiomeGenCliffs) {
                        biomes[i] = new BiomeGenUBCliffs((BiomeGenCliffs)biome,i);
                    }
                    if (biome instanceof BiomeGenDesertMountains) {
                        biomes[i] = new BiomeGenUBDesertMountains(i);
                    }
                    if (biome instanceof BiomeGenRockMountains) {
                        biomes[i] = new BiomeGenUBRockMountains(i);
                    }
                    if (biome instanceof BiomeGenBadlands) {
                        biomes[i] = new BiomeGenUBBadlands(i);
                    }
                }
            }
        } catch (java.lang.NoClassDefFoundError e) {
        }

    }
}


