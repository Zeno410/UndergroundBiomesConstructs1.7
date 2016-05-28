package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaItem;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;

public class BlockMetamorphicStone extends BlockMetadataBase
{
    private static final float[] hardness = {1.1f, 1.0f, 1.1f, 1.3f, 0.7f, 0.7f, 0.4f, 0.9f};
    private static final float[] resistance = {1.11f, 1.0f, 1.11f, 1.26f, 0.54f, 0.54f, 0.2f, 0.86f};
    public static final String[] blockName = {
        "gneiss", "eclogite", "marble", "quartzite", "blueschist", "greenschist", "soapstone", "migmatite"
    };


    public BlockMetamorphicStone(){
        this(UBIDs.metamorphicStoneName);
    }

    public BlockMetamorphicStone(NamedBlock namer){
        super(namer);
        this.setHardness(1.5F*UndergroundBiomes.hardnessModifier())
                .setResistance(1.66F*UndergroundBiomes.resistanceModifier());
            ubExplosionResistance = blockResistance;
    }
    @Override
    public float getBlockHardness(int meta){
        return super.getBlockHardness(meta) * hardness[meta];
    }

    public float getBlockExplosionResistance(int meta){
        float result = super.getBlockExplosionResistance(meta) * resistance[meta];
        return result;
    }


    public ItemStack itemDropped(int metadata, Random random, int fortune, int y)
    {
        // Very rare drops
        if ((metadata < 8) && (random.nextInt(1024) <= fortune))
        {
            if ((y < 31) && (random.nextInt(3) == 0))
            {
                // Lapis lazuli
                return new ItemStack(NamedVanillaItem.dyePowder.cachedItem(), 1, 4);
            }
            if ((y < 16) && (random.nextInt(3) == 0))
            {
                // Redstone
                return new ItemStack(NamedVanillaItem.redstone.cachedItem(), 1, 0);
            }
        }
        return new ItemStack(UBIDs.metamorphicCobblestoneName.block(), 1, metadata & 7);
    }

    public boolean hasRareDrops()
    {
        return true;
    }

    public String getBlockTypeName(int index)
    {
        return blockName[index & 7];
    }

    public String getBlockName(int index) {
        return getBlockTypeName(index);
    }
        @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.metamorphicCobblestoneName.ID());
    }
}
