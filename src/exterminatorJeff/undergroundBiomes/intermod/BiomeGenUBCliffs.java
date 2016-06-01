
package exterminatorJeff.undergroundBiomes.intermod;

import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBaseHighlands;
import highlands.biome.BiomeGenCliffs;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 *
 * @author Zeno410
 */
public class BiomeGenUBCliffs extends BiomeGenCliffs {

    public BiomeGenUBCliffs(BiomeGenCliffs model, int biomeID) {
        super(biomeID);
	 	int trees = 2;
	    int grass = 4;
	    int flowers = 0;
	    int plants = 1;
	    ((BiomeGenBase)this).theBiomeDecorator = new BiomeDecoratorHighlands(this, trees, grass, flowers);
        ((BiomeGenBase)this).setHeight(new Height(1.0F, 0.5F));
        ((BiomeGenBase)this).temperature = 0.4F;
        ((BiomeGenBase)this).rainfall = 0.4F;
        ((BiomeGenBase)this).setBiomeName("Cliffs");
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
        return new WorldGenBoulders(Blocks.tallgrass,1);
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

            if (var10.isReplaceableOreGen(world, x, z, z, Blocks.stone))
            {
            	world.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
            }
        }

        BiomeDecorator theDecorator = ((BiomeGenBase)this).theBiomeDecorator;

        BiomeDecoratorHighlands highlandsDecorator = (BiomeDecoratorHighlands)theDecorator;

        highlandsDecorator.genOreHighlands(world, random, x, z, 20,
                ((BiomeDecorator)((BiomeGenBase)this).theBiomeDecorator).ironGen, 64, 128);
        highlandsDecorator.genOreHighlands(world, random, x, z, 8,
                ((BiomeDecorator)((BiomeGenBase)this).theBiomeDecorator).redstoneGen, 16, 32);
        highlandsDecorator.genOreHighlands(world, random, x, z, 1,
                ((BiomeDecorator)((BiomeGenBase)this).theBiomeDecorator).lapisGen, 32, 64);
        highlandsDecorator.genOreHighlands(world, random, x, z, 2,
                ((BiomeDecorator)((BiomeGenBase)this).theBiomeDecorator).goldGen, 32, 64);
        highlandsDecorator.genOreHighlands(world, random, x, z, 1,
                ((BiomeDecorator)((BiomeGenBase)this).theBiomeDecorator).diamondGen, 16, 32);
    }

}
