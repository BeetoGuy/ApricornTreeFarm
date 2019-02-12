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

    public static void init(File file) {
        config = new Configuration(file);
        masterBallCrafting = createBooleanProperty(config, "masterball", "_crafting", true, "Allows Purple Apricorns to be grown and harvested for Master Balls.");
        beastBallCrafting = createBooleanProperty(config, "beastball", "_crafting", true, "Allows Ultra Apricorns to be grown and harvested for Beast Balls.");
        steelCrafting = createBooleanProperty(config, "steeldisc", "_crafting", false, "Master and Beast Balls require steel bases to be crafted.");
        apricornTreeGen = createBooleanProperty(config, "generate_trees", "_worldgen", true, "If enabled, Apricorn trees will generate with the world.");
        if (config.hasChanged())
            config.save();
    }

    private static boolean createBooleanProperty(Configuration config, String name, String category, boolean defValue, String comment) {
        boolean bool = config.getBoolean(name, category, defValue, comment);
        CONDITIONS.put(name + category, bool);
        return bool;
    }

    public static class ConditionConfig implements IConditionFactory {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {
            String enabled = JsonUtils.getString(json, "enabled");
            return () -> CONDITIONS.getOrDefault(enabled, false);
        }
    }
}
