package exterminatorJeff.undergroundBiomes.common.block;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.block.material.Material;

import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;

/**
 *
 * @author Zeno410
 */
public class UBStoneTextureProvider extends Block {

    public UBStoneTextureProvider() {
        super(Material.rock);
    }

    @Override
   public IIcon getIcon(int side, int meta){
       UndergroundBiomesBlock block = UndergroundBiomesBlockList.indexed(meta);
       return block.icon();
    }

   public String getBlockTypeName(int meta) {
       UndergroundBiomesBlock block = UndergroundBiomesBlockList.indexed(meta);
       return block.name();
   }

}
