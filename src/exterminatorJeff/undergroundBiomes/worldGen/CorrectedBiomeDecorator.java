
package exterminatorJeff.undergroundBiomes.worldGen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;

/**
 *
 * @author Zeno410
 */
public class CorrectedBiomeDecorator extends BiomeDecorator {

    public CorrectedBiomeDecorator(BiomeDecorator toCorrect) {
        this.bigMushroomGen = toCorrect.bigMushroomGen;
        this.bigMushroomsPerChunk = toCorrect.bigMushroomsPerChunk;
        this.cactiPerChunk = toCorrect.cactiPerChunk;
        this.cactusGen = toCorrect.cactusGen;
        this.clayGen = toCorrect.clayGen;
        this.clayPerChunk = toCorrect.clayPerChunk;
        this.coalGen = toCorrect.coalGen;
        this.deadBushPerChunk = toCorrect.deadBushPerChunk;
        this.diamondGen = toCorrect.diamondGen;
        this.dirtGen = toCorrect.dirtGen;
        this.flowersPerChunk = toCorrect.flowersPerChunk;
        this.generateLakes = toCorrect.generateLakes;
        this.goldGen = toCorrect.goldGen;
        this.grassPerChunk = toCorrect.grassPerChunk;
        this.gravelAsSandGen = toCorrect.gravelAsSandGen;
        this.gravelGen = toCorrect.gravelGen;
        this.ironGen = toCorrect.ironGen;
        this.lapisGen = toCorrect.lapisGen;
        this.mushroomBrownGen = toCorrect.mushroomBrownGen;
        this.mushroomRedGen = toCorrect.mushroomRedGen;
        this.mushroomsPerChunk = toCorrect.mushroomsPerChunk;
        this.randomGenerator = toCorrect.randomGenerator;
        this.redstoneGen = toCorrect.redstoneGen;
        this.reedGen = toCorrect.reedGen;
        this.reedsPerChunk = toCorrect.reedsPerChunk;
        this.sandGen = toCorrect.sandGen;
        this.sandPerChunk = toCorrect.sandPerChunk;
        this.sandPerChunk2 = toCorrect.sandPerChunk2;
        this.treesPerChunk = toCorrect.treesPerChunk;
        this.waterlilyGen = toCorrect.waterlilyGen;
        this.waterlilyPerChunk = toCorrect.waterlilyPerChunk;
        if (toCorrect.yellowFlowerGen != null) {
            this.yellowFlowerGen = toCorrect.yellowFlowerGen;
        }
    }
    @Override
    public void decorateChunk(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_) {
        World wasDecorating = currentWorld;
        Random randomizer= this.randomGenerator;
        currentWorld = null;
        if (this.yellowFlowerGen == null) {
            this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower);
        }
        if (super.yellowFlowerGen == null) {
            super.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower);
        }
        super.decorateChunk(p_150512_1_, p_150512_2_, p_150512_3_, p_150512_4_, p_150512_5_);
        currentWorld = wasDecorating;
        randomGenerator = randomizer;
    }

}
