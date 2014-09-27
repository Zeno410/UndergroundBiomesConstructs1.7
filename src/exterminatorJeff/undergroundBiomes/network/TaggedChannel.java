
package exterminatorJeff.undergroundBiomes.network;
import Zeno410Utils.Acceptor;
import Zeno410Utils.Named;
import Zeno410Utils.Streamer;
import net.minecraft.entity.player.EntityPlayerMP;
/**
 *
 * This wrapper class permits easy access to an object passing channel with string tags on the objects
 * for control. Internal calls for receiving and processing packets are hidden along with the need to call
 * Named all the time.
 * @author Zeno410
 */
public final class TaggedChannel<Type> {
    private final MessageManager<Type> manager = new MessageManager<Type>();
    private final PacketPipeline.Channel<Named<Type>> channel;
    //private final Logger logger = new Zeno410Logger("Tagged channel").logger();
    private final PacketPipeline pipeline;

    public PacketPipeline.Channel<Named<Type>> channel() {return channel;}

    public TaggedChannel(PacketPipeline pipeline, Streamer<Type> streamer) {
        // this crashes if the channel cannot be created.
        // We're goin' down anyway if that happens, better now than later.
        this.pipeline = pipeline;
          channel = pipeline.registerChannel(
                new PassingChannel<Named<Type>>(manager,Named.streamer(streamer)))
                .iterator().next();  // the channel comes out in a Maybe; this extracts it

    }

    public void setManageAll(String tag, Acceptor<Type> manager) {
        this.manager.setManageAll(tag, manager);
    }

    public void setManageNext(String tag, Acceptor<Type> manager) {
        this.manager.setManageNext(tag, manager);
    }

    public void resetManageNext(String tag, Acceptor<Type> manager) {
        this.manager.resetManageNext(tag, manager);
    }
    
    public final void sendTo(String tag,Type sent,EntityPlayerMP player) {
        //logger.info(tag+" channel "+ pipeline.discriminator(this.channel) + sent.toString());
        channel.sendTo(Named.from(tag,sent),player);
    }

    public final void sendToAll(String tag,Type sent) {
        channel.sendToAll(Named.from(tag,sent));
    }

    public final void sendServer(String tag, Type sent) {
        channel.sendServer(Named.from(tag,sent));
    }
}
