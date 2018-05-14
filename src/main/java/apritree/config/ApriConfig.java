package apritree.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class ApriConfig {
    private static Configuration config;
    public static boolean masterBallCrafting;
    public static boolean apricornTreeGen;

    public static void init(File file) {
        config = new Configuration(file);
        masterBallCrafting = config.getBoolean("Master Ball Crafting", "Tweaks", false, "Allows Purple Apricorns to be grown and harvested.");
        apricornTreeGen = config.getBoolean("Apricorn Trees Generate", "Worldgen", false, "If enabled, Apricorn trees will generate with the world.");
        if (config.hasChanged())
            config.save();
    }
}
