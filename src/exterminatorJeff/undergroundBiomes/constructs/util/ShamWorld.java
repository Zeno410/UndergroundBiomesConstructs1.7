
package exterminatorJeff.undergroundBiomes.constructs.util;

import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;

/**
 *
 * @author Zeno410
 */
public class ShamWorld extends World {
    private int shamMetadata;

    //static ISaveHandler iSaveHandler = null;
    static String name = "Sham World";
    //static WorldProvider WorldProvider = null;
    //static Profiler profiler = null;

    public ShamWorld(WorldSettings settings) {
        super(null, name, null, settings, null);
    }

    @Override
    public int getBlockMetadata(int par1, int par2, int par3) {
        return shamMetadata;
    }

    @Override
    protected int func_152379_p() {
        return 256;
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Entity getEntityByID(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}