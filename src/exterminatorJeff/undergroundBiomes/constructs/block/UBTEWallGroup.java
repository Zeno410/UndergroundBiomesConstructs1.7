/**
 *
 * @author Zeno410
 */

package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBWall;

import net.minecraft.block.Block;


public class UBTEWallGroup  extends UBWallGroup {

    public UBTEWallGroup() {
        super();
    }

    private static UBWallBase constructBlock;
    Block definedBlock() {
        if (constructBlock == null) {
            constructBlock = new UBWallBase(baseBlock(),UBIDs.UBWallsName);
            UBIDs.UBWallsName.gameRegister(constructBlock, ItemUBWall.class);
        }
        return constructBlock;
    }

}
