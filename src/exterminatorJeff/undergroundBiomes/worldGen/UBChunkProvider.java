package exterminatorJeff.undergroundBiomes.worldGen;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;


import Zeno410Utils.Zeno410Logger;
import java.util.logging.Logger;

/**
 *
 * @author Zeno410
 */
public class UBChunkProvider extends ChunkProviderWrapper {
    BiomeUndergroundDecorator decorator;
    static Logger logger = new Zeno410Logger("UBChunkProvider").logger();
    int level = 0;
    public final int dimension;
    public UBChunkProvider(IChunkProvider toWrap, BiomeUndergroundDecorator _decorator, int dimension){
        super(toWrap);

        logger.info("UB generation wrapping " + toWrap.toString());
        decorator = _decorator;
        this.dimension = dimension;
    }

    public Chunk provideChunk(int i, int j) {
        Chunk result = super.provideChunk(i,j);
        decorator.replaceChunkBlocks(result, i, j, dimension);
        return result;
    }

    @Override
    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        super.populate(ichunkprovider, i, j);
        decorator.replaceChunkOres(ichunkprovider,i, j);
    }

    
}
