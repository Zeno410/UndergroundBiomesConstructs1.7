package exterminatorJeff.undergroundBiomes.constructs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.api.NamedItem;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.block.UBStairs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 *
 * @author Zeno410
 */
public class ItemUBStairsBlock extends ItemMultiTexture {
    private UBStairs ubStairs;
    private NamedItem name;
    protected String[] names;
    public static int top = 2;

    private static String[] names(Block appearance, NamedItem name,int metadata) {
        String [] result = new String[2];
        for (int i = metadata ; i < metadata + 2; i++) {
            BlockMetadataBase sourceBlock =((UBStairs)appearance).baseStone();
            result[i-metadata] = sourceBlock.getBlockTypeName(i)+"."+ name.internal();
        }
        return result;
    }

    public ItemUBStairsBlock(Block ubBlock) {
        this(ubBlock, UBIDs.UBStairsItemName);
    }

    public int getMetatata(int metadata) {
        if (metadata == 0) throw new RuntimeException();
        return metadata;}

    public ItemUBStairsBlock(Block ubBlock, NamedItem name) {
        //super(_structure, appearance,names(appearance,_name));
        super(ubBlock,null,null);
        //this(Blocks.cobblestone_wall, ubBlock,names(ubBlock,name));
        ubStairs = (UBStairs)ubBlock;

        this.names = names(ubBlock,name,ubStairs.lowerMetadata());
        this.name = name;
        this.setUnlocalizedName(name.internal());
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        //this.setUnlocalizedName(_name.external());
        //bFull3D = true;
    }
   @Override
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        currentColor = getIconFromDamage(itemStack.getItemDamage());
        return super.getColorFromItemStack(itemStack, par2);
    }

   
    public static IIcon currentColor;

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {

        //return this.getIconIndex(stack);
        return currentColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return currentColor;
        //return getIconFromDamage(par2);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return super.getDamage(stack);
    }


    @Override
    public int getDisplayDamage(ItemStack stack) {
        return super.getDisplayDamage(stack);
    }
           
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack itemStack) {
        return getIconFromDamage(itemStack.getItemDamage());

    }


    @Override
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        IIcon result = ubStairs.baseStone().getIcon(top, ubStairs.blockMetadata(par1));//
        currentColor = result;
        ubStairs.currentIcon = result;
        ubStairs.renderingItem = true;
        return result;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return getIconIndex(usingItem);
    }

    int nameReference(int metadata) {
        return metadata>>3;
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return  ubStairs.baseStone().getUnlocalizedName()+"." + (String)this.names[nameReference(stack.getItemDamage())];
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

   
}
