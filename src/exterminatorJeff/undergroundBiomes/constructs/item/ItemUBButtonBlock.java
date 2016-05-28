package exterminatorJeff.undergroundBiomes.constructs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.api.NamedItem;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.block.UBButton;
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
public class ItemUBButtonBlock extends ItemMultiTexture {
    private UBButton ubButton;
    private NamedItem name;
    protected String names;
    public static int top = 2;

    private static String names(Block appearance, NamedItem name,int metadata) {
        String result = new String();
        BlockMetadataBase sourceBlock =((UBButton)appearance).baseStone();
        result = sourceBlock.getBlockTypeName(metadata)+"."+ name.internal();
        return result;
    }

    public ItemUBButtonBlock(Block ubBlock) {
        this(ubBlock, UBIDs.UBButtonItemName);
    }

    public int getMetatata(int metadata) {
        return metadata;}

    public ItemUBButtonBlock(Block ubBlock, NamedItem name) {
        //super(_structure, appearance,names(appearance,_name));
        super(ubBlock,null,null);
        //this(Blocks.cobblestone_wall, ubBlock,names(ubBlock,name));
        ubButton = (UBButton)ubBlock;

        this.names = names(ubBlock,name,ubButton.lowerMetadata());
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
        return this.getIconIndex(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return getIconFromDamage(par2);
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
        //if (par1>0) throw new RuntimeException();
        //return Blocks.cobblestone.getIcon(1, 0);
        return ubButton.baseStone().getIcon(top, ubButton.blockMetadata(par1));//
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
        return  ubButton.baseStone().getUnlocalizedName()+"." + (String)this.names;
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }


}
