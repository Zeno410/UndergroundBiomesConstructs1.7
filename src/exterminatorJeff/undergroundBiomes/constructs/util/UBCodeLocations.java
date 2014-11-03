package exterminatorJeff.undergroundBiomes.constructs.util;

import java.util.HashMap;

import Zeno410Utils.BlockLocation;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

/**
 * This class can record locations where UB Constructs codes are used
 * Currently used for caching codes when blocks are destroyed
 * Perhaps will be used to replace Tile Entities someday
 * @author Zeno410
 */
public class UBCodeLocations {
    private HashMap<BlockLocation,UndergroundBiomesBlock> stored = new HashMap<BlockLocation,UndergroundBiomesBlock>();

    public void add(int x, int y, int z, UndergroundBiomesBlock ubBlock) {
        BlockLocation location = BlockLocation.fetch(x,y,z);
        if (stored.containsKey(location)) {
            if (UndergroundBiomes.crashOnProblems()) {
                //throw new RuntimeException();
            }
        }
        stored.put(location, ubBlock);
    }

    public UndergroundBiomesBlock get(int x, int y, int z) {
        UndergroundBiomesBlock result = stored.get(BlockLocation.fetch(x,y,z));
        // if nothing there crash for development and pass a (garish) default otherwise
        if (result == null) {
            if (UndergroundBiomes.crashOnProblems()) {
                throw new RuntimeException();
            } else {
                return UndergroundBiomesBlockList.indexed(0);
            }
        }
        return result;
    }

    public void remove(int x, int y, int z) {
        stored.remove(BlockLocation.fetch(x,y,z));
    }

    public void clear() {
        // for world loads. Crash under development if not empty.
        if (!stored.isEmpty()) {
            if (UndergroundBiomes.crashOnProblems()) {
                //throw new RuntimeException();
            } else {
                stored.clear();
            }
        }
    }

}
