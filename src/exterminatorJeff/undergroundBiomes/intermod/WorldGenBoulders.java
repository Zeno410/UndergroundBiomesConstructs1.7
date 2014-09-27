
package exterminatorJeff.undergroundBiomes.intermod;

import exterminatorJeff.undergroundBiomes.api.BlockCodes;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumn;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBoulders extends WorldGenerator
{
    /** Stores Block for WorldGenTallGrass */
    private BlockTallGrass tallGrass;
    private int tallGrassMetadata;


    public WorldGenBoulders(BlockTallGrass tallgrass, int par2)
    {
        this.tallGrass = tallgrass;
        this.tallGrassMetadata = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {

        for (boolean var6 = false; (par1World.getBlock(par3, par4, par5) == Blocks.leaves || par1World.isAirBlock(par3, par4, par5) ) && par4 > 0; --par4)
        {
            ;
        }
            int dimension = par1World.provider.dimensionId;
            UBStrataColumnProvider columnProvider =
                    UBAPIHook.ubAPIHook.dimensionalStrataColumnProvider.ubStrataColumnProvider(dimension);
                    BlockCodes cobble = columnProvider.strataColumn(par3, par5).cobblestone(par4);
        for (int var7 = 0; var7 < 128; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);


            int columnX = Integer.MAX_VALUE;
            int columnZ = Integer.MAX_VALUE;
            BlockCodes mossyCobblestone = new BlockCodes(Blocks.mossy_cobblestone,0);
            if (par1World.isAirBlock(var8, var9, var10) && this.tallGrass.canBlockStay(par1World, var8, var9, var10))
            {
                if(par2Random.nextInt(2) == 0){
                    par1World.setBlock(var8, var9, var10, this.tallGrass, this.tallGrassMetadata, 2);
                }
                else{
                    setBlockCodes(par1World,var8, var9, var10, (
                            par2Random.nextInt(8) == 0 ? mossyCobblestone:cobble));
                }
            }
        }

        return true;


    }

    private void setBlockCodes(World world, int x, int y, int z, BlockCodes blockCodes) {
        world.setBlock(x, y, z, blockCodes.block, blockCodes.metadata, 2);
    }
}
