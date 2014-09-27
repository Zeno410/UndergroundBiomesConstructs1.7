/*
 * Author Zeno410
 */

package exterminatorJeff.undergroundBiomes.constructs;

import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.common.block.BlockSedimentaryStone;

import exterminatorJeff.undergroundBiomes.constructs.item.ItemUndergroundBiomesConstruct;
import exterminatorJeff.undergroundBiomes.constructs.block.UBButtonGroup;
import exterminatorJeff.undergroundBiomes.constructs.block.UBStairsGroup;
import exterminatorJeff.undergroundBiomes.constructs.block.UBWallGroup;

import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;
import exterminatorJeff.undergroundBiomes.constructs.entity.UndergroundBiomesTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockWall;
import net.minecraftforge.common.config.Configuration;
import net.minecraft.util.IIcon;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.ItemBlock;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;
import exterminatorJeff.undergroundBiomes.common.item.SwitchableRecipeGroup;
import Zeno410Utils.Zeno410Logger;

//@Mod(modid = "UndergroundBiomesConstructs", name = "Underground Biomes Constructs", version = "0.0.4")
//@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class UndergroundBiomesConstructs {

    public static int subdivisionBlockCount = 4; // 8 types, 2 per class
    private UndergroundBiomesSettings settings() {
        return UndergroundBiomes.instance().settings();
    }

    private UBStairsGroup stoneStair;
    public int stoneStairID() {return settings().stoneStairID.value();}
    public UBStairsGroup stoneStair() {return stoneStair;}

    private UBWallGroup stoneWall;
    public int stoneWallID() {return settings().stoneWallID.value();}
    public UBWallGroup stoneWall() {return stoneWall;}

    private UBButtonGroup stoneButton;
    public int stoneButtonID() {return settings().stoneButtonID.value();}
    public UBButtonGroup stoneButton() {return stoneButton;}

    private BlockMetadataBase iconTrap;

    public Configuration config;

    private UndergroundBiomesBlockList ubBlockList;

    public static String blockCategory = "block";
    public static String itemCategory = "item";

    private SwitchableRecipeGroup stairRecipes;
    private SwitchableRecipeGroup wallRecipes;
    private SwitchableRecipeGroup buttonRecipes;

    //@PreIni
    public void preInit(Configuration config) {
        this.config = config;
        ubBlockList = new UndergroundBiomesBlockList();
        GameRegistry.registerTileEntity(UndergroundBiomesTileEntity.class, "UndergroundBiomesTileEntity");
        iconTrap = new IconTrap();

        // with world-specific configs the buttons always have to exist.
        /*if (UndergroundBiomes.buttonsOn())*/ preInitButtons();
        /*if (UndergroundBiomes.stairsOn())*/ preInitStairs();
        /*if (UndergroundBiomes.wallsOn())*/ preInitWalls();
    }

    public void preInitStairs() {
        stoneStair = new UBStairsGroup();
        stoneStair.baseBlock = iconTrap;
        stoneStair.define(stoneStairID());
    }

    public void preInitWalls() {
        stoneWall = new UBWallGroup();
        stoneWall.baseBlock = ubBlockList.sedimentaryStone;
        stoneWall.define(stoneWallID());
    }

    public void preInitButtons() {
        stoneButton = new UBButtonGroup();
        stoneButton.baseBlock = ubBlockList.sedimentaryStone;
        stoneButton.define(stoneButtonID());
    }

    public void load(FMLInitializationEvent event) {
        /*if (UndergroundBiomes.buttonsOn())*/ loadButtons();
        /*if (UndergroundBiomes.stairsOn())*/ loadStairs();
        /*if (UndergroundBiomes.wallsOn())*/ loadWalls();
    }

    private void loadStairs(){
        //stoneStair.addRecipes();
        stairRecipes = new SwitchableRecipeGroup(stoneStair.recipes(),settings().stairsOn);
    }
    private void loadWalls() {
        //stoneWall.addRecipes();
        wallRecipes = new SwitchableRecipeGroup(stoneWall.recipes(),settings().wallsOn);
    }

    private void loadButtons() {
        //stoneButton.addRecipes();
        buttonRecipes = new SwitchableRecipeGroup(stoneButton.recipes(),settings().buttonsOn);
    }

    public void postInit(FMLPostInitializationEvent event) throws Exception {
        
    }

    public static boolean overridesRecipe(IRecipe recipe) {
        // this determines if oredictifying a recipe will override a constructs recipe
        // right now it's overly simplistic and assumes anything producing a construct being made
        // is an override
        if (recipe == null) return false;
        Object output = null;
        try {
            output = recipe.getRecipeOutput().getItem();
        } catch (Exception e) {
            return false;
        }
        if (output == null) return false;
        if (!(output instanceof ItemBlock)) {return false;}
        Block blockMade = ((ItemBlock)output).field_150939_a;
        if (UndergroundBiomes.buttonsOn()) {
            if (blockMade instanceof BlockButtonStone) {
                return true;
            }
        }
        if (UndergroundBiomes.stairsOn()) {
            if (blockMade instanceof BlockStairs) {
                return true;
            }
        }
        if (UndergroundBiomes.wallsOn()) {
            if (blockMade instanceof BlockWall) {
                return true;
            }
        }
        if (blockMade instanceof BlockSlab) {
            return !blockMade.isOpaqueCube();
        }
        return false;
    }
}

class IconTrap extends BlockSedimentaryStone {

    IconTrap() {super(UBIDs.IconTrap);}
    
    public IIcon getIcon(int side, int metadata) {

        return ItemUndergroundBiomesConstruct.currentColor;
    }
}
