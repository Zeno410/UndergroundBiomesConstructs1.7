package exterminatorJeff.undergroundBiomes.common.item;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.util.StatCollector;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedItem;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;

public class ItemMetadataBlock extends ItemBlock {
    private static HashMap<String,ItemMetadataBlock> namedBlocks = new HashMap<String,ItemMetadataBlock>();
    private BlockMetadataBase theBlock;

    public static ItemMetadataBlock itemFrom(NamedBlock namer) {
        return namedBlocks.get(new NamedItem(namer).internal());
    }

    public ItemMetadataBlock(Block block){
        super(block);
        BlockMetadataBase baseBlock = (BlockMetadataBase)block;
        this.theBlock = baseBlock;
        this.setUnlocalizedName(new NamedItem(baseBlock.namer).internal());
        //itemName.register(namedBlock.ID(), this);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        namedBlocks.put(new NamedItem(baseBlock.namer).internal(), this);
    }

    @Override
    public int getMetadata(int meta){
        return theBlock.hasRareDrops() ? meta | 8 : meta;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta){
        return theBlock.getIcon(2, meta);
    }


    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String result =  super.getItemStackDisplayName(par1ItemStack);
        //String lookup =  "tile." +result.split(":")[1];
        String lookup = result;
        return ("" + StatCollector.translateToLocal(lookup)).trim();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = theBlock.getBlockTypeName(stack.getItemDamage());
        return super.getUnlocalizedName() + "." + name;
    }
}
