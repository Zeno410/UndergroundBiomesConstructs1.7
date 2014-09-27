package exterminatorJeff.undergroundBiomes.constructs.util;

import net.minecraft.item.Item;
import net.minecraft.block.Block;


import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedItem;

/**
 *
 * @author Zeno410
 */
public interface Reregistrable {
    public void reRegister();

    public static class BlockCall implements Reregistrable {
        private NamedBlock namer;
        private int ID;
        private Block block;

        public BlockCall(NamedBlock _namer, int _ID, Block _block) {
            namer = _namer;
            ID = _ID;
            block = _block;
        }

        public void reRegister(){
            namer.reRegister(ID, block);
        }
    }

    public static class ItemCall implements Reregistrable {
        private NamedItem namer;
        private int ID;
        private Item item;

        public ItemCall(NamedItem _namer, int _ID, Item _item) {
            namer = _namer;
            ID = _ID;
            item = _item;
        }
        public void reRegister(){
            namer.reRegister(ID, item);
        }
    }

}
