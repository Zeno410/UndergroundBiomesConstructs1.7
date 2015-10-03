package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.UndergroundBiomeSet;
import net.minecraft.world.gen.layer.*;

import exterminatorJeff.undergroundBiomes.common.WorldGenManager;


public abstract class GenLayerUnderground extends GenLayer {
    private static int biomeSize = 3;
    public static int biomeSizeOfEntireMinecraftWorld = 22;
    public static int biomeSizeUsedForEarlierVersions = 3;
    public static boolean testing;

    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static GenLayer[] initializeAllBiomeGenerators(long par0, int size, UndergroundBiomeSet biomeSet) {
        
        GenLayerIsland var3 = new GenLayerIsland(1L);   
        GenLayerFuzzyZoom var9 = new GenLayerFuzzyZoom(2000L, var3);
        GenLayerAddIsland var10 = new GenLayerAddIsland(1L, var9);
        
        GenLayerZoom var11 = new GenLayerZoom(2001L, var10);
        var10 = new GenLayerAddIsland(2L, var11);
        
        var11 = new GenLayerZoom(2002L, var10);
        var10 = new GenLayerAddIsland(3L, var11);
        var11 = new GenLayerZoom(2003L, var10);
        var10 = new GenLayerAddIsland(4L, var11);
        int var4 = biomeSize;

        GenLayer var5 = GenLayerZoom.magnify(1000L, var11, 0);
        var5 = GenLayerZoom.magnify(1000L, var5, var4 + 2);
        
        GenLayerUndergroundBiomes var17 = new GenLayerUndergroundBiomes(200L, var5,biomeSet);

        if (size >= biomeSizeOfEntireMinecraftWorld) size = biomeSizeUsedForEarlierVersions;
        
        GenLayerSmooth var15 = new GenLayerSmooth(1000L, var17);
        GenLayer var6 = GenLayerZoom.magnify(1000L, var15, size);
        var6 = GenLayerZoom.magnify(1000L, var6, 2);
        
        

        GenLayerSmooth var19 = new GenLayerSmooth(1000L, var6);
        
        GenLayerVoronoiZoom var8 = new GenLayerVoronoiZoom(10L, var19);
        var8.initWorldGenSeed(par0);
        //testGenerator(var8, size);
        //testBiomeSize(var8);
        WorldGenManager.logger.info("initializing underground biome generators");

        return new GenLayer[] {var19, var8, var19};
    }

    public GenLayerUnderground(long par1){
        super(par1);
    }

    public static void testGenerator(GenLayer generator, int biomeSize) {
        int numberBiomes = 40;
        int [] hits = new int [numberBiomes];
        int multiplier = 16 << biomeSize;
        testing = true;
        for (int i = 0; i < 30; i ++) {
            for (int j  = 0; j < 30; j++) {
                int biome = generator.getInts(i*multiplier,j*multiplier,16,16)[0];
                WorldGenManager.logger.info("biome "+ biome  + " from "+ i*multiplier + " "+j*multiplier);
                hits[biome]++;
            }
        }
        for (int biomeType = 0; biomeType < numberBiomes; biomeType++) {
            WorldGenManager.logger.info("biome "+ biomeType + " hits "+ hits[biomeType]);
        }

        testing = false;

    }

    public static void testBiomeSize(GenLayer generator) {
        int length = 10000;
        int currentBiome = generator.getInts(0,0,16,16)[0];
        int currentStart =0;
        for (int i = 10; i < length; i+= 10) {
            int newBiome = generator.getInts(i,0,16,16)[0];
            if (newBiome != currentBiome) {
                WorldGenManager.logger.info("biome "+ currentBiome + " length "+ (i - currentStart));
                currentStart = i;
                currentBiome = newBiome;
            }
        }
    }
}
