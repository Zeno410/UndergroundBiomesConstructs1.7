
package exterminatorJeff.undergroundBiomes.worldGen;

import Zeno410Utils.AccessInt;
import Zeno410Utils.MethodAccessor;
import highlands.biome.BiomeDecoratorHighlands;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.TerrainGen;


import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;
/**
 *
 * @author Zeno410
 */
public class CorrectedBiomeDecoratorHighlands extends BiomeDecoratorHighlands{
    public static AccessInt<BiomeDecoratorHighlands> highlandsPl = new AccessInt("highlandsPlantsPerChunk");
    private BiomeDecorator mcthis;
    private MethodAccessor<BiomeDecorator> genOreAccess = new MethodAccessor<BiomeDecorator>("func_76797_b");
    private static int trees(BiomeDecorator source) {
        return source.treesPerChunk;
    }
    private static int grass(BiomeDecorator source) {
        return source.grassPerChunk;
    }

    private static int flowers(BiomeDecorator source) {
        return source.flowersPerChunk;
    }

    public CorrectedBiomeDecoratorHighlands(BiomeGenBase biome, BiomeDecoratorHighlands toCorrect) {
        super(biome,trees(toCorrect),grass(toCorrect),flowers(toCorrect),
                highlandsPl.get(toCorrect));
        mcthis = this;
    }

    // Why the two different functions that do the same thing? The former one wouldn't be called anyway...
    /* @Override
    public void func_150512_a(World world, Random random, BiomeGenBase biome, int x, int z) {
        World previous = ((BiomeDecorator)this).currentWorld;
        Random randomizer = ((BiomeDecorator)this).randomGenerator;
        ((BiomeDecorator)this).currentWorld  = null;
        World targetWorld = (world == null) ? previous : world;
        if (targetWorld == null) throw new RuntimeException();
        if (random == null) throw new RuntimeException();
        if (biome == null) throw new RuntimeException();
        try {
             super.decorateChunk(targetWorld, random, biome, x, z);
        } catch (RuntimeException e) {
            //((BiomeDecorator)this).currentWorld = targetWorld;
              int j;
               boolean doGen = TerrainGen.decorate(((BiomeDecorator)this).currentWorld,
                       ((BiomeDecorator)this).randomGenerator, ((BiomeDecorator)this).chunk_X,
                       ((BiomeDecorator)this).chunk_Z, FLOWERS);
                for (j = 0; doGen && j < ((BiomeDecorator)this).flowersPerChunk; ++j)
                {
                    int k = ((BiomeDecorator)this).chunk_X + ((BiomeDecorator)this).randomGenerator.nextInt(16) + 8;
                    int l = ((BiomeDecorator)this).chunk_Z + ((BiomeDecorator)this).randomGenerator.nextInt(16) + 8;
                    int i1 = ((BiomeDecorator)this).randomGenerator.nextInt(((BiomeDecorator)this).currentWorld.getHeightValue(k, l) + 32);
                    String s = biome.func_150572_a(((BiomeDecorator)this).randomGenerator, k, i1, l);
                    BlockFlower blockflower = BlockFlower.func_149857_e(s);

                    if (blockflower.getMaterial() != Material.air)
                    {
                        ((BiomeDecorator)this).yellowFlowerGen.func_150550_a(blockflower, BlockFlower.func_149856_f(s));
                        ((BiomeDecorator)this).yellowFlowerGen.generate(((BiomeDecorator)this).currentWorld,
                                ((BiomeDecorator)this).randomGenerator, k, i1, l);
                    }
                }
               throw e;
        }
        ((BiomeDecorator)this).currentWorld  = previous;
        ((BiomeDecorator)this).randomGenerator = randomizer;
    } */
    
    @Override
    public void decorateChunk(World world, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_) {
        World previous =this.currentWorld;
        Random randomizer= this.randomGenerator;
        currentWorld  = null;
        World targetWorld = (world == null) ? previous : world;
        super.decorateChunk(targetWorld, p_150512_2_, p_150512_3_, p_150512_4_, p_150512_5_);
        this.currentWorld  = previous;
        randomGenerator = randomizer;
    }

   public void func_150513_ab(BiomeGenBase p_150513_1_) {
       Random randomizer = mcthis.randomGenerator;
       World world = mcthis.currentWorld;
       int x = mcthis.chunk_X;
       int z = mcthis.chunk_Z;
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, randomizer, x, z));
        this.genOreAccess.run(mcthis);
        int i;
        int j;
        int k;

        boolean doGen = TerrainGen.decorate(world, randomizer, x, z, SAND);
        for (i = 0; doGen && i < mcthis.sandPerChunk2; ++i)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            mcthis.sandGen.generate(world, randomizer, j, world.getTopSolidOrLiquidBlock(j, k), k);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, CLAY);
        for (i = 0; doGen && i < mcthis.clayPerChunk; ++i)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            mcthis.clayGen.generate(world, randomizer, j, world.getTopSolidOrLiquidBlock(j, k), k);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, SAND_PASS2);
        for (i = 0; doGen && i < mcthis.sandPerChunk; ++i)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            mcthis.gravelAsSandGen.generate(world, randomizer, j, world.getTopSolidOrLiquidBlock(j, k), k);
        }

        i = mcthis.treesPerChunk;

        if (randomizer == null) throw new RuntimeException("already missing randomizer");
        if (randomizer.nextInt(10) == 0)
        {
            ++i;
        }

        int l;
        int i1;

        doGen = TerrainGen.decorate(world, randomizer, x, z, TREE);
        for (j = 0; doGen && j < i; ++j)

        {
            if (randomizer == null) throw new RuntimeException("no randomizer");
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = world.getHeightValue(k, l);
            WorldGenAbstractTree worldgenabstracttree = p_150513_1_.func_150567_a(randomizer);
            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);

            if (worldgenabstracttree.generate(world, randomizer, k, i1, l))
            {
                worldgenabstracttree.func_150524_b(world, randomizer, k, i1, l);
            }
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, BIG_SHROOM);
        for (j = 0; doGen && j < mcthis.bigMushroomsPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            mcthis.bigMushroomGen.generate(world, randomizer, k, world.getHeightValue(k, l), l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, FLOWERS);
        for (j = 0; doGen && j < mcthis.flowersPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) + 32);
            String s = p_150513_1_.func_150572_a(randomizer, k, i1, l);
            BlockFlower blockflower = BlockFlower.func_149857_e(s);

            if (blockflower.getMaterial() != Material.air)
            {
                mcthis.yellowFlowerGen.func_150550_a(blockflower, BlockFlower.func_149856_f(s));
                mcthis.yellowFlowerGen.generate(world, randomizer, k, i1, l);
            }
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, GRASS);
        for (j = 0; doGen && j < mcthis.grassPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
            WorldGenerator worldgenerator = p_150513_1_.getRandomWorldGenForGrass(randomizer);
            worldgenerator.generate(world, randomizer, k, i1, l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, DEAD_BUSH);
        for (j = 0; doGen && j < mcthis.deadBushPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
            (new WorldGenDeadBush(Blocks.deadbush)).generate(world, randomizer, k, i1, l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, LILYPAD);
        for (j = 0; doGen && j < mcthis.waterlilyPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;

            for (i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2); i1 > 0 && world.isAirBlock(k, i1 - 1, l); --i1)
            {
                ;
            }

            mcthis.waterlilyGen.generate(world, randomizer, k, i1, l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, SHROOM);
        for (j = 0; doGen && j < mcthis.mushroomsPerChunk; ++j)
        {
            if (randomizer.nextInt(4) == 0)
            {
                k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
                l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
                i1 = world.getHeightValue(k, l);
                mcthis.mushroomBrownGen.generate(world, randomizer, k, i1, l);
            }

            if (randomizer.nextInt(8) == 0)
            {
                k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
                l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
                i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
                mcthis.mushroomRedGen.generate(world, randomizer, k, i1, l);
            }
        }

        if (doGen && randomizer.nextInt(4) == 0)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            l = randomizer.nextInt(world.getHeightValue(j, k) * 2);
            mcthis.mushroomBrownGen.generate(world, randomizer, j, l, k);
        }

        if (doGen && randomizer.nextInt(8) == 0)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            l = randomizer.nextInt(world.getHeightValue(j, k) * 2);
            mcthis.mushroomRedGen.generate(world, randomizer, j, l, k);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, REED);
        for (j = 0; doGen && j < mcthis.reedsPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
            mcthis.reedGen.generate(world, randomizer, k, i1, l);
        }

        for (j = 0; doGen && j < 10; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
            mcthis.reedGen.generate(world, randomizer, k, i1, l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, PUMPKIN);
        if (doGen && randomizer.nextInt(32) == 0)
        {
            j = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            k = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            l = randomizer.nextInt(world.getHeightValue(j, k) * 2);
            (new WorldGenPumpkin()).generate(world, randomizer, j, l, k);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, CACTUS);
        for (j = 0; doGen && j < mcthis.cactiPerChunk; ++j)
        {
            k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
            l = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
            i1 = randomizer.nextInt(world.getHeightValue(k, l) * 2);
            mcthis.cactusGen.generate(world, randomizer, k, i1, l);
        }

        doGen = TerrainGen.decorate(world, randomizer, x, z, LAKE);
        if (doGen && mcthis.generateLakes)
        {
            for (j = 0; j < 50; ++j)
            {
                k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
                l = randomizer.nextInt(randomizer.nextInt(248) + 8);
                i1 = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_water)).generate(world, randomizer, k, l, i1);
            }

            for (j = 0; j < 20; ++j)
            {
                k = mcthis.chunk_X + randomizer.nextInt(16) + 8;
                l = randomizer.nextInt(randomizer.nextInt(randomizer.nextInt(240) + 8) + 8);
                i1 = mcthis.chunk_Z + randomizer.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_lava)).generate(world, randomizer, k, l, i1);
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, randomizer, x, z));
    }


}
