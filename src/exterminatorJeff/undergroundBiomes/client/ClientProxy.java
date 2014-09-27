package exterminatorJeff.undergroundBiomes.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import java.net.URL;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;

import exterminatorJeff.undergroundBiomes.common.CommonProxy;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;

public class ClientProxy extends CommonProxy{

    public void registerRenderThings(OreUBifier oreUBifier) {
        RenderingRegistry.registerBlockHandler(new RenderUBOre(oreUBifier));
        //MinecraftForgeClient.preloadTexture("/exterminatorJeff/undergroundBiomes/textures/BlockTextures.png");
        //MinecraftForgeClient.preloadTexture("/exterminatorJeff/undergroundBiomes/textures/Items.png");
    }

    public void setUpBlockNames()
    {

        Language language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
        {
            String lang = language.getLanguageCode();
            URL urlResource = this.getClass().getResource("/assets/undergroundbiomes/lang/"+lang+".lang");
            if (urlResource != null)
            {
                LanguageRegistry.instance().loadLocalization(urlResource, lang, false);
            }
        }
    }
}
