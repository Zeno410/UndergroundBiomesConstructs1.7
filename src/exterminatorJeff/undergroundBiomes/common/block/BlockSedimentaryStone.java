package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.ItemStack;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaItem;
import exterminatorJeff.undergroundBiomes.common.item.ItemFossilPiece;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockSedimentaryStone extends BlockMetadataBase
{
    private static final float[] hardness = {0.5f, 0.5f, 0.5f, 0.6f, 0.5f, 0.5f, 1.0f, 0.9f};
    private static final float[] resistance = {0.29f, 0.29f, 0.29f, 0.4f, 0.29f, 0.29f, 1.0f, 0.86f};
    public static final String[] blockName = {
        "limestone", "chalk", "shale", "siltstone", "ligniteBlock", "dolomite", "greywacke", "chert"
    };

    public BlockSedimentaryStone(){
        this(UBIDs.sedimentaryStoneName);
    }

    public BlockSedimentaryStone(NamedBlock namer){
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
        float result =  super.getBlockExplosionResistance(meta) * resistance[meta];
        return result;
    }

    public ItemStack itemDropped(int metadata, Random random, int fortune, int y)
    {
        // Rare drops
        if ((metadata < 8) && (random.nextInt(64) <= fortune))
        {
            // Shale drops clay
            if (metadata == 2)
            {
                return new ItemStack(NamedVanillaItem.clay.cachedItem(), 1, 0);
            }
            // Limestone, chalk, siltstone, lignite and dolomite drop fossil pieces
            if (metadata == 0 || metadata == 1 || metadata == 3 || metadata == 4 || metadata == 5)
            {
                return new ItemStack(UBIDs.fossilPieceName.cachedItem(), 1, random.nextInt(ItemFossilPiece.TYPES));
            }
            // Chert drops flint item
            if (metadata == 7)
            {
                return new ItemStack(NamedVanillaItem.flint.cachedItem(), 1, 0);
            }
        }
        if ((metadata & 7) == 4) return new ItemStack(UBIDs.ligniteCoalName.cachedItem(), 1, 0);
        return new ItemStack(UBIDs.sedimentaryStoneName.block(), 1, metadata & 7);
    }

    public boolean hasRareDrops()
    {
        return true;
    }

    public String getBlockTypeName(int index)
    {
        return blockName[index & 7];
    }
}
