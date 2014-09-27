package exterminatorJeff.undergroundBiomes.common.block;

/**
 *
 * @author Zeno410
 */
public class StoneSlabPair {

    public BlockStoneSlab half;
    public BlockStoneSlab full;

    public StoneSlabPair (BlockStoneSlab half,BlockStoneSlab full) {
        this.half = half; this.full = full;
        if (half.isDoubleSlab) throw new RuntimeException();
        if (!full.isDoubleSlab) throw new RuntimeException();
        return;
    }

}
