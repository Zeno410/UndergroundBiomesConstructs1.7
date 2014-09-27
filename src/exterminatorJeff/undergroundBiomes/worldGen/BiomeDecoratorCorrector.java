/*
 * Available under the Lesser GPL License 3.0
 */

package exterminatorJeff.undergroundBiomes.worldGen;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

/**
 *
 * @author Zeno410
 */
public interface BiomeDecoratorCorrector {
    public abstract BiomeDecorator corrected(BiomeGenBase biome, BiomeDecorator toCorrect);
}
