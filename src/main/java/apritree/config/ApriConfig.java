package apritree.config;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.io.File;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class ApriConfig {
    private static Configuration config;
    private static HashMap<String, Boolean> CONDITIONS = Maps.newHashMap();
    public static boolean masterBallCrafting;
    public static boolean beastBallCrafting;
    public static boolean steelCrafting;
    public static boolean apricornTreeGen;
    public static boolean shinyGSBall;
    public static boolean gsBallCrafting;
    public static boolean perfectCherishBall;
    public static boolean cherishBallCrafting;
    public static boolean gen2ShinyBreed;
    public static boolean dittoLegend3IV;
    public static int rareApriChance;
    public static int commonApriChance;

    public static void init(File file) {
        config = new Configuration(file);
        masterBallCrafting = createBooleanProperty(config, "masterball", "_crafting", true, "Allows Purple Apricorns to be grown and harvested for Master Balls.");
        beastBallCrafting = createBooleanProperty(config, "beastball", "_crafting", true, "Allows Ultra Apricorns to be grown and harvested for Beast Balls.");
        steelCrafting = createBooleanProperty(config, "steeldisc", "_crafting", false, "Master and Beast Balls (plus GS and Cherish if enabled) require steel bases to be crafted.");
        apricornTreeGen = createBooleanProperty(config, "generate_trees", "_worldgen", true, "If enabled, Apricorn trees will generate with the world.");
        shinyGSBall = createBooleanProperty(config, "shiny_gs_ball", "_catching", false, "If enabled, GS Balls will make Pokemon shiny.");
        gsBallCrafting = createBooleanProperty(config, "gs_ball", "_crafting", true, "If enabled, GS Balls will be craftable.");
        perfectCherishBall = createBooleanProperty(config, "perfect_cherish_ball", "_catching", false, "If enabled, Cherish Balls will make all IVs perfect (value of 31)");
        cherishBallCrafting = createBooleanProperty(config, "cherish_ball", "_crafting", true, "If enabled, Cherish Balls will be craftable.");
        gen2ShinyBreed = createBooleanProperty(config, "gen2_shiny", "_breeding", false, "If enabled, a shiny parent makes default shiny egg chance 1/64 (Affected by existing modifiers).");
        dittoLegend3IV = createBooleanProperty(config, "ditto_legend_3IV", "_breeding", false, "If enabled, legendaries and UBs from Ditto-Ditto breeding have 3 perfect IVs.");
        rareApriChance = createIntProperty(config, "rare_apricorn_breeding", "_apricorns", 809, "The chance a rare apricorn will be bred.");
        commonApriChance = createIntProperty(config, "common_apricorn_breeding", "_apricorns", 100, "The chance a common apricorn will be bred.");
        if (config.hasChanged())
            config.save();
    }

    private static boolean createBooleanProperty(Configuration config, String name, String category, boolean defValue, String comment) {
        boolean bool = config.getBoolean(name, category, defValue, comment);
        CONDITIONS.put(name + category, bool);
        return bool;
    }

    private static int createIntProperty(Configuration config, String name, String category, int defValue, String comment) {
        return config.getInt(name, category, defValue, 30, 10000, comment);
    }

    public static class ConditionConfig implements IConditionFactory {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {
            String enabled = JsonUtils.getString(json, "enabled");
            return () -> CONDITIONS.getOrDefault(enabled, false);
        }
    }
}
