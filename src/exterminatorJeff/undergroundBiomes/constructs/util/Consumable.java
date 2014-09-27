package exterminatorJeff.undergroundBiomes.constructs.util;

/**
 * For one-use items so they aren't accidentally reused
 * @author Zeno410
 */
public class Consumable<Type> {
    private final Type item;
    private boolean used = false;
    public Consumable(Type _item) {item = _item;}

    public Type use() {
        used = true;
        return item;
    }

    public Type getRiskingMultipleUse() {return item;}

    public static <ConsumableType> Consumable<ConsumableType> from(ConsumableType toUseOnce) {
        return new Consumable<ConsumableType>(toUseOnce);
    }
}
