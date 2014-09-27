package exterminatorJeff.undergroundBiomes.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class ItemLigniteCoal extends Item
{
    IIcon texture;
    
    public ItemLigniteCoal(int id){
        super();
        setUnlocalizedName(UBIDs.ligniteCoalName.internal());
        UBIDs.ligniteCoalName.register(id, this);
        setCreativeTab(UndergroundBiomes.tabModItems);
    }
    
    public String getTextureFile(){
        return UndergroundBiomes.textures;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister){
        texture = UBIDs.ligniteCoalName.registerIcons(iconRegister);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta)
    {
        return texture;
    }
/*
    @Override
    public boolean hasContainerItem() {return true;}

    @Override
    public Item getContainerItem() {
        return this;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(this,itemStack.stackSize, itemStack.getItemDamage());
   }*/ 
}
