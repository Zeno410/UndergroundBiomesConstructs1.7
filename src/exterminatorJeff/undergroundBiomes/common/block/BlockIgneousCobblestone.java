package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import exterminatorJeff.undergroundBiomes.api.UBIDs;

public class BlockIgneousCobblestone extends BlockIgneousStone
{
    public BlockIgneousCobblestone() {
        super(UBIDs.igneousCobblestoneName);
        this.setHardness(this.blockHardness*1.333333f);
        replaceableByOre = false;
    }

    @Override
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y) {
        return new ItemStack(UBIDs.igneousCobblestoneName.block(), 1, metadata & 7);
    }

    @Override
    public boolean hasRareDrops(){
        return false;
    }

    @Override
    public String getBlockName(int index){
        return super.getBlockName(index) + "Cobble";
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.igneousCobblestoneName.ID());
    }

}
