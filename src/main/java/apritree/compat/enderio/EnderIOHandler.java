package apritree.compat.enderio;

import crazypants.enderio.api.farm.IFarmerJoe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnderIOHandler {
    @SubscribeEvent
    public static void registerFarmer(RegistryEvent.Register<IFarmerJoe> evt) {
        evt.getRegistry().register(new EIOApriFarmer().setPriority(EventPriority.NORMAL).setRegistryName("apritree", "apricorns"));
    }
}
