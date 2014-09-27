
package exterminatorJeff.undergroundBiomes.intermod;

import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBaseHighlands;
import highlands.biome.BiomeGenCliffs;
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
    public WorldGenerator func_76730_b(Random par1Random) {
        return getRandomWorldGenForGrass(par1Random);
    }
}
