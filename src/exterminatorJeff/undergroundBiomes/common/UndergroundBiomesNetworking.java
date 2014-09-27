
package exterminatorJeff.undergroundBiomes.common;

import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;
import Zeno410Utils.Acceptor;
import exterminatorJeff.undergroundBiomes.network.DirectChannel;
import exterminatorJeff.undergroundBiomes.network.PacketPipeline;

/**
 *
 * @author Zeno410
 */
public class UndergroundBiomesNetworking {

    private final PacketPipeline pipeline;

    // this channel operates oddly; see the constructor
    public final DirectChannel<UndergroundBiomesSettings> settings;

    public UndergroundBiomesNetworking(PacketPipeline pipeline, UndergroundBiomesSettings settingsTarget) {
        this.pipeline = pipeline;

        // this is not following implicit contracts for DirectChannel. Incoming packets are read directly
        // into the settings variable and the object that's produced gets ignored
        settings  = new DirectChannel<UndergroundBiomesSettings>(
                pipeline,UndergroundBiomesSettings.streamer(settingsTarget),new Acceptor.Ignorer<UndergroundBiomesSettings>());
    }
}
