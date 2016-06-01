
package exterminatorJeff.undergroundBiomes.worldGen;

import Zeno410Utils.Accessor;
import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.api.biome.BOPInheritedBiome;
import java.util.HashSet;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 *
 * @author Zeno410
 */
public class CurrentWorldMemento {
    private int remembered;
    private int [] indices = new int [256];
    private World [] worlds = new World [256];
    private Manager manager;

    private CurrentWorldMemento(Manager manager) {
        this.manager = manager;
    }

    private void save() {
        BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0 ;i < biomes.length; i++) {
            BiomeGenBase biome = biomes[i];
            if (biome != null) {
                if (manager.bopHot()) {
                    biome= manager.bopAdjustedBiome(biome);
                }
                World currentWorld = biome.theBiomeDecorator.currentWorld;
                if (currentWorld != null) {
                    worlds[remembered] = currentWorld;
                    indices[remembered++] = i;
                    biome.theBiomeDecorator.currentWorld = null;
                }
            }
        }
    }

    void restore() {
        BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0;  i < remembered; i++) {
            BiomeGenBase biome = biomes[indices[i]];
            if (manager.bopHot()) {
                biome= manager.bopAdjustedBiome(biome);
            }
            biome.theBiomeDecorator.currentWorld = worlds[i];
        }
        remembered = 0;
        manager.release(this);
    }

    static class Manager {
        private HashSet<CurrentWorldMemento> available = new HashSet<CurrentWorldMemento>();
        private boolean bopHot;
        private Accessor<BOPInheritedBiome,BiomeGenBase> inheritedBiomeAccess;

        public Manager() {
            try {
                Class bopBiomeclass = BOPBiome.class;// to make sure it's there
                bopHot = true;
                inheritedBiomeAccess = new Accessor<BOPInheritedBiome,BiomeGenBase>(BiomeGenBase.class);
            } catch (java.lang.NoClassDefFoundError e) {
                bopHot = false;
            }
        }

        public BiomeGenBase bopAdjustedBiome(BiomeGenBase source) {
            if (source == null) return source;
            if (source instanceof BOPInheritedBiome) {
                return inheritedBiomeAccess.get((BOPInheritedBiome)source);
            }
            return source;
        }

        public boolean bopHot() {return bopHot;}

        private void release(CurrentWorldMemento freed) {
            available.add(freed);
        }

        CurrentWorldMemento memento() {
            CurrentWorldMemento result;
            if (available.size() >0) {
                result = available.iterator().next();
                available.remove(result);
            } else {result = new CurrentWorldMemento(this);}
            result.save();
            return result;
        }
    }

}