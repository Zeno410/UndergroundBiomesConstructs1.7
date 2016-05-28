
package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBWallBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Zeno410
 */
public class UBWallBlockGroup extends UBWallGroup {

    private UBWall igneousStoneWall;
    private UBWall igneousCobblestoneWall;
    private UBWall igneousStoneBrickWall;
    private UBWall metamorphicStoneWall;
    private UBWall metamorphicCobblestoneWall;
    private UBWall metamorphicStoneBrickWall;
    private UBWall sedimentaryStoneWall;

    public void define() {
       igneousStoneWall = createWall(UndergroundBiomes.igneousStone);
       igneousCobblestoneWall= createWall(UndergroundBiomes.igneousCobblestone);
       igneousStoneBrickWall= createWall(UndergroundBiomes.igneousStoneBrick);
       metamorphicStoneWall= createWall(UndergroundBiomes.metamorphicStone);
       metamorphicCobblestoneWall= createWall(UndergroundBiomes.metamorphicCobblestone);
       metamorphicStoneBrickWall= createWall(UndergroundBiomes.metamorphicStoneBrick);
       sedimentaryStoneWall= createWall(UndergroundBiomes.sedimentaryStone);
    }

    private Block wallFor(UndergroundBiomesBlock base) {
        if (base.ubBlock == UndergroundBiomes.igneousStone) return igneousStoneWall;
        if (base.ubBlock == UndergroundBiomes.igneousCobblestone) return igneousCobblestoneWall;
        if (base.ubBlock == UndergroundBiomes.igneousStoneBrick) return igneousStoneBrickWall;
        if (base.ubBlock == UndergroundBiomes.metamorphicStone) return metamorphicStoneWall;
        if (base.ubBlock == UndergroundBiomes.metamorphicCobblestone) return metamorphicCobblestoneWall;
        if (base.ubBlock == UndergroundBiomes.metamorphicStoneBrick) return metamorphicStoneBrickWall;
        if (base.ubBlock == UndergroundBiomes.sedimentaryStone) return sedimentaryStoneWall;
        throw new RuntimeException(base.ubBlock.getUnlocalizedName());
    }

    private UBWall createWall(BlockMetadataBase sourceBlock) {
        NamedBlock createdNamer = new NamedBlock(UBIDs.UBWallsName.internal()+"."+sourceBlock.getUnlocalizedName());
        UBWall created = new UBWall(sourceBlock);
        createdNamer.gameRegister(created, ItemUBWallBlock.class);
        return created;
    }

    @Override
    public ProductItemDefiner productItemDefiner(int index) {
        return new BlockProductItemDefiner(index);
    }

    class BlockProductItemDefiner extends ProductItemDefiner {

        BlockProductItemDefiner(int _stoneIndex) {
            super(_stoneIndex);
        }

        private UndergroundBiomesBlock ubBlock() {
            return UndergroundBiomesBlockList.indexed(stoneIndex);
        }

        private Block product() {
            return wallFor(ubBlock());
        }

        private int productMetadata() {
            return ubBlock().metadata;
        }
        @Override
        public final ItemStack stackOf(int items) {

            return new ItemStack(product(),items,productMetadata());
        }
    }
}
