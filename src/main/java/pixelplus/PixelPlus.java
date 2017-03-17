package pixelplus;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import pixelplus.config.PixConfig;
import pixelplus.inventory.PixelGuiHandler;
import pixelplus.proxy.CommonProxy;
import pixelplus.worldgen.gen.ApricornTreeGenerator;

@Mod(name="Pixel Plus", modid="pixelplus", version="1.0", dependencies="required-after:pixelmon;after:betterwithmods")
public class PixelPlus
{
    @Mod.Instance("pixelplus")
    public static PixelPlus INSTANCE;

    public static Logger logger;

    @SidedProxy(clientSide = "pixelplus.proxy.ClientProxy", serverSide = "pixelplus.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        logger = evt.getModLog();
        PixConfig.init(evt.getSuggestedConfigurationFile());
        PixelRegistry.init();
        proxy.registerRendering();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new PixelGuiHandler());
        PixelRegistry.initCrafting();
        proxy.registerColors();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        if (PixConfig.apricornTreeGen)
            GameRegistry.registerWorldGenerator(ApricornTreeGenerator.INSTANCE, 25);
        PixelRegistry.initAddons();
    }
}
