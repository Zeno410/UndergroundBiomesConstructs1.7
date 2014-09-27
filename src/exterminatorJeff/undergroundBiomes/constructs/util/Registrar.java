package exterminatorJeff.undergroundBiomes.constructs.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.block.Block;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedItem;

/**
 * This class is for reregistring items that disappear from the item and block registries
 * I was having some trouble with that during development. Currently not used.
 * @author Zeno410
 */
public class Registrar {

    public static final Registrar instance  = new Registrar();

    private Set<Reregistrable>  items = new HashSet<Reregistrable>();

    public void reRegister() {
        for (Reregistrable item: items) item.reRegister();
    }

    public void add(Reregistrable toAdd) {items.add(toAdd);}

    public void add(NamedBlock namer, int ID, Block block) {
        add(new Reregistrable.BlockCall(namer,ID,block));
    }

    public void add(NamedItem namer, int ID, Item item) {
        add(new Reregistrable.ItemCall(namer,ID,item));
    }

}
