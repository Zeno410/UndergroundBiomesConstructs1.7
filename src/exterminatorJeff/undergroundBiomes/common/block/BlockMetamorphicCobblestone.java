package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.ItemStack;

import exterminatorJeff.undergroundBiomes.api.UBIDs;

public class BlockMetamorphicCobblestone extends BlockMetamorphicStone
{
    public BlockMetamorphicCobblestone() {
        super(UBIDs.metamorphicCobblestoneName);
        this.setHardness(this.blockHardness*1.333333f);
        replaceableByOre = false;
    }

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
