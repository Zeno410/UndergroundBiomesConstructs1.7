
package exterminatorJeff.undergroundBiomes.intermod;

import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBaseHighlands;
import highlands.biome.BiomeGenDesertMountains;
import highlands.worldgen.WorldGenHighlandsShrub;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

/**
 *
 * @author Zeno410
 */
public class BiomeGenUBDesertMountains extends BiomeGenDesertMountains
{
    private static final Height biomeHeight = new Height(1.6F, 0.5F);

	public BiomeGenUBDesertMountains(int par1)
    {
        super(par1);
        int trees = -999;
	    int grass = 0;
	    int flowers = 0;
	    ((BiomeGenBase)this).theBiomeDecorator = new BiomeDecoratorHighlands(this, trees, grass, flowers);

        //((BiomeGenBase)this).spawnableCreatureList.clear();

        ((BiomeGenBase)this).topBlock = Blocks.sand;
        ((BiomeGenBase)this).fillerBlock = Blocks.sand;
        ((BiomeGenBase)this).temperature = 0.9F;
        ((BiomeGenBase)this).rainfall = 0.0F;

        ((BiomeGenBase)this).setDisableRain();
        ((BiomeGenBase)this).setBiomeName("Desert Mountains");
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random par1Random)
    {
        return (WorldGenAbstractTree)(new WorldGenHighlandsShrub(1, 1));
    }

    @Override
	public void decorate(World world, Random random, int x, int z) {

    	if(random.nextInt(4) == 0)
    		new WorldGenUBMountain(15, 20, false, 2).generate(world, random, x+random.nextInt(16), 128, z+random.nextInt(16));

    	BiomeGenBaseHighlands biome = this;
		((BiomeGenBase)this).theBiomeDecorator.decorateChunk(world, random, biome, x, z);

		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 20, ((BiomeGenBase)this).theBiomeDecorator.ironGen, 64, 128);
		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 8, ((BiomeGenBase)this).theBiomeDecorator.redstoneGen, 16, 32);
		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.lapisGen, 32, 64);
		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 2, ((BiomeGenBase)this).theBiomeDecorator.goldGen, 32, 64);
		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 4, ((BiomeGenBase)this).theBiomeDecorator.goldGen, 0, 64);
		((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.diamondGen, 16, 32);
    }
}