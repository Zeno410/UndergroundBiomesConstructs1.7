package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.UBStoneCodes;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

import cpw.mods.fml.common.eventhandler.Event.Result;

/**
 *
 * @author Zeno410
 */

public class VillageStoneChanger {

    private UBStoneCodes preferredStone;
    boolean replacing;

    public void setStoneCode (UBStoneCodes newCode) {
        preferredStone = newCode;
    }

    public void onVillageSelectBlock(GetVillageBlockID e){
        if (preferredStone == null) return;
        if (e.original == Blocks.log)e.replacement = e.original;
        if (e.original == Blocks.cobblestone) {
            e.replacement = preferredStone.onDrop.block;
            replacing = true;
            e.setResult(Result.DENY);
        }
        if (e.original == Blocks.planks)e.replacement = e.original;
        if (e.original == Blocks.oak_stairs)e.replacement = e.original;
        if (e.original == Blocks.stone_stairs){
            if (UndergroundBiomes.stairsOn()) {
                  e.replacement = Blocks.oak_stairs;
                  e.setResult(Result.DENY);
            }
        }
        if (e.original == Blocks.gravel){
                if (UndergroundBiomes.replaceVillageGravel()) {
                e.replacement = preferredStone.brickVersionEquivalent().block;
                replacing = true;
                e.setResult(Result.DENY);
            }
            
        }
        if (e.original == Blocks.stone_slab) {
            e.replacement = preferredStone.slabVersionEquivalent().block;
            replacing = true;
            e.setResult(Result.DENY);
        }
    }

    public void onVillageSelectMeta(GetVillageBlockMeta e){
        if (preferredStone == null) return;
        if (replacing == true) {
            e.replacement = preferredStone.onDrop.metadata;
            e.setResult(Result.DENY);
            replacing = false;
        }
    }
}
