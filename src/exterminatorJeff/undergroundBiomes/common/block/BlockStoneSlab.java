package exterminatorJeff.undergroundBiomes.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import exterminatorJeff.undergroundBiomes.api.NamedItem;
import exterminatorJeff.undergroundBiomes.api.NamedSlabPair;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockStoneSlab extends BlockSlab {
    //private IIcon[] textures;
    protected final boolean isDoubleSlab;
    public final BlockMetadataBase referenceBlock;
    private final NamedItem halfSlabNamer;
    
    public BlockStoneSlab(boolean isDouble, Block refBlock,NamedSlabPair pairName){
        super(isDouble, refBlock.getMaterial());
        this.setCreativeTab(isDouble ? null : UndergroundBiomes.tabModBlocks);
        isDoubleSlab = isDouble;
        referenceBlock = (BlockMetadataBase)refBlock;
        this.useNeighborBrightness = true;
        halfSlabNamer= new NamedItem(pairName.half);
    }

    @Override
    public float getBlockHardness(World par1World, int x, int y, int z) {
        float result =  referenceBlock.getBlockHardness(par1World, x, y, z);
        if (this.isDoubleSlab()) return result;
        return result/2;

    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        return getHalfSlab();
    }

    public String func_150002_b(int meta) {
        return referenceBlock.getUnlocalizedName() + "Slab."+referenceBlock.getBlockTypeName(meta);      
    }


    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return referenceBlock.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    protected ItemStack createStackedBlock(int metadata){
        return new ItemStack(halfSlabNamer.cachedItem(), 2, metadata & 7);
    }

    public boolean isDoubleSlab() {return isDoubleSlab;}

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int meta) {
        return referenceBlock.textures[meta & 7];
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list){
        if (isDoubleSlab) return;
        for (int i = 0; i < 8; i++){

            list.add(new ItemStack(item, 1, i));
        }
    }

    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) & 7;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
        //textures = new IIcon[8];
        for(int i = 0; i < 8; i++)
        {
           // textures[i] = iconRegister.registerIcon("undergroundbiomes:" + referenceBlock.getBlockName(i));
        }
    }

    public String getFullSlabName(int index){
        return super.getUnlocalizedName() + "." + referenceBlock.getBlockTypeName(index);
    }

    public Item getHalfSlab() {
        return halfSlabNamer.registeredItem();

    }
    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return getHalfSlab();
    }

    public int slabsDropped() {
        if (this.isDoubleSlab) {return 2;} else {return 1;}
    }

    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target){
        return false;
    }
     @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        Item item = getItemDropped(metadata, world.rand, fortune);
        if (item != null) {
            ret.add(new ItemStack(item, slabsDropped(),metadata&7));
        }
        return ret;
    }
}
