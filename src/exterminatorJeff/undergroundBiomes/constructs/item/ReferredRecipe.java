
package exterminatorJeff.undergroundBiomes.constructs.item;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 *
 * @author Zeno410
 */
abstract public class ReferredRecipe implements IRecipe{

    abstract public IRecipe referred();

    public boolean matches(InventoryCrafting arg0, World arg1) {
        return referred().matches(arg0, arg1);
    }

    public ItemStack getCraftingResult(InventoryCrafting arg0) {
        return referred().getCraftingResult(arg0);
    }

    public int getRecipeSize() {
        return referred().getRecipeSize();
    }

    public ItemStack getRecipeOutput() {
        return referred().getRecipeOutput();
    }

}
