
package exterminatorJeff.undergroundBiomes.intermod;

import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBadlands;
import highlands.biome.BiomeGenBaseHighlands;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 *
 * @author Zeno410
 */
public class BiomeGenUBBadlands extends BiomeGenBadlands {
    private WorldGenerator ubStoneGenerator = new WorldGenUndergroundUB(Blocks.stone, 72, Blocks.dirt);;
    private static final Height biomeHeight = new Height(0.8F, 0.45F);

    public BiomeGenUBBadlands(int biomeID) {
        super(biomeID);
	    int trees = 1;
	    int grass = 6;
	    int flowers = 0;
	    int plants = 1;
	    ((BiomeGenBase)this).theBiomeDecorator = new BiomeDecoratorHighlands(this, trees, grass, flowers, plants);

        ((BiomeGenBase)this).setHeight(biomeHeight);
        ((BiomeGenBase)this).temperature = 0.6F;
        ((BiomeGenBase)this).rainfall = 0.1F;
        ((BiomeGenBase)this).setBiomeName("Badlands");
    }

    @Override
	public void decorate(World world, Random random, int x, int z) {
    	BiomeGenBaseHighlands biome = this;
    	((BiomeGenBase)this).theBiomeDecorator.decorateChunk(world, random, biome, x, z);
        int var5 = 3 + random.nextInt(6);

        for (int var6 = 0; var6 < var5; ++var6)
        {
            int var7 = x + random.nextInt(16);
            int var8 = random.nextInt(28) + 4;
            int var9 = z + random.nextInt(16);
            Block var10 = world.getBlock(var7, var8, var9);

            if (var10 == Blocks.stone)
            {
            	world.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
            }
        }

        genUBStoneHighlandsNoCheck(world, random, x, z, 6, ubStoneGenerator, 62, 120);
        BiomeDecoratorHighlands decorator = (BiomeDecoratorHighlands) ((BiomeGenBase)this).theBiomeDecorator;
        decorator.genOreHighlands(world, random, x, z, 2, ((BiomeGenBase)this).theBiomeDecorator.goldGen, 0, 32);
    }

    public void genUBStoneHighlandsNoCheck(World world, Random random, int locX, int locZ, int timesPerChunk, WorldGenerator oreGen, int minH, int maxH)
    {
        for (int var5 = 0; var5 < timesPerChunk; ++var5)
        {
            int var6 = locX + random.nextInt(16);
            int var7 = random.nextInt(maxH - minH) + minH;
            int var8 = locZ + random.nextInt(16);
            oreGen.generate(world, random, var6, var7, var8);
        }
    }

}
