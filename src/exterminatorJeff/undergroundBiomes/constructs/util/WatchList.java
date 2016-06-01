package exterminatorJeff.undergroundBiomes.constructs.util;
import Zeno410Utils.Accessor;
import java.util.ArrayList;
import java.util.HashMap;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;

/**
 * This class is for checking up on and redoing problematic registry entries
 * @author Zeno410
 */
public class WatchList {
    private HashMap<Object,Watchable> items = new HashMap<Object,Watchable>();

    private static Accessor<RegistryNamespaced,ObjectIntIdentityMap> intRegistryAccess =
            new Accessor<RegistryNamespaced,ObjectIntIdentityMap>(RegistryNamespaced.class);

    //public static Logger logger = new Zeno410Logger("WatchList").logger();
    public ArrayList<String> problems() {
        ArrayList<String> result = new ArrayList<String>();
        for (Watchable item: items.values()) {
            ProblemReport report = item.problemReport();
            result.add(report.description);
        }
        return result;
    }

    public void redoAsNeeded() {
        for (Watchable item: items.values()) {
            item.redoIfNeeded();
        }
    }

    public void add(Block added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableBlock(added));
    }

    public void addWithItem(Block added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableBlock(added));
        int blockID = Block.getIdFromBlock(added);
        WatchableItem watchableItem = new WatchableItem(Item.getItemById(blockID));
        this.items.put(watchableItem.item,watchableItem);
    }

    public void addChangeWithItem(int newID, Block added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableBlock(newID, added));
        WatchableItem watchableItem = new WatchableItem(newID, added);
        this.items.put(watchableItem.item,watchableItem);
    }

    public void addChange(int newID, Block added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableBlock(newID, added));
    }

    public void addChange(int newID, Item added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableItem(newID, added));
    }

    public void add(Item added) {
        if (items.containsKey(added)) return;
        this.items.put(added,new WatchableItem(added));
    }


    public void clear() {
        items = new HashMap<Object,Watchable>();
    }



    public static interface Watchable {
        public ProblemReport problemReport();
        public void redoIfNeeded();
    }

    public static class ProblemReport {
        public final boolean hasProblem;
        public final String description;
        public ProblemReport(boolean hasProblem, String text) {
            this.hasProblem  = hasProblem;
            description = text;
        }
    }

    public static class WatchableBlock implements Watchable {
        private final Block block;
        private final int blockID;
        private final String blockName;


        public WatchableBlock(Block toWatch) {
            block = toWatch;
            blockID = Block.getIdFromBlock(toWatch);
            if (blockID == -1 ) throw new RuntimeException(toWatch.toString()+ " currently unregistered");
            blockName = Block.blockRegistry.getNameForObject(toWatch);
            if (blockName == null) throw new RuntimeException(toWatch.toString()+ " currently unregistered");
        }

        public WatchableBlock(int newID, Block toWatch) {
            block = toWatch;
            blockID = newID;
            blockName = Block.blockRegistry.getNameForObject(toWatch);
            if (blockName == null) throw new RuntimeException(toWatch.toString()+ " currently unregistered");

        }

        public ProblemReport problemReport() {
            String result = "";
            boolean problem = false;
            int newID = Block.getIdFromBlock(block);
            if (newID != blockID) {
                problem = true;
                if (newID == -1 )  result += block.toString()+ " lacks ID";
                if (newID != -1 ) result += block.toString() + " moved from "+ blockID + " to "+ newID;
            }

            Block identified = Block.getBlockById(blockID);
            if (identified != block) {
                problem = true;
                if (identified == null) {
                    result += block.toString() + " not retrievable by number";
                } else {
                    result += block.toString() + " number replaced by " + identified.toString();
                }
            } else {
                result += ""+ blockID + " correctly IDs "+ block.toString();
            };

            Block named = Block.getBlockFromName(blockName);
            if (named != block) {
                problem = true;
                if (named == null) {
                    result += block.toString() + " not named";
                } else {
                    result += block.toString() + " replaced by " + named.toString();
                }
            } else {
                result += block.toString() + " correctly named "+ blockName;
            };
            if (problem) {
                return new ProblemReport(true,result);
            } else {
                return new ProblemReport(false,block.toString() + " registered to "+ newID);
            }
        }

        public void redoIfNeeded() {
            ProblemReport report = problemReport();
            if (report.hasProblem)  {
                ObjectIntIdentityMap intRegistry = intRegistryAccess.get(Block.blockRegistry);
                intRegistry.func_148746_a(block, blockID);
                //Block.blockRegistry.addObject(ID, name, block);
            }
        }
    }

    public static class WatchableItem implements Watchable {

        public final Item item;
        public final int itemID;
        private final String itemName;

        public WatchableItem( Item paired) {
            //super(toWatch);
            item= paired;
            itemID = Item.getIdFromItem(item);
            if (itemID == -1 ) throw new RuntimeException(item.toString()+ " currently unregistered");
            itemName = Item.itemRegistry.getNameForObject(item);
            if (itemName == null) throw new RuntimeException(item.toString()+ " currently unregistered");
        }

        public WatchableItem(int newID, Item paired) {
            //super(toWatch);
            item= paired;
            itemID = newID;
            itemName = Item.itemRegistry.getNameForObject(item);
            if (itemName == null) throw new RuntimeException(item.toString()+ " currently unregistered");
        }

        public WatchableItem(int newID, Block blockPairing) {
            //super(toWatch);
            int oldID = Block.getIdFromBlock(blockPairing);
            item= Item.getItemById(oldID);
            if (item == null) throw new RuntimeException("No current item for "+blockPairing.toString());
            itemID = newID;
            itemName = Item.itemRegistry.getNameForObject(item);
            if (itemName == null) throw new RuntimeException(item.toString()+ " currently unregistered");
        }

        public ProblemReport problemReport() {
            String result = "";
            boolean problem = false;
            int newID = Item.getIdFromItem(item);
            if (newID != itemID) {
                problem = true;
                if (newID == -1 )  result += item.toString()+ " lacks ID";
                if (newID != -1 ) result += item.toString() + " moved from "+ itemID + " to "+ newID;
            }

            Item identified = Item.getItemById(itemID);
            if (identified != item) {
                problem = true;
                if (identified == null) {
                    result += item.toString() + " not retrievable by number";
                } else {
                    result += item.toString() + " number replaced by " + identified.toString();
                }
            } else {
                result += ""+ itemID + " correctly IDs "+ item.toString();
            };

            Item named = (Item)(Item.itemRegistry.getObject(itemName));
            if (named != item) {
                problem = true;
                if (named == null) {
                    result += item.toString() + " not named";
                } else {
                    result += item.toString() + " replaced by " + named.toString();
                }
            } else {
                result += item.toString() + " correctly named "+ itemName;
            };
            if (problem) {
                return new ProblemReport(true,result);
            } else {
                return new ProblemReport(false,item.toString() + " registered to "+ newID);
            }
        }

        public void redoIfNeeded() {
            ProblemReport report = problemReport();
            if (report.hasProblem)  {
                ObjectIntIdentityMap intRegistry = intRegistryAccess.get(Item.itemRegistry);
                intRegistry.func_148746_a(item, itemID);
                //Block.blockRegistry.addObject(ID, name, block);
            }
        }
    }

}
