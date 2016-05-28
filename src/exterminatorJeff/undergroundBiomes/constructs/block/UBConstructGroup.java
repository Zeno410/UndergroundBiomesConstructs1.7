/*
 * author Zeno410
 * This is an abstract class for defining a group of similar objects based on the
 * Underground Biomes block classes
 * Each object is created by a combination of a block and a tileEntity which has
 * an integer index into a master list of UB blocks
 */

package exterminatorJeff.undergroundBiomes.constructs.block;

import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlock;
import exterminatorJeff.undergroundBiomes.constructs.util.UndergroundBiomesBlockList;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class UBConstructGroup {
        public Integer constructID;
        //public int baseID;
        public BlockMetadataBase baseBlock;
        public final String name;
        public Block construct; // the block representing the group of constructs

        public UBConstructGroup(String _name) {
            name  = _name;
        }

        public void define(int _constructID) {
            constructID = _constructID;
            construct = definedBlock();
            String test = construct.toString();
            //GameRegistry.registerBlock( construct, itemClass(),name);\
        }
        
        public ArrayList<IRecipe> recipes() {
            ArrayList<IRecipe> result = new ArrayList<IRecipe>();
            for (int ubIndex = 0; ubIndex <UndergroundBiomesBlockList.detailedBlockCount;ubIndex++) {
                IRecipe added = recipe(productItemDefiner(ubIndex),new StoneItemDefiner(ubIndex));
                if (added != null) result.add(added);
                added = rescueRecipe(productItemDefiner(ubIndex),new StoneItemDefiner(ubIndex));
                if (added != null) result.add(added);
            }
            return result;
        }
                
        abstract IRecipe recipe(ProductItemDefiner product, StoneItemDefiner stone);

        abstract IRecipe rescueRecipe(ProductItemDefiner product, StoneItemDefiner stone);

        abstract Block definedBlock(); // this should return the construct block for the group

        public BlockMetadataBase baseBlock() {
            return baseBlock;
        }

        /* the next two inner classes wrap references out of the package so descendent
        *  classes have minimal imports and couplings */
        
        class StoneItemDefiner {
            // this inner class hides the references to UndergroundBiomesBlockList
            final int stoneIndex;

            StoneItemDefiner(int _stoneIndex) {stoneIndex = _stoneIndex;}

            public final ItemStack stackOf(int items) {
                UndergroundBiomesBlock stone = ubBlock();
                return new ItemStack(stone.ubBlock,items,stone.metadata);
            }

            public final UndergroundBiomesBlock ubBlock() {
                return UndergroundBiomesBlockList.indexed(stoneIndex);
            }
            public final ItemStack one() {return stackOf(1);}
        }

        public ProductItemDefiner productItemDefiner(int index) {
            return new ProductItemDefiner(index);
        }

        public class ProductItemDefiner {
            // this inner class wraps the stoneIndex for "standard" UBConstruct item group
            // the assumption is that a damage index of x indicates that stone
            final int stoneIndex;

            ProductItemDefiner(int _stoneIndex) {stoneIndex = _stoneIndex;}

            public ItemStack stackOf(int items) {
                return new ItemStack(construct,items,stoneIndex);
            }

            public ItemStack one() {return stackOf(1);}
        }

}
