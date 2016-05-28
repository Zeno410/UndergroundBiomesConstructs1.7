package exterminatorJeff.undergroundBiomes.common.block;

import Zeno410Utils.Acceptor;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class BlockIgneousCobblestone extends BlockIgneousStone
{
    public BlockIgneousCobblestone() {
        super(UBIDs.igneousCobblestoneName);
        baseHardness = this.blockHardness;
        this.setHardness(this.blockHardness*1.333333f);
        replaceableByOre = false;
        UndergroundBiomes.instance().settings().cobbleHardnessMultiplier.informOnChange(hardnessUpdater);
        hardnessUpdater.accept(UndergroundBiomes.instance().settings().cobbleHardnessMultiplier.value());
    }
    final float baseHardness;
    private final Acceptor<Double> hardnessUpdater = new Acceptor<Double>() {

        @Override
        public void accept(Double accepted) {
            setHardness(baseHardness*accepted.floatValue());
        }

    };
    @Override
    public ItemStack itemDropped(int metadata, Random random, int fortune, int y) {
        return new ItemStack(UBIDs.igneousCobblestoneName.block(), 1, metadata & 7);
    }

    @Override
    public boolean hasRareDrops(){
        return false;
    }

    @Override
    public String getBlockName(int index){
        return super.getBlockName(index) + "Cobble";
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemById(UBIDs.igneousCobblestoneName.ID());
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return super.getIcon(side, metadata);
    }


}
