package exterminatorJeff.undergroundBiomes.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class ItemFossilPiece extends Item
{
    private IIcon[] textures;
    private String[] names = {"ammonite", "shell", "rib", "bone", "skull", "bone", "shell", "boneshard"};
    public static final int TYPES = 8;

    public ItemFossilPiece(int id){
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName(UBIDs.fossilPieceName.internal());
        UBIDs.fossilPieceName.register(id, this);
        setCreativeTab(UndergroundBiomes.tabModItems);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        textures = new IIcon[TYPES];
        for (int i = 0; i < TYPES; i++)
        {
            textures[i] = iconRegister.registerIcon("undergroundbiomes:fossilPiece_" + i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta)
    {
        if (meta > TYPES) meta = 0;
        return textures[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item id, CreativeTabs tabs, List list){
        for (int i = 0; i < TYPES; i++) {
            list.add(new ItemStack(UBIDs.fossilPieceName.registeredItem(), 1, i));
        }
    }

    public boolean isHasSubtypes() {
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int damage = stack.getItemDamage();
        if (damage > TYPES) damage = 0;
        return super.getUnlocalizedName() + "." + names[damage];
    }
    

}
