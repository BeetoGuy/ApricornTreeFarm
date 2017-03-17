package apritree.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class ApriConfig {
    private static Configuration config;
    public static boolean masterBallCrafting;
    public static boolean bwmMasterBallCrafting;
    public static boolean apricornTreeGen;
    public static boolean apricornTreeCrafting;
    public static boolean bwmDriverEnabled;
    public static boolean ic2DriverEnabled;
    public static boolean rfDriverEnabled;
    public static boolean driversExist;

    public static void init(File file) {
        config = new Configuration(file);
        masterBallCrafting = config.getBoolean("Master Ball Crafting", "Tweaks", true, "Allows Purple Apricorns to be grown and harvested. Will auto-disable if driver anvils don't exist.");
        bwmMasterBallCrafting = config.getBoolean("BWM Master Ball Crafting", "Tweaks", true, "If Better With Mods is loaded and Master Ball Crafting enabled, requires soulforged steel anvil to craft Master Ball lids.");
        apricornTreeCrafting = config.getBoolean("Apricorn Saplings Craftable", "Tweaks", false, "If enabled, Apricorn Saplings can be crafted with Apricorns and any sapling.");
        apricornTreeGen = config.getBoolean("Apricorn Trees Generate", "Worldgen", true, "If enabled, Apricorn trees will generate with the world.");
        bwmDriverEnabled = config.getBoolean("Better With Mods Anvil", "Items", true, "If enabled with Better With Mods in the same pack, Better With Mods Driver Anvil exists and can be crafted.");
        ic2DriverEnabled = config.getBoolean("IC2 Anvil", "Items", true, "If enabled with IC2 in the same pack, IC2 Driver Anvil exists and can be crafted.");
        rfDriverEnabled = config.getBoolean("Powered Anvil", "Items", true, "If enabled, RF/Tesla-compatible Driver Anvil exists and can be crafted. You might want a mod that has an RF/Tesla power system if you want this.");
        driversExist = bwmExists() || ic2Exists() || rfDriverEnabled;
        if (config.hasChanged())
            config.save();
    }

    private static boolean bwmExists() {
        return bwmDriverEnabled && Loader.isModLoaded("betterwithmods");
    }

    private static boolean ic2Exists() {
        return ic2DriverEnabled && Loader.isModLoaded("IC2");
    }
}
