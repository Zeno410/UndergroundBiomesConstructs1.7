
package exterminatorJeff.undergroundBiomes.intermod;

import exterminatorJeff.undergroundBiomes.api.BlockCodes;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumn;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;
import highlands.worldgen.WorldGenMountain;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 *
 * @author Zeno410
 */
public class WorldGenUBMountain extends WorldGenerator
{

    private int minHeight;
    private int maxHeight;

    private int snowrocksand;
    private boolean notifyFlag;

    private World worldObj;
    private Random random;

    /** Constructor
     * @param minH minimum height of tree trunk
     * @param maxH max possible height above minH
     * @param notify whether or not to notify blocks of the change
     *  Generally false for world generation.
     */
    public WorldGenUBMountain(int minH, int maxH, boolean notify, int type)
    {
        this.minHeight = minH;
        this.maxHeight = maxH;
        this.notifyFlag = notify;
        this.snowrocksand = type;
    }

    //public boolean generate(World world, Random random, int locX, int locY, int locZ)
    //{
    //	return true;
    //}
    public boolean generate(World world, Random random, int locX, int locY, int locZ)
    {
    	this.worldObj = world;
    	this.random = random;

    	int height = minHeight + random.nextInt(maxHeight);
    	int radius = height;
        BlockCodes snowBlock = new BlockCodes(Blocks.snow,0);
        BlockCodes sandstoneBlock = new BlockCodes(Blocks.sandstone,0);
    	//System.out.println("H:"+height+" X:"+locX+" Z:"+locZ);
        UBStrataColumnProvider columnProvider =
                    UBAPIHook.ubAPIHook.dimensionalStrataColumnProvider
                    .ubStrataColumnProvider(world.provider.dimensionId);

    	for(int x = (int)Math.ceil(locX - radius); x <= (int)Math.ceil(locX + radius); x++){
			for(int z = (int)Math.ceil(locZ - radius); z <= (int)Math.ceil(locZ + radius); z++){
				double xfr = z - locZ;
				double zfr = x - locX;
                UBStrataColumn column = columnProvider.strataColumn(x,z);
				int dist = (int)Math.sqrt(xfr * xfr + zfr * zfr);

				if(dist <= radius){
					//overwrites trees rather than placing the mountain on top of them.
					for(locY = world.getTopSolidOrLiquidBlock(x, z); locY > 0; locY--){
						Block block = world.getBlock(x,  locY, z);
						if(block != null && block.isOpaqueCube() && !block.isWood(world, x, locY, z) && !block.isLeaves(world, x, locY, z))break;
					}

					int h = locY + height - dist;
					for(int i = locY; i < h; i++){
						if(snowrocksand == 0 && h-i <4)setBlockInWorld(x, i, z, snowBlock);
						else if(snowrocksand == 2 && h-i <4)setBlockInWorld(x, i, z, sandstoneBlock);
						else if(random.nextInt(3)== 0) setBlockInWorld(x, i, z, column.cobblestone(i));
						else setBlockInWorld(x, i, z,column.stone(i));
					}
					if(snowrocksand == 0 && h > 62)
						setBlockInWorld(x, h, z, snowBlock);
				}
			}
		}
    	this.worldObj = null;
    	return true;
    }

    private void setBlockInWorld(int x, int y, int z, BlockCodes blockCodes){
			if(notifyFlag) worldObj.setBlock(x, y, z, blockCodes.block, blockCodes.metadata, 3);
		    else worldObj.setBlock(x, y, z, blockCodes.block, blockCodes.metadata, 2);
    }

}
