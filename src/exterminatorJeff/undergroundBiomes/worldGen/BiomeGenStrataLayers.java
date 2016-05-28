package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.StrataLayer;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaBlock;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;
import java.util.ArrayList;



public class BiomeGenStrataLayers {
    
    public StrataLayer[][] layers;
    private final UndergroundBiomesSettings settings;
    
    /*Sedimentary rock IDs:
     * 0 - limestone
     * 1 - chalk
     * 2 - shale
     * 3 - siltstone
     * 4 - lignite
     * 5 - flint
     * 6 - greywacke
     * 7 - chert
     */
    
    /*Metamorphic rock IDs:
     * 2 - marble
     */
    
    public BiomeGenStrataLayers(UndergroundBiomesSettings settings){
        layers = new StrataLayer[30][];
        this.settings = settings;
        if (UndergroundBiomes.harmoniousStrata()) {
            createHarmoniousLayers();
        } else {
            createLayers();
        }
    }
    
    public void createLayers(){
        NamedBlock sedimentaryBlockID = UBIDs.sedimentaryStoneName;
        NamedBlock metamorphicBlockID = UBIDs.metamorphicStoneName;
        NamedBlock igneousBlockID = UBIDs.igneousStoneName;
        layers[0] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 0, 30, 32), new StrataLayer(sedimentaryBlockID, 1, 38, 41), new StrataLayer(sedimentaryBlockID, 6, 65, 70)});
        layers[1] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 33, 36), new StrataLayer(sedimentaryBlockID, 1, 38, 41), new StrataLayer(sedimentaryBlockID, 5, 60, 62), new StrataLayer(sedimentaryBlockID, 7, 75, 80)});
        layers[2] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 3, 30, 35), new StrataLayer(sedimentaryBlockID, 4, 26, 29), new StrataLayer(metamorphicBlockID, 2, 70, 74)});
        layers[3] = generatable(new StrataLayer[]{new StrataLayer(NamedVanillaBlock.sandstone, 0, 40, 43), new StrataLayer(NamedVanillaBlock.sand, 0, 44, 47), new StrataLayer(sedimentaryBlockID, 1, 55, 57)});
        layers[4] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 7, 29, 33), new StrataLayer(sedimentaryBlockID, 5, 42, 44), new StrataLayer(sedimentaryBlockID, 4, 90, 105)});
        layers[5] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 33, 35), new StrataLayer(sedimentaryBlockID, 3, 38, 41), new StrataLayer(sedimentaryBlockID, 6, 72, 79)});
        layers[6] = generatable(new StrataLayer[]{new StrataLayer(metamorphicBlockID, 2, 30, 35), new StrataLayer(sedimentaryBlockID, 0, 39, 44), new StrataLayer(NamedVanillaBlock.sandstone, 0, 52, 54), new StrataLayer(NamedVanillaBlock.sand, 0, 55, 60)});
        layers[7] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 0, 33, 35), new StrataLayer(sedimentaryBlockID, 3, 45, 49), new StrataLayer(sedimentaryBlockID, 6, 80, 85)});
        layers[8] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 1, 30, 32), new StrataLayer(metamorphicBlockID, 2, 70, 74), new StrataLayer(sedimentaryBlockID, 4, 75, 79)});
        layers[9] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 31, 35), new StrataLayer(sedimentaryBlockID, 5, 39, 42), new StrataLayer(sedimentaryBlockID, 7, 62, 65)});
        
        //Layers for vanilla stone biomes
        layers[10] = generatable(new StrataLayer[]{new StrataLayer(igneousBlockID, 2, 12, 18), new StrataLayer(igneousBlockID, 6, 24, 29), new StrataLayer(metamorphicBlockID, 2, 80, 85)});
        layers[11] = generatable(new StrataLayer[]{new StrataLayer(igneousBlockID, 5, 13, 22), new StrataLayer(metamorphicBlockID, 6, 29, 36), new StrataLayer(metamorphicBlockID, 3, 80, 128)});
        
    }

    public void createHarmoniousLayers(){
        NamedBlock sedimentaryBlockID = UBIDs.sedimentaryStoneName;
        NamedBlock metamorphicBlockID = UBIDs.metamorphicStoneName;
        NamedBlock igneousBlockID = UBIDs.igneousStoneName;
        layers[0] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 0, 30, 32), new StrataLayer(sedimentaryBlockID, 1, 38, 41), new StrataLayer(sedimentaryBlockID, 6, 65, 70)});
        layers[5] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 33, 36), new StrataLayer(sedimentaryBlockID, 1, 38, 41), new StrataLayer(sedimentaryBlockID, 5, 60, 62), new StrataLayer(sedimentaryBlockID, 7, 75, 80)});
        layers[2] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 3, 30, 35), new StrataLayer(sedimentaryBlockID, 4, 26, 29), new StrataLayer(metamorphicBlockID, 2, 70, 74)});
        layers[3] = generatable(new StrataLayer[]{new StrataLayer(NamedVanillaBlock.sandstone, 0, 40, 43), new StrataLayer(NamedVanillaBlock.sand, 0, 44, 47), new StrataLayer(sedimentaryBlockID, 1, 55, 57)});
        layers[4] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 7, 29, 33), new StrataLayer(sedimentaryBlockID, 5, 42, 44), new StrataLayer(sedimentaryBlockID, 4, 90, 105)});
        layers[1] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 33, 35), new StrataLayer(sedimentaryBlockID, 3, 38, 41), new StrataLayer(sedimentaryBlockID, 6, 72, 79)});
        layers[6] = generatable(new StrataLayer[]{new StrataLayer(metamorphicBlockID, 2, 30, 35), new StrataLayer(sedimentaryBlockID, 0, 39, 44), new StrataLayer(NamedVanillaBlock.sandstone, 0, 52, 54), new StrataLayer(NamedVanillaBlock.sand, 0, 55, 60)});
        layers[7] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 0, 33, 35), new StrataLayer(sedimentaryBlockID, 3, 45, 49), new StrataLayer(sedimentaryBlockID, 4, 80, 85)});
        layers[8] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 1, 30, 32), new StrataLayer(metamorphicBlockID, 2, 70, 74), new StrataLayer(sedimentaryBlockID, 6, 75, 79)});
        layers[9] = generatable(new StrataLayer[]{new StrataLayer(sedimentaryBlockID, 2, 31, 35), new StrataLayer(sedimentaryBlockID, 5, 39, 42), new StrataLayer(sedimentaryBlockID, 7, 62, 65)});

        //Layers for vanilla stone biomes
        layers[10] = generatable(new StrataLayer[]{new StrataLayer(igneousBlockID, 2, 12, 18), new StrataLayer(igneousBlockID, 6, 24, 29), new StrataLayer(metamorphicBlockID, 2, 80, 85)});
        layers[11] = generatable(new StrataLayer[]{new StrataLayer(igneousBlockID, 5, 13, 22), new StrataLayer(metamorphicBlockID, 6, 29, 36), new StrataLayer(metamorphicBlockID, 3, 80, 128)});

    }

    public StrataLayer [] generatable(StrataLayer[] possible) {
        ArrayList<StrataLayer> accepted = new ArrayList<StrataLayer>();
        for (int i = 0; i < possible.length; i++) {
            NamedBlock block = possible[i].layerBlock;
            int metadata = possible[i].layerMetadataID;
            if (settings.generationAllowed(block, metadata)) accepted.add(possible[i]);
        }
        StrataLayer[] result = new StrataLayer[accepted.size()];
        return accepted.toArray(result);
    }
}
