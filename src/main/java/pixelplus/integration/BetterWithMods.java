package pixelplus.integration;

import betterwithmods.BWCrafting;
import betterwithmods.util.item.ItemExt;
import betterwithmods.util.item.ItemStackMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import pixelplus.PixelRegistry;

public class BetterWithMods {
    public static void registerMasterball() {
        BWCrafting.addSteelShapedOreRecipe(new ItemStack(PixelRegistry.masterball, 1, 0), " MM ", "PMMP", 'M', new ItemStack(PixelRegistry.apricorn, 1, 1), 'P', new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "cooked_pink_apricorn"))));
    }

    public static void registerBuoyancy() {
        String[] colors = {"black", "white", "pink", "green", "red", "blue", "yellow"};
        ItemStackMap<Float> buoyancy = ItemExt.getBuoyancyRegistry();
        for (String str : colors) {
            buoyancy.put(getItem(str + "_apricorn"), OreDictionary.WILDCARD_VALUE, 1.0F);
            buoyancy.put(getItem("cooked_" + str + "_apricorn"), OreDictionary.WILDCARD_VALUE, 1.0F);
        }
        buoyancy.put(PixelRegistry.apricorn, OreDictionary.WILDCARD_VALUE, 1.0F);
        String[] berries = {"oran", "leppa", "rawst", "cheri", "chesto", "pecha", "aspear", "persim", "lum", "sitrus", "enigma", "pumkin", "drash", "eggant", "yago", "touga", "ginema", "apicot", "liechi", "ganlon", "salac", "petaya", "lansat", "starf",
        "custap", "micle", "aguav", "fig", "iapapa", "mago", "wiki", "occa", "passho", "wacan", "rindo", "yache", "chople", "kebia", "shuca", "coba", "payapa", "tanga", "charti", "kasib", "haban", "colbur", "babiri", "chilan", "roseli", "jaboca", "rowap", "kee", "maranga",
        "pomeg", "kelpsy", "qualot", "hondew", "grepa", "tamato"};
        for (String str : berries)
            buoyancy.put(getItem(str + "_berry"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("air_balloon"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("shed_shell"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("red_card"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("light_clay"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("mental_herb"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("white_herb"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("bright_powder"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("float_stone"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("reaper_cloth"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("quick_powder"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("black_belt"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("silver_powder"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("destiny_knot"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("choice_band"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("choice_scarf"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("muscle_band"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("never_melt_ice"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("silk_scarf"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("snowball"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("spell_tag"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("leftovers"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("focus_sash"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("focus_band"), OreDictionary.WILDCARD_VALUE, 1.0F);
        String[] mail = {"air", "bloom", "brick", "bridged", "bridgem", "bridges", "bridgev", "bubble", "dream", "fab", "favored", "flame", "glitter", "grass", "greet", "heart", "inquiry", "like", "mech", "mosaic", "orange", "reply", "retro", "rsvp", "snow", "space", "steel", "thanks", "wave", "wood"};
        for (String str : mail) {
            buoyancy.put(getItem("pokemail_" + str), OreDictionary.WILDCARD_VALUE, 1.0F);
        }
        buoyancy.put(getItem("heal_powder"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("energy_powder"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("energy_root"), OreDictionary.WILDCARD_VALUE, 1.0F);
        buoyancy.put(getItem("revival_herb"), OreDictionary.WILDCARD_VALUE, 1.0F);
    }

    private static Item getItem(String name) {
        return Item.REGISTRY.getObject(new ResourceLocation("pixelmon", name));
    }
}
