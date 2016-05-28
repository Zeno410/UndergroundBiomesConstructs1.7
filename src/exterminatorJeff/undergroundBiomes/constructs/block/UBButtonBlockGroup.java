package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBButtonBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Zeno410
 */
public class UBButtonBlockGroup extends UBButtonGroup {
    private UBButtonSet igneousStoneButtonSet;
    private UBButtonSet igneousCobblestoneButtonSet;
    private UBButtonSet igneousStoneBrickButtonSet;
    private UBButtonSet metamorphicStoneButtonSet;
    private UBButtonSet metamorphicCobblestoneButtonSet;
    private UBButtonSet metamorphicStoneBrickButtonSet;
    private UBButtonSet sedimentaryStoneButtonSet;

    @Override
    public void define(int _constructID) {define();}

    public void define() {
       igneousStoneButtonSet = createButtonSet(UndergroundBiomes.igneousStone);
       igneousCobblestoneButtonSet= createButtonSet(UndergroundBiomes.igneousCobblestone);
       igneousStoneBrickButtonSet= createButtonSet(UndergroundBiomes.igneousStoneBrick);
       metamorphicStoneButtonSet= createButtonSet(UndergroundBiomes.metamorphicStone);
       metamorphicCobblestoneButtonSet= createButtonSet(UndergroundBiomes.metamorphicCobblestone);
       metamorphicStoneBrickButtonSet= createButtonSet(UndergroundBiomes.metamorphicStoneBrick);
       sedimentaryStoneButtonSet= createButtonSet(UndergroundBiomes.sedimentaryStone);
    }

    private UBButtonSet createButtonSet(BlockMetadataBase sourceBlock) {
        return new UBButtonSet(sourceBlock);
    }

    private UBButtonSet ButtonSetFor(UndergroundBiomesBlock base) {
        if (base.ubBlock == UndergroundBiomes.igneousStone) return igneousStoneButtonSet;
        if (base.ubBlock == UndergroundBiomes.igneousCobblestone) return igneousCobblestoneButtonSet;
        if (base.ubBlock == UndergroundBiomes.igneousStoneBrick) return igneousStoneBrickButtonSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicStone) return metamorphicStoneButtonSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicCobblestone) return metamorphicCobblestoneButtonSet;
        if (base.ubBlock == UndergroundBiomes.metamorphicStoneBrick) return metamorphicStoneBrickButtonSet;
        if (base.ubBlock == UndergroundBiomes.sedimentaryStone) return sedimentaryStoneButtonSet;
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
            UBButtonSet ButtonSet = ButtonSetFor(ubBlock());
            if (ubBlock().metadata<8) return ButtonSet.buttons[ubBlock().metadata];
            throw new RuntimeException();
        }

        private int productMetadata() {
            return ubBlock().metadata;
        }
        
        @Override
        public final ItemStack stackOf(int items) {

            return new ItemStack(product(),items,productMetadata());
        }
    }

    private static class UBButtonSet {
        // a group of UBButton blocks covering one type of stone
        UBButton [] buttons = new UBButton[8];

        public UBButtonSet(BlockMetadataBase sourceBlock) {
            buttons[0] = createButton(sourceBlock,0);
            buttons[1] = createButton(sourceBlock,1);
            buttons[2] = createButton(sourceBlock,2);
            buttons[3] = createButton(sourceBlock,3);
            buttons[4] = createButton(sourceBlock,4);
            buttons[5] = createButton(sourceBlock,5);
            buttons[6] = createButton(sourceBlock,6);
            buttons[7] = createButton(sourceBlock,7);
        }

        private UBButton createButton(BlockMetadataBase sourceBlock, int lowerMetadata) {
            NamedBlock createdNamer = new NamedBlock(UBIDs.UBButtonName.internal()+"."+sourceBlock.getUnlocalizedName()+lowerMetadata);
            UBButton created = new UBButton(sourceBlock,lowerMetadata);
            createdNamer.gameRegister(created, ItemUBButtonBlock.class);
        return created;
        }
    }

}