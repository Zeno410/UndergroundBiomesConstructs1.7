package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import exterminatorJeff.undergroundBiomes.api.UBIDs;

public class BlockIgneousStoneBrick extends BlockIgneousStone
{
    public BlockIgneousStoneBrick()
    {
        super(UBIDs.igneousStoneBrickName);
        replaceableByOre = false;
    }
    
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y)
    {
        return new ItemStack(UBIDs.igneousStoneBrickName.block(), 1, metadata & 7);
    }

    public boolean hasRareDrops()
    {
        return false;
    }

    public String getBlockName(int index) {
        return super.getBlockName(index) + "Brick";
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.igneousStoneBrickName.ID());
    }
}
