
package exterminatorJeff.undergroundBiomes.network;

import Zeno410Utils.Acceptor;
import Zeno410Utils.Streamer;
import net.minecraft.entity.player.EntityPlayerMP;
/**
 *
 * This wrapper class permits easy access to an object passing channel.
 * Internal calls for receiving and processing packets are hidden.
 * @author Zeno410
 */
public final class DirectChannel<Type> {
    private final PacketPipeline.Channel<Type> channel;
    public PacketPipeline.Channel<Type> channel() {return channel;}

    public DirectChannel(PacketPipeline pipeline, Streamer<Type> streamer, Acceptor<Type> manager) {
        // this crashes if the channel cannot be created.
        // We're goin' down anyway if that happens, better now than later.
          channel = pipeline.registerChannel(
                new PassingChannel<Type>(manager,streamer))
                .iterator().next();  // the channel comes out in a Maybe; this extracts it
    }

    public final void sendTo(Type sent,EntityPlayerMP player) {
        channel.sendTo(sent,player);
    }

    public final void sendToAll(Type sent) {
        channel.sendToAll(sent);
    }

    public final void sendServer(Type sent) {
        channel.sendServer(sent);
    }
}
