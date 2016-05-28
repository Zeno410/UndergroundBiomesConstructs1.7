/**
 *
 * @author Zeno410
 *
 * Constructor for the button group
 */
package exterminatorJeff.undergroundBiomes.constructs.block;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBButton;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class UBTEButtonGroup extends UBConstructGroup {

    public UBTEButtonGroup() {
        super("button");
    }

    public static boolean suppress(UndergroundBiomesBlock block) {
        if (block.ubBlock == UndergroundBiomes.igneousStoneBrick) return true;
        return  (block.ubBlock  == UndergroundBiomes.metamorphicStoneBrick) ;
    }
    
    IRecipe recipe(ProductItemDefiner product,StoneItemDefiner stone) {
        // suppress brick buttons
        if (suppress(stone.ubBlock())) return null;
        return new ShapelessOreRecipe(product.stackOf(1), stone.one());
    }

    IRecipe rescueRecipe(ProductItemDefiner product,StoneItemDefiner stone) {
        // suppress brick buttons
        if (suppress(stone.ubBlock())) return null;
        if (stone.ubBlock().ubBlock == UndergroundBiomes.igneousCobblestone) return null;
        if (stone.ubBlock().ubBlock == UndergroundBiomes.metamorphicCobblestone) return null;
        return new ShapelessOreRecipe(new ItemStack(Blocks.stone_button,1), product.stackOf(1));
    }

    private static UBButtonBase constructBlock;
    Block definedBlock() {
        if (constructBlock == null) {
            constructBlock = new UBButtonBase(UBIDs.UBButtonName);
            UBIDs.UBButtonName.gameRegister(constructBlock, ItemUBButton.class);
        }
        return constructBlock;
    }

}

