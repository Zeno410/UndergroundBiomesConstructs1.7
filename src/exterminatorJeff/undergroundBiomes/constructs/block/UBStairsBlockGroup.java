
package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBStairsBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Zeno410
 */
public class UBStairsBlockGroup extends UBStairsGroup {
    private UBStairsSet igneousStoneStairsSet;
    private UBStairsSet igneousCobblestoneStairsSet;
    private UBStairsSet igneousStoneBrickStairsSet;
    private UBStairsSet metamorphicStoneStairsSet;
    private UBStairsSet metamorphicCobblestoneStairsSet;
    private UBStairsSet metamorphicStoneBrickStairsSet;
    private UBStairsSet sedimentaryStoneStairsSet;

    @Override
    public void define(int _constructID) {define();}

    public void define() {
       igneousStoneStairsSet = createStairsSet(UndergroundBiomes.igneousStone);
       igneousCobblestoneStairsSet= createStairsSet(UndergroundBiomes.igneousCobblestone);
       igneousStoneBrickStairsSet= createStairsSet(UndergroundBiomes.igneousStoneBrick);
       metamorphicStoneStairsSet= createStairsSet(UndergroundBiomes.metamorphicStone);
       metamorphicCobblestoneStairsSet= createStairsSet(UndergroundBiomes.metamorphicCobblestone);
       metamorphicStoneBrickStairsSet= createStairsSet(UndergroundBiomes.metamorphicStoneBrick);
       sedimentaryStoneStairsSet= createStairsSet(UndergroundBiomes.sedimentaryStone);
    }

    private UBStairsSet createStairsSet(BlockMetadataBase sourceBlock) {
        return new UBStairsSet(sourceBlock);
    }

    private UBStairsSet stairsSetFor(UndergroundBiomesBlock base) {
        if (base.ubBlock == UndergroundBiomes.igneousStone) return igneousStoneStairsSet;
        if (base.ubBlock == UndergroundBiomes.igneousCobblestone) return igneousCobblestoneStairsSet;
        if (base.ubBlock == UndergroundBiomes.igneousStoneBrick) return igneousStoneBrickStairsSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicStone) return metamorphicStoneStairsSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicCobblestone) return metamorphicCobblestoneStairsSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicStoneBrick) return metamorphicStoneBrickStairsSet;
        if (base.ubBlock == UndergroundBiomes.sedimentaryStone) return sedimentaryStoneStairsSet;
        throw new RuntimeException(base.ubBlock.getUnlocalizedName());
    }

        @Override
    public ProductItemDefiner productItemDefiner(int index) {
        return new BlockProductItemDefiner(index);
    }

    class BlockProductItemDefiner extends ProductItemDefiner {

        BlockProductItemDefiner(int _stoneIndex) {
            super(_stoneIndex);
        }

        private final UndergroundBiomesBlock ubBlock() {
            return UndergroundBiomesBlockList.indexed(stoneIndex);
        }

        private Block product() {
            UBStairsSet stairsSet = stairsSetFor(ubBlock());
            if (ubBlock().metadata<2) return stairsSet.zeroOne;
            if (ubBlock().metadata<4) return stairsSet.twoThree;
            if (ubBlock().metadata<6) return stairsSet.fourFive;
            if (ubBlock().metadata<8) return stairsSet.sixSeven;
            throw new RuntimeException();
        }

        private int productMetadata() {
            int result = ubBlock().metadata%2*8;
            if ((result !=0)&&result!=8) throw new RuntimeException();
            return ubBlock().metadata%2*8;
        }
        @Override
        public final ItemStack stackOf(int items) {

            return new ItemStack(product(),items,productMetadata());
        }
    }

    private static class UBStairsSet {
        // a group of UBStairs blocks covering one type of stone
        UBStairs zeroOne;
        UBStairs twoThree;
        UBStairs fourFive;
        UBStairs sixSeven;

        public UBStairsSet(BlockMetadataBase sourceBlock) {
            zeroOne = createStairs(sourceBlock,0);
            twoThree = createStairs(sourceBlock,2);
            fourFive = createStairs(sourceBlock,4);
            sixSeven = createStairs(sourceBlock,6);
        }

        private UBStairs createStairs(BlockMetadataBase sourceBlock, int lowerMetadata) {
            NamedBlock createdNamer = new NamedBlock(UBIDs.UBStairsName.internal()+"."+sourceBlock.getUnlocalizedName()+lowerMetadata/2);
            UBStairs created = new UBStairs(sourceBlock,lowerMetadata);
            createdNamer.gameRegister(created, ItemUBStairsBlock.class);
        return created;
        }
    }

}
