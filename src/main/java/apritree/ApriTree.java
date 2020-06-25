package apritree;

import apritree.client.PixelGuiHandler;
import apritree.compat.ExternalTickingCap;
import apritree.compat.IExternalTick;
import apritree.event.ShinyPokemonEventHandler;
import com.pixelmonmod.pixelmon.Pixelmon;
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

import java.util.Random;

@Mod(name="Apricorn Tree Farm", modid="apritree", version="2.4.0", dependencies="required-after:pixelmon@[8.0.0,);after:betterwithmods;after:immersiveengineering;after:industrialforegoing;before:bonsaitrees")
public class ApriTree
{
    @Mod.Instance("apritree")
    public static ApriTree INSTANCE;

    public static Logger logger;

    @SidedProxy(clientSide = "apritree.proxy.ClientProxy", serverSide = "apritree.proxy.CommonProxy")
    public static CommonProxy proxy;

    public Random rand = new Random();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        logger = evt.getModLog();
        CapabilityManager.INSTANCE.register(IExternalTick.class, new ExternalTickingCap.Impl(), ExternalTickingCap.Default::new);
        ApriConfig.init(evt.getSuggestedConfigurationFile());
        ApriRegistry.init();
        if (ApriConfig.shinyGSBall || ApriConfig.perfectCherishBall || ApriConfig.gen2ShinyBreed || ApriConfig.dittoLegend3IV)
            Pixelmon.EVENT_BUS.register(new ShinyPokemonEventHandler());
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
