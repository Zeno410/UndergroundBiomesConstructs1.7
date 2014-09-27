package exterminatorJeff.undergroundBiomes.constructs.item;

/**
 *
 * @author zeno410
 */

import net.minecraft.block.Block;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedItem;

public class ItemUBButton extends ItemUndergroundBiomesConstruct{

    public ItemUBButton(Block block) {
        this(block,UBIDs.UBButtonItemName);
    }

    public ItemUBButton(Block block,NamedItem namer){
        super(block,namer);
    }

    public String groupName() {return "button";}

    public int metadataForPlacement(int metadata) {return metadata;}
}
