
package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.UBStoneCodes;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import Zeno410Utils.BlockState;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.client.RenderUBOre;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.BlockMetadataBase;
import exterminatorJeff.undergroundBiomes.common.block.BlockOverlay;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBOre;
import exterminatorJeff.undergroundBiomes.common.item.ItemUBOreBlock;
import Zeno410Utils.Acceptor;
import Zeno410Utils.ConcreteMutable;
import Zeno410Utils.Function;
import Zeno410Utils.KeyedRegistry;
import Zeno410Utils.MinecraftName;
import Zeno410Utils.Mutable;
import Zeno410Utils.Zeno410Logger;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBHidden;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBMetadataOre;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBReplaceable;
import exterminatorJeff.undergroundBiomes.common.item.ItemUBHiddenBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class registers blocks and prepares the decorator for converting ores to a UB base stone
 * @author Zeno410
 */
public final class OreUBifier {

    public static Logger logger = new Zeno410Logger("OreUBifier").logger();

    private boolean replacementActive;
    private UBVersionsDictionary blockReplacer = new UBVersionsDictionary();
    private ReplacedOres replacedOres = new ReplacedOres();
    private HashMap<Block,ItemStack> oreFor = new HashMap<Block,ItemStack>();
    private HashMap<Block,Block> overlayFor = new HashMap<Block,Block>();
    private HashMap<Block,Block> stoneFor = new HashMap<Block,Block>();
    private HashSet<Class> replacedBlockClasses = new HashSet<Class>();
    private BlockReplacer [] blockReplacers;

    private ConcreteMutable<Integer> renderID = new ConcreteMutable<Integer>();
    private Acceptor<Boolean> updateReplacement = new Acceptor<Boolean>() {
        public void accept(Boolean accepted) {
            replacementActive = accepted;
        }
    };


    public OreUBifier(Mutable<Boolean> replacementFlag) {
        this.replacementActive = replacementFlag.value();
        // send callback so replacement gets updated if setting change;
        replacementFlag.informOnChange(updateReplacement);
    }

    public void setupUBHidden(Block oreBlock,FMLPreInitializationEvent event){
        assert(event != null);
        registerHiddenBlock(oreBlock,UndergroundBiomes.igneousStone,"igneous");
        registerHiddenBlock(oreBlock,UndergroundBiomes.metamorphicStone,"metamorphic");
        registerHiddenBlock(oreBlock,UndergroundBiomes.sedimentaryStone,"sedimentary");
        replacedOres.setAll(oreBlock);
        this.replacedBlockClasses.add(oreBlock.getClass());
    }
    
    public void setupUBOre(Block oreBlock, String overlayName, FMLPreInitializationEvent event){
        assert(event != null);
        registerBlock(oreBlock,UndergroundBiomes.igneousStone,"igneous",overlayName);
        registerBlock(oreBlock,UndergroundBiomes.metamorphicStone,"metamorphic",overlayName);
        registerBlock(oreBlock,UndergroundBiomes.sedimentaryStone,"sedimentary",overlayName);
        replacedOres.setAll(oreBlock);
        this.replacedBlockClasses.add(oreBlock.getClass());
    }

    public void setupUBOre(Block oreBlock, String overlayName, int metadata, MinecraftName blockName,
            FMLPreInitializationEvent event){
        assert(event != null);
        replacedOres.set(oreBlock, metadata);
        this.replacedBlockClasses.add(oreBlock.getClass());
        registerBlockWithMetadata(oreBlock,UndergroundBiomes.igneousStone,"igneous",overlayName, metadata,blockName);
        registerBlockWithMetadata(oreBlock,UndergroundBiomes.metamorphicStone,"metamorphic",overlayName, metadata,blockName);
        registerBlockWithMetadata(oreBlock,UndergroundBiomes.sedimentaryStone,"sedimentary",overlayName, metadata,blockName);
    }


    private void registerBlock(Block oreBlock, BlockMetadataBase ubStone, String rockName, String overlayName) {
        BlockOverlay overlay = new BlockOverlay(overlayName);
        BlockUBOre ubOre = new BlockUBOre(ubStone,oreBlock,overlay,renderID);
        NamedBlock namer = new NamedBlock(rockName+"_"+oreBlock.getUnlocalizedName().substring(5));
        BlockOverlay.logger.info("block "+oreBlock+ " no metadata ");
        GameRegistry.registerBlock(ubOre, ItemUBOreBlock.class, namer.internal());
        //BlockOverlay.logger.info(namer.internal());
        // all the metadatas get replaced
        for (int i = 0; i < 16; i ++) {
            blockReplacer.item(oreBlock).ubversions[i].set(ubStone, ubOre);
        }
        oreFor.put(ubOre, new ItemStack(oreBlock,1,1));
        overlayFor.put(ubOre, overlay);
        stoneFor.put(ubOre, ubStone);
        int blockID = Block.getIdFromBlock(ubOre);
        Item matchedItem = Item.getItemById(blockID);
        Block matchedBlock = Block.getBlockFromItem(matchedItem);
        //BlockOverlay.logger.info("block "+blockID+" matched to "+matchedItem.toString()+" for "+matchedBlock.getUnlocalizedName());
    }

    private void registerHiddenBlock(Block oreBlock, BlockMetadataBase ubStone, String rockName) {
        BlockUBHidden ubOre = new BlockUBHidden(ubStone,oreBlock);
        NamedBlock namer = new NamedBlock(rockName+"_"+oreBlock.getUnlocalizedName().substring(5));
        BlockOverlay.logger.info("block "+oreBlock+ " no metadata ");
        GameRegistry.registerBlock(ubOre, ItemUBHiddenBlock.class, namer.internal());
        //BlockOverlay.logger.info(namer.internal());
        // all the metadatas get replaced
        for (int i = 0; i < 16; i ++) {
            blockReplacer.item(oreBlock).ubversions[i].set(ubStone, ubOre);
        }
        oreFor.put(ubOre, new ItemStack(oreBlock,1,1));
        stoneFor.put(ubOre, ubStone);
        int blockID = Block.getIdFromBlock(ubOre);
        Item matchedItem = Item.getItemById(blockID);
    }

    private void registerBlockWithMetadata(Block oreBlock, BlockMetadataBase ubStone, String rockName, String overlayName, 
            int metadata, MinecraftName metadataBlockName) {
        //overlayName.replace("metallurgy:/", "metallurgy:");
        //overlayName = "Metallurgy:"+overlayName.substring("metallurgy:/".length());
        BlockOverlay overlay = new BlockOverlay(overlayName);
        logger.info(metadataBlockName.localized() + " " + metadataBlockName.unlocalized());

        BlockState oreBlockState = new BlockState(oreBlock,metadata);
        BlockUBOre ubOre = new BlockUBMetadataOre(ubStone,oreBlockState,overlay,renderID,metadataBlockName);
        NamedBlock namer = null;
        BlockOverlay.logger.info("block "+oreBlock+ " metadata "+metadata + " " +overlayName);
        if (metadata == 0) {
            namer = new NamedBlock(rockName+"_"+oreBlock.getUnlocalizedName().substring(5));
        } else {
            namer = new NamedBlock(rockName+"_"+oreBlock.getUnlocalizedName().substring(5)+"."+metadata);
        }
        GameRegistry.registerBlock(ubOre, ItemUBOreBlock.class, namer.internal());
        //BlockOverlay.logger.info(namer.internal());
        blockReplacer.item(oreBlock).ubversions[metadata].set(ubStone, ubOre);
        oreFor.put(ubOre, new ItemStack(oreBlock,1,metadata));
        overlayFor.put(ubOre, overlay);
        stoneFor.put(ubOre, ubStone);
        int blockID = Block.getIdFromBlock(ubOre);
        Item matchedItem = Item.getItemById(blockID);
        Block matchedBlock = Block.getBlockFromItem(matchedItem);

        // test replacement process
        UBStoneCodes testUBStone = new UBStoneCodes(ubStone.namer,4);
        BlockState replacement = this.replacement(oreBlock, metadata, testUBStone, testUBStone);
        if ((!(replaces(oreBlock,metadata)))&&replacementActive) {
            if (UndergroundBiomes.crashOnProblems()) {
                BlockOverlay.logger.info("blueschist instanceof BlockMetadataBase " +(testUBStone.block instanceof BlockMetadataBase));
                MetadataUBVersions versions = blockReplacer.item(oreBlock);
                for (int i = 0; i < 16;i++) {
                    Block ore = versions.ubversions[i].ore((BlockMetadataBase)(testUBStone.block)).block();
                    if (ore == null) {
                        BlockOverlay.logger.info("null in "+i);
                    } else{
                        BlockOverlay.logger.info(ore.getLocalizedName()+ " " + ore.toString()+i);
                    }
                }
                throw new RuntimeException();
            }
        }
        if (!Block.isEqualTo(replacement.block,ubOre)) {
            if (UndergroundBiomes.crashOnProblems()) {
                throw new RuntimeException();
            } else {

            }
        }
        if (replacement.metadata != 4) {
            if (UndergroundBiomes.crashOnProblems()) throw new RuntimeException();
        }

        //BlockOverlay.logger.info("block "+blockID+" matched to "+matchedItem.toString()+" for "+matchedBlock.getUnlocalizedName());
    }

    public int getRenderID() {return renderID.value();}

    public void setRenderer(RenderUBOre renderer) {
        renderID.set(renderer.getRenderId());
    }

    public BlockMetadataBase baseStone(Block ubVersion) {
        Block result = stoneFor.get(ubVersion);
        if (result == null) {
            if (UndergroundBiomes.crashOnProblems()) {
                throw new RuntimeException("no ore for "+ubVersion.getUnlocalizedName());
            }
            return UndergroundBiomes.igneousStone;
        }
        return (BlockMetadataBase)result;

    }

    public Block overlayBlock(Block ubVersion) {
        Block result = overlayFor.get(ubVersion);
        if (result == null) {
            UndergroundBiomes.throwIfTesting("no overlay for "+ubVersion.getUnlocalizedName());
            return UndergroundBiomes.igneousStone;
        }
        return result;

    }

    public void registerOres() {
        for (Block block: this.oreFor.keySet()){
            ItemStack ore = oreFor.get(block);
            try{
                int oreID  = OreDictionary.getOreID(ore);
                //OreDictionary.registerOre(oreID, block);
                for (int metadata = 0; metadata<8; metadata++) {
                    ItemStack metadataBlock = new ItemStack(block,1,metadata);
                    OreDictionary.registerOre(oreID, metadataBlock);
                }
            } catch (NullPointerException e) {
                if (UndergroundBiomes.crashOnProblems()) throw e;
            }
        }
            // no action needed at present since the ore dictionary handles mined ore
            // and we mine ores as whatever block they come from.

    }

    private class UBVersions {
        private final HashMap<BlockMetadataBase,BlockUBReplaceable> converter =
                new HashMap<BlockMetadataBase,BlockUBReplaceable>();
        private final HashMap<BlockMetadataBase,ArrayList<BlockState>> convertedBlockStates =
                new HashMap<BlockMetadataBase,ArrayList<BlockState>>();

        public void set(BlockMetadataBase ubStone, BlockUBReplaceable ubOre) {
            converter.put(ubStone, ubOre);
            ArrayList<BlockState> blockStates = new ArrayList<BlockState>();
            for (int i = 0; i < BlockMetadataBase.metadatas; i++) {
                blockStates.add(new BlockState(ubOre.block(),i));
            }
            convertedBlockStates.put(ubStone, blockStates);
        }

        public boolean active() {return converter.size()>0;}

        public BlockUBReplaceable ore(BlockMetadataBase stone) {
            return converter.get(stone);
        }

        public BlockState convertedore(BlockMetadataBase stone, int metadata) {
            return convertedBlockStates.get(stone).get(metadata);
        }
    }

    private class MetadataUBVersions {
        private UBVersions ubversions [] = new UBVersions[16];
        MetadataUBVersions() {
            for (int i = 0; i < 16;i++) {
                ubversions[i] = new UBVersions();
            }
        }
    }

    private class UBVersionsDictionary extends KeyedRegistry<Block,MetadataUBVersions>{
        UBVersionsDictionary() {
            super(new Function<Block,MetadataUBVersions>() {
                @Override
                public MetadataUBVersions result(Block baseOre) {
                    return new MetadataUBVersions();
                }
            });
        }
    }

    public boolean replacementActive() {return replacementActive;}

    public boolean replaces(Block possibleOre, int metadata) {
        if (replacementActive) {
            boolean result = replacedOres.has(possibleOre, metadata);
            /* Code to test for missed ores
            if (result == false) {
                if (this.replacedBlockClasses.contains(possibleOre.getClass())) {
                    BlockOverlay.logger.info(possibleOre.toString() + " " + metadata + " " +
                            possibleOre.getClass().toString());
                }
            }*/
            return result;
        }
        return false;
    }

    public BlockState replacement(Block ore, int metadata, UBStoneCodes stone, UBStoneCodes defaultStone) {
        BlockMetadataBase baseStone;
        // we look for a UB stone to use for the ore
        if (stone.block instanceof BlockMetadataBase) {
            baseStone = (BlockMetadataBase)stone.block;
        } else {
            if (defaultStone.block instanceof BlockMetadataBase) {
                baseStone = (BlockMetadataBase)defaultStone.block;
            } else {
                // no UB stone available; stay with the default ore;
                return new BlockState(ore,metadata);
                //throw new RuntimeException();
            }
        }
        return this.blockReplacer.item(ore).ubversions[metadata].convertedore(baseStone,stone.metadata);
    }

    private class MetadataIndexedBlock {
        private Block [] blocks = new Block[16];
        public void set(Block block, int index) {
            blocks[index] = block;
        }

        public Block get(int index) {return blocks[index];}
    }

    private class ReplacedOres {
        private HashMap<Block,boolean []> flags = new HashMap<Block,boolean []>();

        private boolean [] assuredFlags(Block block) {
            boolean [] result = flags.get(block);
            if (result == null) {
                result = new boolean[16];
                flags.put(block, result);
            }
            return result;
        }

        void set(Block block, int metadata) {
            assuredFlags(block)[metadata] = true;
        }

        boolean has(Block block, int metadata) {
            boolean [] has = flags.get(block);
            if (has == null) return false;
            return has[metadata];
        }

        void setAll(Block block) {
            for (int i = 0; i < 16; i++) {
                set(block,i);
            }
        }
    }
    
    public interface BlockStateReplacer {
        public BlockState replacement(UBStoneCodes stone, UBStoneCodes defaultStone);
    }

    public BlockReplacer blockReplacer(int blockID) {
        return blockReplacers[blockID];
    }
    public interface BlockReplacer {
        public BlockStateReplacer replacer(int metadata);
    }

    private class ConcreteBlockStateReplacer implements BlockStateReplacer {
        private final UBVersions versions;
        ConcreteBlockStateReplacer(UBVersions versions) {
            this.versions = versions;
        }

        public BlockState replacement(UBStoneCodes stone, UBStoneCodes defaultStone) {
            if (stone.block instanceof BlockMetadataBase) {
                return versions.convertedore((BlockMetadataBase)stone.block, stone.metadata);
            }
            if (defaultStone.block instanceof BlockMetadataBase) {
                return versions.convertedore((BlockMetadataBase)defaultStone.block, stone.metadata);
            }
            return null;
        }
    }

    private class ConcreteBlockReplacer implements BlockReplacer {
        ConcreteBlockStateReplacer [] replacers = new ConcreteBlockStateReplacer[16];

        public ConcreteBlockReplacer(MetadataUBVersions metadataVersions){
            for (int i = 0 ; i < 16; i ++) {
                if (metadataVersions.ubversions[i].active()) {
                    replacers[i]= new ConcreteBlockStateReplacer(metadataVersions.ubversions[i]);
                }
            }
        }

        public BlockStateReplacer replacer(int metadata) {
            return replacers[metadata];
        }
    }

    public void renewBlockReplacers() {
        this.blockReplacers = new ConcreteBlockReplacer [4096];
        for (Block block: this.blockReplacer.keys()) {
            blockReplacers[Block.getIdFromBlock(block)] =
                    new ConcreteBlockReplacer(blockReplacer.item(block));
        }
    }

}

