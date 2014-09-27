/**
 *
 * @author Zeno410
 *
 * Constructor for the Stairs group
 */

package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBStairs;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;


public class UBStairsGroup extends UBConstructGroup {
    
    public UBStairsGroup() {
        super("stairs");
    }

    void addRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        IRecipe stairsRecipe = new ShapedOreRecipe(
                product.stackOf(4), "  X", " XX", "XXX", 'X', stone.one());
        GameRegistry.addRecipe(stairsRecipe);
    }

    IRecipe recipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return new ShapedOreRecipe(product.stackOf(4), "  X", " XX", "XXX", 'X', stone.one());
    }

    Class<? extends ItemBlock> itemClass() {
        return ItemUBStairs.class;
    }
    
    private static UBStairsBase constructBlock;
    Block definedBlock() {
        if (constructBlock == null) {
            constructBlock = new UBStairsBase(baseBlock(),UBIDs.UBStairsName);
            UBIDs.UBStairsName.gameRegister(constructBlock, ItemUBStairs.class);
        }
        return constructBlock;
    }
    
    class StairsRecipe extends ShapedOreRecipe {
        public StairsRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
            super (product.stackOf(4), "  X", " XX", "XXX", 'X', stone.one());
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting var1) {
            ItemStack result =  super.getCraftingResult(var1);
            int registeredID = Item.getIdFromItem(result.getItem());
            //ItemUBWall.logger.info("crafting "+registeredID+" " +result.getItem().toString());
            if (registeredID == -1) {
                ItemUBStairs wallItem = (ItemUBStairs)(result.getItem());
                registeredID = Item.getIdFromItem(result.getItem());
                throw new RuntimeException();
            }
            //ItemUBWall.logger.info("crafting "+registeredID+" " +result.getItem().toString());
            return result;
        }

        public ItemStack getRecipeOutput() {
            ItemStack result = super.getRecipeOutput();
            //ItemUBWall.logger.info("output "+Item.getIdFromItem(result.getItem())+" " +result.getItem().toString());
            return result;
        }

    }
}
