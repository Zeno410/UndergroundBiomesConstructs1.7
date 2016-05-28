
package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBWall;

import cpw.mods.fml.common.registry.GameRegistry;

import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
            GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone_wall,1), product.stackOf(1));
            //product.stackOf(6).func_77980_a(ItemStack.java:411)
    }

    IRecipe recipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return wallRecipe(product,stone);
    }

    IRecipe rescueRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        if (stone.ubBlock().ubBlock == UndergroundBiomes.igneousCobblestone){
            return new ShapelessOreRecipe(new ItemStack(Blocks.cobblestone_wall,1), product.stackOf(1));
        }
        if (stone.ubBlock().ubBlock == UndergroundBiomes.metamorphicCobblestone) {
            return new ShapelessOreRecipe(new ItemStack(Blocks.cobblestone_wall,1), product.stackOf(1));
        }
        return null;
    }
    ShapedOreRecipe wallRecipe(ProductItemDefiner product, StoneItemDefiner stone) {
        return new ShapedOreRecipe(product.stackOf(6), "   ", "XXX", "XXX", 'X', stone.one());
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