package exterminatorJeff.undergroundBiomes.constructs.item;

import exterminatorJeff.undergroundBiomes.api.NamedItem;
import exterminatorJeff.undergroundBiomes.constructs.util.Reregistrable;
import exterminatorJeff.undergroundBiomes.common.block.UBStoneTextureProvider;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Zeno410Utils.Zeno410Logger;
import java.util.logging.Logger;

/**
 *
 * @author Zeno410
 */
public abstract class ItemUndergroundBiomesConstruct extends ItemMultiTexture {

    public static int NO_PRIOR_METADATA = 0;
    public static final int subBlocksPerBlock = 8;

    public static Logger logger = new Zeno410Logger("ConstructItems").logger();
    private static String[] names(UBStoneTextureProvider appearance, NamedItem name) {
        String [] result = new String[subBlocksPerBlock];
        for (int i = 0 ; i < subBlocksPerBlock; i++) {
            result[i] = appearance.getBlockTypeName(i)+"."+ name.internal();
        }
        return result;
    }


    public static final UBStoneTextureProvider appearance = new UBStoneTextureProvider();

    private Block structure;
    private NamedItem name;

    public ItemUndergroundBiomesConstruct(Block _structure, NamedItem _name) {
        //super(_structure, appearance,names(appearance,_name));
        super(_structure, appearance,names(appearance,_name));
        structure = _structure;
        name = _name;
        this.setUnlocalizedName(_name.internal());
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        //this.setUnlocalizedName(_name.external());
        //this.setUnlocalizedName("cobbleWall");
        //bFull3D = true;
    }


    public final UndergroundBiomesBlock ubBlock(int reference) {
        return UndergroundBiomesBlockList.indexed(reference);
    }

    @SideOnly(Side.CLIENT)
    
    public IIcon getIconFromDamage(int par1){
        IIcon field_150938_b = ubBlock(par1).icon();
        return field_150938_b != null ? field_150938_b : this.field_150939_a.getBlockTextureFromSide(1);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        currentColor = getIconFromDamage(par1ItemStack.getItemDamage());
        return super.getColorFromItemStack(par1ItemStack, par2);
    }

    public static IIcon currentColor;

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        
        return currentColor;
        //return super.getIcon(stack, pass);
    }

    public abstract String groupName();

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String result =  super.getItemStackDisplayName(par1ItemStack);
        //String lookup =  "tile." +result.split(":")[1];
        String lookup = result;
        return ("" + StatCollector.translateToLocal(lookup)).trim();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        //int meta = getMetadata(stack.getItemDamage());
        String unlocalizedname = ubBlock(stack.getItemDamage()).getUnlocalizedName();

        //String name = theBlock.getBlockName(meta);
        return unlocalizedname + "." + groupName();
    }

    public Block structure() {return this.structure;}

    public void testRegistration() {
        logger.info( structure().toString());
        logger.info( Block.getBlockById(Block.getIdFromBlock(structure())).toString());
        logger.info( ""+Item.getIdFromItem(this));
        logger.info(""+Block.getIdFromBlock(structure()));
        logger.info(""+Block.getIdFromBlock(structure()));
        logger.info( Item.getItemById(Block.getIdFromBlock(structure())).toString());
    }

/*
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage){
        UndergroundBiomesBlock source = ubBlock(damage);
        IIcon found = source.icon();
        //found = Blocks.cobblestone_wall.getBlockTextureFromSide(damage);
        //IIcon found = structure.getIcon(2, damage);
        //throw new RuntimeException();
        return found;
        //return theBlock.getIcon(2, getMetadata(damage));
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public IIcon getItemIcon() {
        throw new RuntimeException();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return this.getIconIndex(stack);
    }

    public IIcon getIconIndex(ItemStack item) {
        return this.getIconFromDamage(item.getItemDamage());
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return this.getIconFromDamage(par1);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.getIconIndex(stack);
    }

    public int getMetadata(int damage){ return damage;}

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage){
        UndergroundBiomesBlock source = ubBlock(damage);
        IIcon found = source.icon();
        //found = Blocks.cobblestone_wall.getBlockTextureFromSide(damage);
        //IIcon found = structure.getIcon(2, damage);
        //throw new RuntimeException();
        return found;
        //return theBlock.getIcon(2, getMetadata(damage));
    }

    private void setIconKludge(IIcon kludged) {
        //appearance.setIconKludge(kludged);
    }


    
    @Override
    public int getDamage(ItemStack stack) {
        int result = super.getDamage(stack);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            setIconKludge(getIconFromDamage(result));
        }
        return result;
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        int result = super.getDisplayDamage(stack);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            setIconKludge(getIconFromDamage(result));
        }
        return result;
    }



    @Override
    public void registerIcons(IIconRegister par1IconRegister){

    }



    public void testRenderItemIntoGUI(ItemStack par3ItemStack,boolean renderEffect) {
        int k = par3ItemStack.getItemDamage();
        Object object = par3ItemStack.getIconIndex();
        int l;
        float f;
        float f3;
        float f4;
        int rendertype = Block.getBlockFromItem(par3ItemStack.getItem()).getRenderType();
        if (par3ItemStack.getItemSpriteNumber() == 0 ){

            Block block = Block.getBlockFromItem(par3ItemStack.getItem());
            l = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);

        } else if (par3ItemStack.getItem().requiresMultipleRenderPasses()) {

            Item item = par3ItemStack.getItem();
            for (l = 0; l < item.getRenderPasses(k); ++l){
                IIcon iicon = item.getIcon(par3ItemStack, l);
                int i1 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, l);
                f = (float)(i1 >> 16 & 255) / 255.0F;
                float f1 = (float)(i1 >> 8 & 255) / 255.0F;
                float f2 = (float)(i1 & 255) / 255.0F;

                if (renderEffect && par3ItemStack.hasEffect(l)) {}
            }
        }  else {
            int number = par3ItemStack.getItemSpriteNumber();


            l = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);



            if (renderEffect && par3ItemStack.hasEffect(0))  {}
        }

    }
*/

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){

       int blockID = Block.getIdFromBlock(structure);
       if (blockID == -1) {
           ((Reregistrable)structure).reRegister();
       }


       int newMetadata = 0;//world.getBlockMetadata(x, y, z);
       newMetadata = structure.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, newMetadata);
       // the "metadata we've been passed is a ub index and doesn't go in the block
       if (!world.setBlock(x, y, z, structure, newMetadata, 3)){
           return false;
       }



       if (world.getBlock(x, y, z) == structure){
           structure.onBlockPlacedBy(world, x, y, z, player, stack);
           // stairs set some metadata and we need to get it 
           structure.onPostBlockPlaced(world, x, y, z, newMetadata);
       }

       return true;
    }
}
