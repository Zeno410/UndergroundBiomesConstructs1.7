package exterminatorJeff.undergroundBiomes.common.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemSlab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import exterminatorJeff.undergroundBiomes.common.block.BlockStoneSlab;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
/**
 *
 * @author Zeno410
 */
public class ItemMetadataSlab extends ItemSlab {

    private final BlockMetadataBase referenceBlock;
    private final boolean isDouble;

    public static BlockStoneSlab slab(Block block) {
        return (BlockStoneSlab)block;
    }

    public static BlockSlab singleSlab(Block one, Block two) {
        if (slab(one).isDoubleSlab()) return (BlockSlab)two;
        return (BlockSlab)one;
    }

    public static BlockSlab doubleSlab(Block one, Block two) {
        if (slab(one).isDoubleSlab()) return (BlockSlab)one;
        return (BlockSlab)two;
    }

    public static BlockMetadataBase stone(Block block) {
        return slab(block).referenceBlock;
    }

    public ItemMetadataSlab(Block slab, BlockStoneSlab otherSlab) {
        super(slab,singleSlab(slab,otherSlab), doubleSlab(slab,otherSlab), slab(slab).isDoubleSlab());
        referenceBlock = stone(slab);
        isDouble = slab(slab).isDoubleSlab();
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        if (!isDouble) {
             super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta){
        return referenceBlock.getIcon(2, meta);
    }

    @Override
    public String getUnlocalizedName(ItemStack arg0) {
        return super.getUnlocalizedName(arg0);
    }


}
