
package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBStairs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author Zeno410
 */
public class UBStairsGroup extends UBConstructGroup {
    
    public UBStairsGroup() {
        super("stairs");
    }

    IRecipe recipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return new ShapedOreRecipe(product.stackOf(4), "  X", " XX", "XXX", 'X', stone.one());
    }

    IRecipe rescueRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        if (stone.ubBlock().ubBlock == UndergroundBiomes.igneousCobblestone) return null;
        if (stone.ubBlock().ubBlock == UndergroundBiomes.metamorphicCobblestone) return null;
        if (stone.ubBlock().ubBlock == UndergroundBiomes.igneousStoneBrick){
            return new ShapelessOreRecipe(new ItemStack(Blocks.stone_brick_stairs,1), product.stackOf(1));
        }
        if (stone.ubBlock().ubBlock == UndergroundBiomes.metamorphicStoneBrick){
            return new ShapelessOreRecipe(new ItemStack(Blocks.stone_brick_stairs,1), product.stackOf(1));
        }
        if (stone.ubBlock().ubBlock == UndergroundBiomes.sedimentaryStone){
            return new ShapelessOreRecipe(new ItemStack(Blocks.sandstone_stairs,1), product.stackOf(1));
        }
        return new ShapelessOreRecipe(new ItemStack(Blocks.stone_stairs,1), product.stackOf(1));
    }

    private static UBStairsBase constructBlock;
    Block definedBlock() {
        if (constructBlock == null) {
            constructBlock = new UBStairsBase(baseBlock(),UBIDs.UBStairsName);
            UBIDs.UBStairsName.gameRegister(constructBlock, ItemUBStairs.class);
        }
        return constructBlock;
    }
}
