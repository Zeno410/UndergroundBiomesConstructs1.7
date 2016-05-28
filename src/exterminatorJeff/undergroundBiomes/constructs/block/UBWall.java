/**
 *
 * @author Zeno410
 */

package exterminatorJeff.undergroundBiomes.constructs.block;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;

import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import Zeno410Utils.Zeno410Logger;
import java.util.logging.Logger;
import net.minecraft.client.renderer.texture.IIconRegister;

public class UBWall extends BlockWall {

    private int storedID;
    public static Logger logger = new Zeno410Logger("UBWallBase").logger();
    private final BlockMetadataBase baseBlock;

    public UBWall(BlockMetadataBase _baseBlock) {
        super(_baseBlock);
        baseBlock = _baseBlock;
        this.isBlockContainer = false;
        this.setCreativeTab(UndergroundBiomes.tabModBlocks);
        this.setBlockName("wall");
    }
    
    @Override
    public void registerBlockIcons(IIconRegister arg0) {
        //super.registerBlockIcons(arg0);
    }
    public BlockMetadataBase baseBlock() {return baseBlock;}

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        if (!(UndergroundBiomes.wallsOn())) return;
        for (int i = 0; i < BlockMetadataBase.metadatas; i++){
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z){
        return world.getBlockMetadata(x,y,z);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z){
        return this.baseBlock.getBlockHardness(world, x, y, z);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return this.baseBlock.getIcon(side, metadata);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        return this.baseBlock.getIcon(world, x,y,z, side);
    }


    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target){
        return false;
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z,
            double explosionX, double explosionY, double explosionZ){
        return this.baseBlock.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public int getRenderType(){return 32;}

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    public ItemStack itemDropped(int metadata, Random random, int fortune, int y){
        return new ItemStack(this, 1, metadata);
    }

    public String getBlockName(int meta) {
        return this.baseBlock.getBlockName(meta);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

}
