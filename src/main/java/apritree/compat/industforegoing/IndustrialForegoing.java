package apritree.compat.industforegoing;

import com.buuz135.industrial.api.plant.PlantRecollectable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IndustrialForegoing {
    @SubscribeEvent
    public static void registerApricornFarm(RegistryEvent.Register<PlantRecollectable> evt) {
        evt.getRegistry().register(new IFApricornHarvesting());
    }
}
