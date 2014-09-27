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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class UBButtonGroup extends UBConstructGroup {

    public UBButtonGroup() {
        super("button");
    }

    public static boolean suppress(UndergroundBiomesBlock block) {
        if (block.ubBlock == UndergroundBiomes.igneousStoneBrick) return true;
        return  (block.ubBlock  == UndergroundBiomes.metamorphicStoneBrick) ;
    }

    void addRecipe(ProductItemDefiner product,StoneItemDefiner stone) {
        // suppress brick buttons
        if (suppress(stone.ubBlock())) return;
        //GameRegistry.addRecipe(new ShapedOreRecipe(product.stackOf(2), "   ", " X ", " X ", 'X', stone.one()));
        GameRegistry.addShapelessRecipe(product.stackOf(1), stone.one());
    }
    
    IRecipe recipe(ProductItemDefiner product,StoneItemDefiner stone) {
        // suppress brick buttons
        if (suppress(stone.ubBlock())) return null;
        return new ShapelessOreRecipe(product.stackOf(1), stone.one());
    }

    Class<? extends ItemBlock> itemClass() {
        return ItemUBButton.class;
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

