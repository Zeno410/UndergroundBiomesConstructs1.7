/**
 *
 * @author Zeno410
 */

package exterminatorJeff.undergroundBiomes.network;
import Zeno410Utils.Streamer;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackStreamer extends Streamer<ItemStack>{


    public ItemStack readFrom(DataInput input) throws IOException {
        String itemName = input.readUTF();
        int itemCount = input.readInt();
        int itemDamage = input.readInt();
        return new ItemStack((Item)(Item.itemRegistry.getObject(itemName)),itemCount,itemDamage);
    }
    public void writeTo(ItemStack written, DataOutput output) throws IOException {
        output.writeUTF(Item.itemRegistry.getNameForObject(written.getItem()));
        output.writeInt(written.stackSize);
        output.writeInt(written.getItemDamage());
    }


}
