package apritree;

import apritree.client.PixelGuiHandler;
import apritree.compat.ExternalTickingCap;
import apritree.compat.IExternalTick;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import apritree.config.ApriConfig;
import apritree.proxy.CommonProxy;
import apritree.worldgen.gen.ApricornTreeGenerator;

@Mod(name="Apricorn Tree Farm", modid="apritree", version="2.0.4", dependencies="required-after:pixelmon@[7.0.0,);after:betterwithmods;after:immersiveengineering;after:industrialforegoing;before:bonsaitrees")
public class ApriTree
{
    @Mod.Instance("apritree")
    public static ApriTree INSTANCE;

    public static Logger logger;

    @SidedProxy(clientSide = "apritree.proxy.ClientProxy", serverSide = "apritree.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        logger = evt.getModLog();
        CapabilityManager.INSTANCE.register(IExternalTick.class, new ExternalTickingCap.Impl(), ExternalTickingCap.Default::new);
        ApriConfig.init(evt.getSuggestedConfigurationFile());
        ApriRegistry.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new PixelGuiHandler());
        ApriRegistry.initCrafting();
        proxy.registerColors();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        if (ApriConfig.apricornTreeGen)
            GameRegistry.registerWorldGenerator(ApricornTreeGenerator.INSTANCE, 25);
        ApriRegistry.initAddons();
    }
}
