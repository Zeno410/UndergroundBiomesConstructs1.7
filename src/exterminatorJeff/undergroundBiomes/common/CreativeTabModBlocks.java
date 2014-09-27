package exterminatorJeff.undergroundBiomes.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabModBlocks extends CreativeTabs
{
    public Item item;

    public CreativeTabModBlocks(String s){
        super(s);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem(){
        return item;
    }
}
