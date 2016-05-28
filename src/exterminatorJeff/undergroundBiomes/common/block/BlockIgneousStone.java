package exterminatorJeff.undergroundBiomes.common.block;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaItem;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockIgneousStone extends BlockMetadataBase
{
    private static final float[] hardness = {1.7f, 1.6f, 1.3f, 1.4f, 1.0f, 1.4f, 1.5f, 1.2f};
    private static final float[] resistance = {1.42f, 1.39f, 1.26f, 1.31f, 1.0f, 1.31f, 1.35f, 1.2f};
    public static final String[] blockName = {
        "redGranite", "blackGranite", "rhyolite", "andesite", "gabbro", "basalt", "komatiite", "dacite"
    };

    public BlockIgneousStone(){
        this(UBIDs.igneousStoneName);
    }
    
    public BlockIgneousStone(NamedBlock namer){
        super(namer);
        this.setHardness(1.5F*UndergroundBiomes.hardnessModifier())
                .setResistance(1.66F*UndergroundBiomes.resistanceModifier());
            ubExplosionResistance = blockResistance;
    }

    @Override
    public float getBlockHardness(int meta){
        return super.getBlockHardness(meta) * hardness[meta];
    }

    @Override
    public float getBlockExplosionResistance(int meta){
        return super.getBlockExplosionResistance(meta) * resistance[meta];
    }

    @Override
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y){
        // Very rare drops
        if ((metadata < 8) && (random.nextInt(1024) <= fortune))
        {
            int num = UndergroundBiomes.nuggets.size();
            if (num > 0)
            {
                ItemStack stack = UndergroundBiomes.nuggets.get(random.nextInt(num));
                if ((NamedVanillaItem.goldNugget.matches(stack.getItem())) || (y < 32))
                {
                    return stack;
                }
            }
        }
        return new ItemStack(UBIDs.igneousCobblestoneName.block(), 1, metadata & 7);
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.igneousCobblestoneName.ID());
    }

    public boolean hasRareDrops(){
        return true;
    }

    public String getBlockTypeName(int index)
    {
        return blockName[index & 7];
    }
}
