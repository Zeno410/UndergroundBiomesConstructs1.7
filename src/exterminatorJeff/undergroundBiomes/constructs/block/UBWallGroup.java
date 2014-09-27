/**
 *
 * @author Zeno410
 */

package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBWall;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;

public class UBWallGroup  extends UBConstructGroup {

    public UBWallGroup() {
        super("wall");
    }


    void addRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        IRecipe wallRecipe = wallRecipe(product,stone);
            GameRegistry.addRecipe(wallRecipe);
            ItemStack stack = product.one();
            ItemUBWall item = (ItemUBWall)stack.getItem();
            String test = item.toString();
            //item.testRenderItemIntoGUI(product.stackOf(6), false);
            //item.testRegistration();
            Item toGet = product.stackOf(6).getItem();
            //ItemUBWall.logger.info(""+Item.getIdFromItem(toGet));
            Item toGetProduct = wallRecipe.getRecipeOutput().copy().getItem();
            //ItemUBWall.logger.info(""+Item.getIdFromItem(toGetProduct));
            //ItemUBWall.logger.info(""+Item.getIdFromItem(toGet));
            //product.stackOf(6).func_77980_a(ItemStack.java:411)
    }

    IRecipe recipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return wallRecipe(product,stone);
    }

    private ShapedOreRecipe wallRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return new ShapedOreRecipe(product.stackOf(6), "   ", "XXX", "XXX", 'X', stone.one());
    }
    Class<? extends ItemBlock> itemClass() {
        return ItemUBWall.class;
    }

    private static UBWallBase constructBlock;
    Block definedBlock() {
        if (constructBlock == null) {
            constructBlock = new UBWallBase(baseBlock(),UBIDs.UBWallsName);
            UBIDs.UBWallsName.gameRegister(constructBlock, ItemUBWall.class);
        }
        return constructBlock;
    }

    class WallRecipe extends ShapedOreRecipe {
        public WallRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
            super (product.stackOf(6), "   ", "XXX", "XXX", 'X', stone.one());
        }

        public ItemStack getCraftingResult(InventoryCrafting var1) {
            ItemStack result =  super.getCraftingResult(var1);
            int registeredID = Item.getIdFromItem(result.getItem());
            //ItemUBWall.logger.info("crafting "+registeredID+" " +result.getItem().toString());
            if (registeredID == -1) {
                ItemUBWall wallItem = (ItemUBWall)(result.getItem());
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
