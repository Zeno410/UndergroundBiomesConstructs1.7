package exterminatorJeff.undergroundBiomes.common.block;

import Zeno410Utils.Acceptor;
import java.util.Random;

import net.minecraft.item.ItemStack;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockMetamorphicCobblestone extends BlockMetamorphicStone
{
    public BlockMetamorphicCobblestone() {
        super(UBIDs.metamorphicCobblestoneName);
        baseHardness = this.blockHardness;
        this.setHardness(this.blockHardness*1.333333f);
        replaceableByOre = false;
        UndergroundBiomes.instance().settings().cobbleHardnessMultiplier.informOnChange(hardnessUpdater);
        hardnessUpdater.accept(UndergroundBiomes.instance().settings().cobbleHardnessMultiplier.value());
    }
    final float baseHardness;
    private final Acceptor<Double> hardnessUpdater = new Acceptor<Double>() {

        @Override
        public void accept(Double accepted) {
            setHardness(baseHardness*accepted.floatValue());
        }

    };
    @Override
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y){
        return new ItemStack(UBIDs.metamorphicCobblestoneName.block(), 1, metadata & 7);
    }

    public boolean hasRareDrops(){
        return false;
    }

    public String getBlockName(int index){
        return super.getBlockName(index) + "Cobble";
    }
}
