
package exterminatorJeff.undergroundBiomes.network;

import Zeno410Utils.Acceptor;
import Zeno410Utils.Named;
import java.util.HashMap;
/**
 *
 * @author Zeno410
 */
public class MessageManager<Type> extends Acceptor<Named<Type>>{
    // This stores packets that haven't yet been handled
    private HashMap<String,Type> waitingPackets = new HashMap<String,Type>();

    // This stores one-shot packet managers that haven't yet been activated
    private HashMap<String,Acceptor<Type>> targets = new HashMap<String,Acceptor<Type>>();

    // This stores permanent managers
    private HashMap<String,Acceptor<Type>> managers = new HashMap<String,Acceptor<Type>>();

    public void accept(Named<Type> accepted) {
        receive(accepted.name,accepted.object);
    }

    public void receive(String tag, Type value) {

        synchronized(this) {

            if (managers.containsKey(tag)) {
                managers.get(tag).accept(value);
                return;
            }
            if (targets.containsKey(tag)) {
                targets.get(tag).accept(value);
                targets.remove(tag);
                return;
            }
            waitingPackets.put(tag, value);
        }
    }

    public void setManageAll(String tag, Acceptor<Type> manager) {
        synchronized(this) {
            if (targets.containsKey(tag)) throw new RuntimeException("Duplicate manager");
            if (managers.containsKey(tag)) throw new RuntimeException("Duplicate manager");
            managers.put(tag, manager);
            if (waitingPackets.containsKey(tag)) {
                manager.accept(waitingPackets.remove(tag));
            }
        }
    }

    public void setManageNext(String tag, Acceptor<Type> manager) {

        synchronized(this) {
            if (targets.containsKey(tag)) throw new RuntimeException("Duplicate manager");
            if (managers.containsKey(tag)) throw new RuntimeException("Duplicate manager");
            if (waitingPackets.containsKey(tag)) {
                manager.accept(waitingPackets.remove(tag));
            } else {
                targets.put(tag, manager);
            }
        }
    }

    public void resetManageNext(String tag, Acceptor<Type> manager) {

        synchronized(this) {
            if (managers.containsKey(tag)) throw new RuntimeException("Duplicate manager");
            if (waitingPackets.containsKey(tag)) {
                manager.accept(waitingPackets.remove(tag));
            } else {
                targets.put(tag, manager);
            }
        }
    }

}
