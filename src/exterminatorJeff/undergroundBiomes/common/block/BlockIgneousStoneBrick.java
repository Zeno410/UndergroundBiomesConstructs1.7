package exterminatorJeff.undergroundBiomes.common.block;

import Zeno410Utils.Acceptor;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockIgneousStoneBrick extends BlockIgneousStone
{

    public BlockIgneousStoneBrick()
    {
        super(UBIDs.igneousStoneBrickName);
        replaceableByOre = false;
        baseHardness = this.blockHardness;
        UndergroundBiomes.instance().settings().brickHardnessMultiplier.informOnChange(hardnessUpdater);
        hardnessUpdater.accept(UndergroundBiomes.instance().settings().brickHardnessMultiplier.value());
    }
    final float baseHardness;
    private final Acceptor<Double> hardnessUpdater = new Acceptor<Double>() {

        @Override
        public void accept(Double accepted) {
            setHardness(baseHardness*accepted.floatValue());
        }

    };

    public ItemStack itemDropped(int metadata, Random random, int fortune, int y)
    {
        return new ItemStack(UBIDs.igneousStoneBrickName.block(), 1, metadata & 7);
    }

    public boolean hasRareDrops()
    {
        return false;
    }

    public String getBlockName(int index) {
        return super.getBlockName(index) + "Brick";
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.igneousStoneBrickName.ID());
    }
}
