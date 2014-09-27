
package exterminatorJeff.undergroundBiomes.common.item;

/**
 *
 * @author Zeno410
 */
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
import exterminatorJeff.undergroundBiomes.common.block.BlockOverlay;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBOre;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemUBOreBlock  extends ItemBlock {
    private static HashMap<String,ItemMetadataBlock> namedBlocks = new HashMap<String,ItemMetadataBlock>();
    private BlockUBOre theBlock;

    public static ItemMetadataBlock itemFrom(NamedBlock namer) {
        return namedBlocks.get(new NamedItem(namer).internal());
    }

    public ItemUBOreBlock(Block block){
        super(block);
        BlockUBOre baseBlock = (BlockUBOre)block;
        this.theBlock = baseBlock;
        //itemName.register(namedBlock.ID(), this);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta){
        return theBlock.getIcon(2, meta);
    }


    @Override
    public String getItemStackDisplayName(ItemStack stack) {

        return theBlock.getDisplayName(stack.getItemDamage());
        //String result =  super.getItemStackDisplayName(par1ItemStack);
        //String lookup =  "tile." +result.split(":")[1];
        //String lookup = result;
        //return ("" + StatCollector.translateToLocal(lookup)).trim();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return theBlock.getUnlocalizedName(stack.getItemDamage());
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){
       metadata = stack.getItemDamage();
       if (!world.setBlock(x, y, z, theBlock, metadata, 3)){
           return false;
       }
       if (world.getBlock(x, y, z) == theBlock){
           theBlock.onBlockPlacedBy(world, x, y, z, player, stack);
           theBlock.onPostBlockPlaced(world, x, y, z, metadata);
       }
       return true;
    }

}

