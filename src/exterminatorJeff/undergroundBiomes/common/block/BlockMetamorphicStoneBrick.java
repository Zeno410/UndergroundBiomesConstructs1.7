package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import exterminatorJeff.undergroundBiomes.api.UBIDs;

public class BlockMetamorphicStoneBrick extends BlockMetamorphicStone
{
    public BlockMetamorphicStoneBrick(){
        super(UBIDs.metamorphicStoneBrickName);
        this.replaceableByOre = false;
    }
    
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y){
        return new ItemStack(UBIDs.metamorphicStoneBrickName.block(), 1, metadata & 7);
    }

    @Override
    public boolean hasRareDrops(){
        return false;
    }
    
    public String getBlockName(int index) {
        return super.getBlockName(index) + "Brick";
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.metamorphicStoneBrickName.ID());
    }
}
