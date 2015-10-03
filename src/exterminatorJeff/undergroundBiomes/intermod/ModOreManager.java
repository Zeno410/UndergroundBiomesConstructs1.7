
package exterminatorJeff.undergroundBiomes.intermod;

import Zeno410Utils.Zeno410Logger;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBOreTexturizer;
import java.util.logging.Logger;
import net.minecraft.block.Block;

/**
 *
 * @author Zeno410
 */
public class ModOreManager {
    public static Logger logger = new Zeno410Logger("ModOreManager").logger();

    public void register() {
        UBOreTexturizer texturizer = UBAPIHook.ubAPIHook.ubOreTexturizer;
        {
            // Thermal Foundation
            Block thermalFoundationOreBlock = Block.getBlockFromName("ThermalFoundation:Ore");
            if (thermalFoundationOreBlock != null) {
                logger.info("Thermal Foundation found");
                // install texturizers
                texturizer.requestUBOreSetup(thermalFoundationOreBlock, 0, UBOreTexturizer.copper_overlay, "tile.thermalfoundation.ore.copper");
                texturizer.requestUBOreSetup(thermalFoundationOreBlock, 1, UBOreTexturizer.tin_overlay, "tile.thermalfoundation.ore.tin");
                texturizer.requestUBOreSetup(thermalFoundationOreBlock, 2, UBOreTexturizer.silver_overlay, "tile.thermalfoundation.ore.silver");
                texturizer.requestUBOreSetup(thermalFoundationOreBlock, 3, UBOreTexturizer.lead_overlay, "tile.thermalfoundation.ore.lead");
            }
        }
        {
            // Thaumcraft
            Block thaumcraftOreBlock = Block.getBlockFromName("Thaumcraft:blockCustomOre");
            if (thaumcraftOreBlock != null) {
                texturizer.requestUBOreSetup(thaumcraftOreBlock, 0, UBOreTexturizer.cinnabar_overlay, "tile.blockCustomOre.0");
                texturizer.requestUBOreSetup(thaumcraftOreBlock, 7, UBOreTexturizer.amber_overlay, "tile.blockCustomOre.7");
            }
        }
        {
            //Mekanism
            Block mekanismBlock = Block.getBlockFromName("Mekanism:OreBlock");
            if (mekanismBlock != null) {
                texturizer.requestUBOreSetup(mekanismBlock, 1, UBOreTexturizer.copper_overlay, "tile.OreBlock.CopperOre");
                texturizer.requestUBOreSetup(mekanismBlock, 2, UBOreTexturizer.tin_overlay, "tile.OreBlock.TinOre");
            }
        }
        {
            //Project Red ProjRed|Exploration:projectred.exploration.ore
            Block projectRedBlock = Block.getBlockFromName("ProjRed|Exploration:projectred.exploration.ore");

            if (projectRedBlock == null) {
                for (Object key: Block.blockRegistry.getKeys()) {
                    String name = (String) key;
                    Block named = Block.getBlockFromName(name);
                    int id = Block.getIdFromBlock(named);
                    logger.info(name + " "+id);
                    if (name.contains("projectred.exploration.ore")) {
                        projectRedBlock = named;
                        break;
                    }
                }
            }

            if (projectRedBlock !=null) {
                texturizer.requestUBOreSetup(projectRedBlock, 0, UBOreTexturizer.ruby_overlay, "tile.projectred.exploration.ore|0");
                texturizer.requestUBOreSetup(projectRedBlock, 1, UBOreTexturizer.sapphire_overlay, "tile.projectred.exploration.ore|1");
                texturizer.requestUBOreSetup(projectRedBlock, 2, UBOreTexturizer.olivine_peridot_overlay, "tile.projectred.exploration.ore|2");
            }
        }
        {
            //Biomes O' Plenty
            Block bopOreBlock = Block.getBlockFromName("BiomesOPlenty:gemOre");
            if (bopOreBlock !=null) {
                texturizer.requestUBOreSetup(bopOreBlock, 2, UBOreTexturizer.ruby_overlay, "tile.gemOre.rubyore");
                texturizer.requestUBOreSetup(bopOreBlock, 4, UBOreTexturizer.olivine_peridot_overlay, "tile.gemOre.peridotore");
                texturizer.requestUBOreSetup(bopOreBlock, 6, UBOreTexturizer.olivine_peridot_overlay, "tile.gemOre.topazore");
                texturizer.requestUBOreSetup(bopOreBlock, 8, UBOreTexturizer.olivine_peridot_overlay, "tile.gemOre.tanzaniteore");
                texturizer.requestUBOreSetup(bopOreBlock, 10, UBOreTexturizer.emerald_overlay, "tile.gemOre.malachiteore");
                texturizer.requestUBOreSetup(bopOreBlock, 12, UBOreTexturizer.sapphire_overlay, "tile.gemOre.sapphireore");
                texturizer.requestUBOreSetup(bopOreBlock, 14, UBOreTexturizer.amber_overlay, "tile.gemOre.amberore");
            }
        }
        {
            //Enviromine
            Block enviromineBlock = Block.getBlockFromName("enviromine:flammablecoal");
            if (enviromineBlock != null) {
                texturizer.requestUBOreSetup(enviromineBlock, UBOreTexturizer.coal_overlay);
            }
        }
    }

}
