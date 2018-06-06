package apritree.compat;

import apritree.compat.enderio.EnderIOHandler;
import apritree.compat.industforegoing.IndustrialForegoing;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "apritree")
public class IntegrationHandler {
    @SubscribeEvent
    public static void registerModSubscriptions(RegistryEvent.Register<Block> evt) {
        if (Loader.isModLoaded("enderio")) {
            MinecraftForge.EVENT_BUS.register(EnderIOHandler.class);
        }
        if (Loader.isModLoaded("industrialforegoing")) {
            MinecraftForge.EVENT_BUS.register(IndustrialForegoing.class);
        }
    }
}
