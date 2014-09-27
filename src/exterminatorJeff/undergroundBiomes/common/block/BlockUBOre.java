
package exterminatorJeff.undergroundBiomes.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import Zeno410Utils.Mutable;
import Zeno410Utils.MinecraftName;
import exterminatorJeff.undergroundBiomes.constructs.util.ShamWorld;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author Zeno410
 */
public class BlockUBOre extends Block {

    protected final BlockMetadataBase stone;
    protected final Block ore;
    private final BlockOverlay overlay;
    private Mutable<Integer> renderIDSource;
    protected ShamWorld shamWorld;
    private final MinecraftName oreName;

    public BlockUBOre(BlockMetadataBase stone, final Block ore, BlockOverlay overlay, Mutable<Integer> renderIDSource) {
        this(stone,ore,overlay,renderIDSource,new MinecraftName(ore.getUnlocalizedName()));
    }

    public BlockUBOre(BlockMetadataBase stone, Block ore, BlockOverlay overlay,
            Mutable<Integer> renderIDSource,MinecraftName oreName) {
        super(Material.rock);
        this.stone = stone;
        this.ore = ore;
        if (ore instanceof BlockUBOre) {
            throw new RuntimeException();
        }
        this.renderIDSource = renderIDSource;
        if (renderIDSource == null) {
            if (UndergroundBiomes.crashOnProblems()) throw new RuntimeException();
        }
        this.overlay = overlay;
        this.setCreativeTab(UndergroundBiomes.tabModBlocks);
        this.oreName = oreName;

    }

    @Override
    public int getRenderType() {
        if (renderIDSource == null) return 0;
        Integer result = renderIDSource.value();
        if (result == null) return 0;
        return result;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister){
        // Block icons registered by UB blocks.
        // overlay doesn't get its icon registered because *it* doesn't get registered;
        overlay.registerBlockIcons(iconRegister);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata){
        return stone.getIcon(side,metadata);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected String getTextureName(){
        return stone.getTextureName();
    }

    @Override
    public void getSubBlocks(Item id, CreativeTabs tabs, List list){
        // if UB ores is off nothing in the tabs
        if (UndergroundBiomes.ubOres() == false) return;
        for (int i = 0; i < BlockMetadataBase.metadatas; i++){
            list.add(new ItemStack(id, 1, i));
        }
    }

    protected ShamWorld shamWorld(World forSettings) {
        if (shamWorld == null) shamWorld = new ShamWorld(new WorldSettings(forSettings.getWorldInfo()));
        return shamWorld;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        return ore.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public String getItemIconName() {
        return stone.getItemIconName()+" " + oreName.localized();
    }

    public String getDisplayName(int meta) {
        return stone.getBlockName(meta)+ " " + oreName.localized();
    }

    public String getUnlocalizedName(int meta) {
        return stone.getBlockName(meta)+ "."+ oreName.unlocalized();
    }

    @Override
    public void dropXpOnBlockBreak(World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_, int p_149657_5_) {
        ore.dropXpOnBlockBreak(p_149657_1_, p_149657_2_, p_149657_3_, p_149657_4_, p_149657_5_);
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        ore.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
    }

    @Override
    public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
        return ore.getBlockHardness(p_149712_1_, p_149712_2_, p_149712_3_, p_149712_4_);
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
        return ore.getExpDrop(world, metadata, fortune);
    }

    @Override
    public float getExplosionResistance(Entity p_149638_1_) {
        return ore.getExplosionResistance(p_149638_1_);
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return ore.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return ore.getLightOpacity();
    }

    @Override
    public int getLightValue() {
        return ore.getLightValue();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return ore.getLightValue();
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        ore.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
    }


    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return ore.canHarvestBlock(player, 0);
    }
    
    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        ore.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        Item item = getItem(world, x, y, z);
        if (item == null){
            return null;
        }
        return new ItemStack(item, 1, getDamageValue(world, x, y, z));
    }
    public int damageDropped(int metadata){
        return metadata;
    }
   
}
