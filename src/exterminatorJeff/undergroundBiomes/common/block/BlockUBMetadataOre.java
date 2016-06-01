
package exterminatorJeff.undergroundBiomes.common.block;

import Zeno410Utils.BlockState;
import Zeno410Utils.MinecraftName;
import Zeno410Utils.Mutable;
import Zeno410Utils.Zeno410Logger;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

/**
 *
 * @author Zeno410
 */
public class BlockUBMetadataOre extends BlockUBOre {

    public static Logger logger = new Zeno410Logger("BlockUBMetadataOre").logger();

    private final int oreMetadata;
    private IBlockAccess currentAccess;
    private IBlockAccess currentShamAccess;

    public BlockUBMetadataOre(BlockMetadataBase stone, BlockState ore, BlockOverlay overlay, 
            Mutable<Integer> renderIDSource, MinecraftName oreName) {
        super(stone,ore.block,overlay,renderIDSource,oreName);
        oreMetadata = ore.metadata;
    }
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        //logger.info("Getting drops "+oreMetadata);
        return ore.getDrops(world, x, y, z, oreMetadata, fortune);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
    }

    private IBlockAccess iBlockAccess(IBlockAccess toWrap) {
        if (toWrap != currentAccess) {
            currentAccess = toWrap;
            currentShamAccess = new ThisBlockAccess(toWrap);
        }
        return currentShamAccess;
    }

    @Override
    public void dropXpOnBlockBreak(World world, int x, int y, int z, int p_149657_5_) {
        int stoneMetadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, oreMetadata, 0);
        ore.dropXpOnBlockBreak(world, x, y, z, p_149657_5_);
        world.setBlockMetadataWithNotify(x, y, z, stoneMetadata, 0);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(world, x, y, z, oreMetadata, p_149690_6_, p_149690_7_);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int stoneMetadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, oreMetadata, 0);
        float result =  ore.getBlockHardness(world, x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, stoneMetadata, 0);
        return result;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
        return ore.getExpDrop(iBlockAccess(world), oreMetadata, fortune);
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        int stoneMetadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, oreMetadata, 0);
        float result =  ore.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
        world.setBlockMetadataWithNotify(x, y, z, stoneMetadata, 0);
        return result;
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return ore.getLightOpacity();
    }

    @Override
    public int getLightValue() {
        return ore.getLightValue();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return ore.getLightValue();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer p_149636_2_, int x, int y, int z, int p_149636_6_) {
        super.harvestBlock(world, p_149636_2_, x, y, z, oreMetadata);
    }
    
    @Override
    public int getHarvestLevel(int metadata) {
        return ore.getHarvestLevel(oreMetadata);
    }

    @Override
    public String getHarvestTool(int metadata) {
        return ore.getHarvestTool(oreMetadata);
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return ore.canHarvestBlock(player, oreMetadata);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {        int metadata = world.getBlockMetadata(x, y, z);
        float hardness = getBlockHardness(world, x, y, z);
        if (hardness < 0.0F)
        {
            return 0.0F;
        }

        if (!canHarvestBlock(player, metadata))
        {
            return player.getBreakSpeed(this, true, metadata, x, y, z) / hardness / 100F;
        }
        else
        {
            return player.getBreakSpeed(this, false, metadata, x, y, z) / hardness / 30F;
        }
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random p_149674_5_) {
        int stoneMetadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, oreMetadata, 0);
        ore.updateTick(world, x, y, z, p_149674_5_);
        world.setBlockMetadataWithNotify(x, y, z, stoneMetadata, 0);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random p_149734_5_) {
        int stoneMetadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, oreMetadata, 0);
        super.randomDisplayTick(world, x, y, z, p_149734_5_);
        world.setBlockMetadataWithNotify(x, y, z, stoneMetadata, 0);
    }
    
    private class ThisBlockAccess implements IBlockAccess {
        private final IBlockAccess wrapped;
        int x;
        int y;
        int z;

        ThisBlockAccess(IBlockAccess wrapped) {
            this.wrapped = wrapped;
        }

        public Block getBlock(int arg0, int arg1, int arg2) {
            if ((arg0 == x)&&(arg1== y)&&(arg2 == z)) return BlockUBMetadataOre.this;
            return wrapped.getBlock(arg0, arg1, arg2);
        }

        public TileEntity getTileEntity(int arg0, int arg1, int arg2) {
            return wrapped.getTileEntity(arg0, arg1, arg2);
        }

        public int getLightBrightnessForSkyBlocks(int arg0, int arg1, int arg2, int arg3) {
            return wrapped.getLightBrightnessForSkyBlocks(arg0, arg1, arg2, arg3);
        }

        public int getBlockMetadata(int arg0, int arg1, int arg2) {
            if ((arg0 == x)&&(arg1== y)&&(arg2 == z)) return oreMetadata;
            return wrapped.getBlockMetadata(arg0, arg1, arg2);
        }

        public boolean isAirBlock(int arg0, int arg1, int arg2) {
            return wrapped.isAirBlock(arg0, arg1, arg2);
        }

        public BiomeGenBase getBiomeGenForCoords(int arg0, int arg1) {
            return wrapped.getBiomeGenForCoords(arg0, arg1);
        }

        public int getHeight() {
            return wrapped.getHeight();
        }

        public boolean extendedLevelsInChunkCache() {
            return wrapped.extendedLevelsInChunkCache();
        }

        public int isBlockProvidingPowerTo(int arg0, int arg1, int arg2, int arg3) {
            return wrapped.isBlockProvidingPowerTo(arg0, arg1, arg2, arg3);
        }

        public boolean isSideSolid(int arg0, int arg1, int arg2, ForgeDirection arg3, boolean arg4) {
            return wrapped.isSideSolid(arg0, arg1, arg2, arg3, arg4);
        }

    }
}
