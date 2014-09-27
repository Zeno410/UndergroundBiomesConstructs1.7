
package exterminatorJeff.undergroundBiomes.network;

import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author Zeno410
 */
abstract public class AbstractChannel<Type> {

     abstract AbstractMessage<Type> outgoingMessage(Type source);

     abstract AbstractMessage<Type> incomingMessage();

     abstract void receiveAsClient(Type incoming, EntityPlayer player);

     abstract void receiveAsServer(Type incoming, EntityPlayer player);
}
