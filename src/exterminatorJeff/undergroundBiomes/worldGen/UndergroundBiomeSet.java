
package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaBlock;
import exterminatorJeff.undergroundBiomes.api.UBIDs;

/**
 *
 * @author Zeno410
 */
public class UndergroundBiomeSet {
    final BiomeGenStrataLayers strataLayers = new BiomeGenStrataLayers();

    public final BiomeGenUndergroundBase[] biomeList = new BiomeGenUndergroundBase[256];

    NamedBlock igneousID = UBIDs.igneousStoneName;
 
    public final BiomeGenUndergroundBase igneous1 = (new BiomeGenUndergroundBase(0, igneousID, 0, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[0]);
    public final BiomeGenUndergroundBase igneous2 = (new BiomeGenUndergroundBase(1, igneousID, 1, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[1]);
    public final BiomeGenUndergroundBase igneous3 = (new BiomeGenUndergroundBase(2, igneousID, 2, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[2]);
    public final BiomeGenUndergroundBase igneous4 = (new BiomeGenUndergroundBase(3, igneousID, 3, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[3]);
    public final BiomeGenUndergroundBase igneous5 = (new BiomeGenUndergroundBase(4, igneousID, 4, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[4]);
    public final BiomeGenUndergroundBase igneous6 = (new BiomeGenUndergroundBase(5, igneousID, 5, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[5]);
    public final BiomeGenUndergroundBase igneous7 = (new BiomeGenUndergroundBase(6, igneousID, 6, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[6]);
    public final BiomeGenUndergroundBase igneous8 = (new BiomeGenUndergroundBase(7, igneousID, 7, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[7]);

    public final BiomeGenUndergroundBase igneous9 = (new BiomeGenUndergroundBase(8, igneousID, 0, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[8]);
    public final BiomeGenUndergroundBase igneous10 = (new BiomeGenUndergroundBase(9, igneousID, 1, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[9]);
    public final BiomeGenUndergroundBase igneous11 = (new BiomeGenUndergroundBase(10, igneousID, 2, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[0]);
    public final BiomeGenUndergroundBase igneous12 = (new BiomeGenUndergroundBase(11, igneousID, 3, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[1]);
    public final BiomeGenUndergroundBase igneous13 = (new BiomeGenUndergroundBase(12, igneousID, 4, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[2]);
    public final BiomeGenUndergroundBase igneous14 = (new BiomeGenUndergroundBase(13, igneousID, 5, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[3]);
    public final BiomeGenUndergroundBase igneous15 = (new BiomeGenUndergroundBase(14, igneousID, 6, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[4]);
    public final BiomeGenUndergroundBase igneous16 = (new BiomeGenUndergroundBase(15, igneousID, 7, biomeList))
            .setName("Igneous").AddStrataLayers(strataLayers.layers[5]);

    static NamedBlock metamorphicID = UBIDs.metamorphicStoneName;

    public final BiomeGenUndergroundBase metamorphic1 = (new BiomeGenUndergroundBase(16, metamorphicID, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[6]);
    public final BiomeGenUndergroundBase metamorphic2 = (new BiomeGenUndergroundBase(17, metamorphicID, 1, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[7]);
    public final BiomeGenUndergroundBase metamorphic3 = (new BiomeGenUndergroundBase(18, metamorphicID, 1, biomeList))//to stop marble from being a base rock
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[8]);
    public final BiomeGenUndergroundBase metamorphic4 = (new BiomeGenUndergroundBase(19, metamorphicID, 3, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[9]);
    public final BiomeGenUndergroundBase metamorphic5 = (new BiomeGenUndergroundBase(20, metamorphicID, 4, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
    public final BiomeGenUndergroundBase metamorphic6 = (new BiomeGenUndergroundBase(21, metamorphicID, 5, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);
    public final BiomeGenUndergroundBase metamorphic7 = (new BiomeGenUndergroundBase(22, metamorphicID, 6, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[2]);
    public final BiomeGenUndergroundBase metamorphic8 = (new BiomeGenUndergroundBase(23, metamorphicID, 7, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[3]);

    public final BiomeGenUndergroundBase metamorphic9 = (new BiomeGenUndergroundBase(24, metamorphicID, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[4]);
    public final BiomeGenUndergroundBase metamorphic10 = (new BiomeGenUndergroundBase(25, metamorphicID, 1, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[5]);
    public final BiomeGenUndergroundBase metamorphic11 = (new BiomeGenUndergroundBase(26, metamorphicID, 1, biomeList))//to stop marble from being a base rock
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[6]);
    public final BiomeGenUndergroundBase metamorphic12 = (new BiomeGenUndergroundBase(27, metamorphicID, 3, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[7]);
    public final BiomeGenUndergroundBase metamorphic13 = (new BiomeGenUndergroundBase(28, metamorphicID, 4, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[8]);
    public final BiomeGenUndergroundBase metamorphic14 = (new BiomeGenUndergroundBase(29, metamorphicID, 5, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[9]);
    public final BiomeGenUndergroundBase metamorphic15 = (new BiomeGenUndergroundBase(30, metamorphicID, 6, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
    public final BiomeGenUndergroundBase metamorphic16 = (new BiomeGenUndergroundBase(31, metamorphicID, 7, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);

    public final BiomeGenUndergroundBase vanillaStone1 = (new BiomeGenUndergroundBase(32, NamedVanillaBlock.stone, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
    public final BiomeGenUndergroundBase vanillaStone2 = (new BiomeGenUndergroundBase(33, NamedVanillaBlock.stone, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);
    public final BiomeGenUndergroundBase vanillaStone3 = (new BiomeGenUndergroundBase(34, NamedVanillaBlock.stone, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[2]);
    public final BiomeGenUndergroundBase vanillaStone4 = (new BiomeGenUndergroundBase(35, NamedVanillaBlock.stone, 0, biomeList))
            .setName("Metamorphic").AddStrataLayers(strataLayers.layers[3]);

}
