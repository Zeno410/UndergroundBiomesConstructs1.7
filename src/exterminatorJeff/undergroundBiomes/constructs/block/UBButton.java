
package exterminatorJeff.undergroundBiomes.constructs.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import java.util.List;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author Zeno410
 */
public class UBButton extends BlockButton {
    final BlockMetadataBase baseStone;
    final int lowerMetadata;
    IIcon currentIcon;
    public boolean renderingItem;
    static final int materialFace = 2;

    UBButton(BlockMetadataBase material, int lowerMetadata) {
        super(true);
        baseStone = material;
        this.lowerMetadata = lowerMetadata;
        this.setCreativeTab(UndergroundBiomes.tabModBlocks);
    }
    public final int blockMetadata(int worldMetadata) {
        return lowerMetadata;
    }
    @Override
    public void registerBlockIcons(IIconRegister arg0) {
        //super.registerBlockIcons(arg0);
    }
    @Override
    public int getRenderColor(int metadata) {
        currentIcon =  baseStone.getIcon(materialFace, lowerMetadata());
        return super.getRenderColor(metadata);
    }

    public final int itemMetadata(int itemDamage) {
        return itemDamage;
    }

    public BlockMetadataBase baseStone() {return baseStone;}
    public int lowerMetadata() {return lowerMetadata;}
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata){
        currentIcon =  baseStone.getIcon(materialFace, lowerMetadata());
        // world metadata: stair status plus 8 if upper metadata
        /*if (metadata>7) {
            return this.baseStone.getIcon(side, lowerMetadata()+1);
        }
        throw new RuntimeException("metadata "+metadata);*/
        //return this.baseStone.getIcon(side, lowerMetadata());
        return this.currentIcon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

        @Override
    public IIcon func_149735_b(int side, int metadata) {
        //if (metadata <8) throw new RuntimeException("metadata "+metadata);
        //if (metadata > 0) return baseStone.getIcon(side, lowerMetadata()+1);
        return this.baseStone.getIcon(side, blockMetadata(metadata));
    }

    @Override
    public void getSubBlocks(Item id, CreativeTabs tabs, List list){
        list.add(new ItemStack(id, 1, 0));
    }
    /*@Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_){
        this.baseStone.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, blockMetadata(p_149664_5_));
    }*/
}
