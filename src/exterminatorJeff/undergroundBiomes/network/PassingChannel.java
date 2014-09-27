
package exterminatorJeff.undergroundBiomes.network;

/**
 *
 * @author Zeno410
 */

import Zeno410Utils.Acceptor;
import Zeno410Utils.PlayerAcceptor;
import Zeno410Utils.Streamer;
import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author Zeno410
 */
public class PassingChannel<Type>  extends AbstractChannel<Type> {

    // double singleton due to Forge creation timing issues
    public final Acceptor<Type> clientManager;
    public PlayerAcceptor<Type> serverManager;
    public final Streamer<Type> streamer;


    protected PassingChannel(Acceptor<Type> manager,Streamer<Type> streamer) {
        this.clientManager = manager;
        this.streamer = streamer;
        this.serverManager = new IgnorePlayer<Type>(manager);
    }
    
    protected PassingChannel(Acceptor<Type> manager,PlayerAcceptor<Type> serverManager,Streamer<Type> streamer) {
        this.clientManager = manager;
        this.serverManager = serverManager;
        String type = serverManager.toString();
        this.streamer = streamer;
    }

    private class IgnorePlayer<JustType> extends PlayerAcceptor<JustType> {
        private Acceptor<JustType> internal;
        public IgnorePlayer(Acceptor<JustType> internal) {
            this.internal = internal;
        }

        public void accept(EntityPlayer player, JustType accepted) {
            internal.accept(accepted);
        }
    }
    public void receiveAsClient(Type incoming, EntityPlayer player){
        clientManager().accept(incoming);
    }

    public void receiveAsServer(Type incoming, EntityPlayer player){
        serverManager().accept(player,incoming);
    }

    public Acceptor<Type> clientManager() {return clientManager;}
    public PlayerAcceptor<Type> serverManager() {return serverManager;}

    public AbstractMessage<Type> incomingMessage(){
        return new AbstractMessage<Type>() {
            public Streamer<Type> streamer() {
                return streamer;
            }
        };
    }

    public AbstractMessage<Type> outgoingMessage(Type sent){
        return new AbstractMessage<Type>(sent) {
            public Streamer<Type> streamer() {
                return streamer;
            }
        };
    }

}
