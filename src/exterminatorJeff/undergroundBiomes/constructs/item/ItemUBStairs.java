/*
 * Author Zeno410
 */

package exterminatorJeff.undergroundBiomes.constructs.item;

import java.util.ArrayList;

import net.minecraft.block.Block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedItem;
import net.minecraft.item.ItemStack;

public class ItemUBStairs extends ItemUndergroundBiomesConstruct {

    public static ArrayList<ItemUBStairs> instances = new ArrayList<ItemUBStairs>();

    public ItemUBStairs(Block block) {
        this(block,UBIDs.UBStairsItemName);
    }
    
    public ItemUBStairs(Block block,NamedItem namer){
        super(block,namer);
        instances.add(this);
    }

    public String groupName() {return "stairs";}

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack stairs)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int i1 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) ;
        int metadata = stairs.getItemDamage();

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2 | i1 | metadata, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1 | i1| metadata, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3 | i1| metadata, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0 | i1| metadata, 2);
        }

        throw new RuntimeException("" + metadata + " " + (0 | i1| metadata));
    }
}
