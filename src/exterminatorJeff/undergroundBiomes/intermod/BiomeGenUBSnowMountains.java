
package exterminatorJeff.undergroundBiomes.intermod;

import highlands.Highlands;
import highlands.worldgen.WorldGenHighlandsShrub;
import highlands.worldgen.WorldGenMountain;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBaseHighlands;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenUBSnowMountains extends BiomeGenBaseHighlands
{
	private static final Height biomeHeight = new Height(2.0F, 1.0F);

	public BiomeGenUBSnowMountains(int par1)
    {
        super(par1);

        int trees = -999;
	    int grass = 2;
	    int flowers = 0;
	    this.theBiomeDecorator = new BiomeDecoratorHighlands(this, trees, grass, flowers);

        this.spawnableCreatureList.clear();

        this.topBlock = Blocks.snow;
        this.fillerBlock = Blocks.snow;
        this.setHeight(biomeHeight);
        this.temperature = 0.0F;
        this.rainfall = 0.5F;

        this.setEnableSnow();
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random par1Random)
    {
        return (WorldGenAbstractTree)(new WorldGenHighlandsShrub(1, 1));
    }

    @Override
    @SuppressWarnings("static-access")
	public void decorate(World world, Random random, int x, int z) {
		BiomeGenBaseHighlands biome = this;
		// WorldType.getTranslateName() doesn't work in bukkit ?
    	//if(random.nextInt(3) == 0 && !world.provider.terrainType.getTranslateName().equals("ATG - Alternate"))
    	new WorldGenUBMountain(15, 25, false, 0).generate(world, random, x+random.nextInt(16), 128, z+random.nextInt(16));

    	((BiomeGenBase)this).theBiomeDecorator.decorateChunk(world, random, biome, x, z);
        int var5 = 3 + random.nextInt(6);

        for (int var6 = 0; var6 < var5; ++var6){
            int var7 = x + random.nextInt(16);
            int var8 = random.nextInt(28) + 4;
            int var9 = z + random.nextInt(16);
            Block var10 = world.getBlock(var7, var8, var9);

            if (var10.isReplaceableOreGen(world, x, z, z, Blocks.stone)){
            	world.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
            }
        }

        BiomeDecorator theDecorator = ((BiomeGenBase)this).theBiomeDecorator;

        BiomeDecoratorHighlands highlandsDecorator = (BiomeDecoratorHighlands)theDecorator;

    	highlandsDecorator.genOreHighlands(world, random, x, z, 20, ((BiomeDecoratorHighlands)this.theBiomeDecorator).HLice, 0, 128);

    	highlandsDecorator.genOreHighlands(world, random, x, z, 20, ((BiomeGenBase)this).theBiomeDecorator.ironGen, 64, 128);
    	highlandsDecorator.genOreHighlands(world, random, x, z, 8, ((BiomeGenBase)this).theBiomeDecorator.redstoneGen, 16, 32);
    	highlandsDecorator.genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.lapisGen, 32, 64);
    	highlandsDecorator.genOreHighlands(world, random, x, z, 2, ((BiomeGenBase)this).theBiomeDecorator.goldGen, 32, 64);
    	highlandsDecorator.genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.diamondGen, 16, 32);
    	highlandsDecorator.genOreHighlands(world, random, x, z, 2, ((BiomeGenBase)this).theBiomeDecorator.diamondGen, 0, 32);
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float par1)
    {
    	if(Highlands.skyColorFlag)return 0xC6E3FF;
    	else return super.getSkyColorByTemp(par1);
    }
}

