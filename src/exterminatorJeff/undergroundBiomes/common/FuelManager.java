package exterminatorJeff.undergroundBiomes.common;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelManager implements IFuelHandler
{
    public int getBurnTime(ItemStack fuel)
    {
        if(UBIDs.ligniteCoalName.matches(fuel.getItem()))
        {
            return 200;
        } else {
            return 0;
        }
    }
}
