package pixelplus;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import pixelplus.block.*;
import pixelplus.block.tile.TileEntityDriver;
import pixelplus.block.tile.TileEntityElectricDriver;
import pixelplus.block.tile.TileEntityEnergyDriver;
import pixelplus.block.tile.TileEntityMechanicalDriver;
import pixelplus.config.PixConfig;
import pixelplus.integration.BetterWithMods;
import pixelplus.item.ItemApricorn;
import pixelplus.item.ItemMasterballParts;
import pixelplus.utils.AnvilRegistry;

import java.util.ArrayList;
import java.util.List;

public class PixelRegistry
{
    public static Block mechanicalDriver;
    public static Block electricDriver;
    public static Block energeticDriver;
    public static Block apricornLeafOne;
    public static Block apricornLeafTwo;
    public static Block apricornOne;
    public static Block apricornTwo;
    public static Block apricornSapling;

    public static Item apricorn;
    public static Item masterball;

    public static void init()
    {
        if (Loader.isModLoaded("betterwithmods") && PixConfig.bwmDriverEnabled)
        {
            mechanicalDriver = new BlockMechanicalDriver();
            registerBlock(mechanicalDriver, "mech_driver");
            GameRegistry.registerTileEntity(TileEntityMechanicalDriver.class, "pixelplus_mechdriver");
        }
        if (Loader.isModLoaded("IC2") && PixConfig.ic2DriverEnabled)
        {
            electricDriver = new BlockElectricDriver();
            registerBlock(electricDriver, "electric_driver");
            GameRegistry.registerTileEntity(TileEntityElectricDriver.class, "pixelplus_elecdriver");
        }
        if (PixConfig.rfDriverEnabled) {
            energeticDriver = new BlockEnergeticDriver();
            registerBlock(energeticDriver, "energetic_driver");
            GameRegistry.registerTileEntity(TileEntityEnergyDriver.class, "pixelplus_enerdriver");
        }
        apricornLeafOne = new BlockApricornLeafOne();
        registerProduct(apricornLeafOne, "apricorn_leaf_one");
        apricornLeafTwo = new BlockApricornLeafTwo();
        registerProduct(apricornLeafTwo, "apricorn_leaf_two");
        apricornOne = new BlockApricornComplex();
        registerProduct(apricornOne, "apricorn_one");
        apricornTwo = new BlockApricornPrimary();
        registerProduct(apricornTwo, "apricorn_two");
        apricornSapling = new BlockApricornSapling();
        registerSapling(apricornSapling, "apricorn_sapling");
        apricorn = new ItemApricorn();
        registerItem(apricorn, "apricorn");
        masterball = new ItemMasterballParts();
        registerItem(masterball, "masterball_parts");
        if (PixConfig.apricornTreeCrafting) {
            for (int i = 0; i < 8; i++) {
                Item apricorn = i < 4 ? ((BlockApricornLeafOne) apricornLeafOne).getApricorn(apricornLeafOne.getStateFromMeta(i)) : ((BlockApricornLeafTwo) apricornLeafTwo).getApricorn(apricornLeafTwo.getStateFromMeta(i - 4));
                if (PixConfig.apricornTreeCrafting)
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(apricornSapling, 1, i), "treeSapling", apricorn, apricorn, apricorn));
            }
        }
        else if (PixConfig.driversExist) {
            if (PixConfig.apricornTreeGen)
                GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(apricornSapling, 1, 7), "treeSapling", new ItemStack(apricorn), new ItemStack(apricorn), new ItemStack(apricorn)));
        }
    }

    public static void initCrafting()
    {
        registerAnvilCrafting();
        registerAnvilRecipe("ingotAluminum", new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "aluminium_plate"))));
        if (PixConfig.driversExist)
            registerPlateSmashing();
        boolean steelLoaded = OreDictionary.doesOreNameExist("ingotSteel") && OreDictionary.getOres("ingotSteel").size() > 0;
        if (PixConfig.masterBallCrafting && PixConfig.driversExist) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 0), new ItemStack(masterball, 1, 1));
            if(steelLoaded) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(masterball, 1, 2), "SSS", 'S', "ingotSteel"));
                registerAnvilRecipe(new ItemStack(masterball, 1, 2), new ItemStack(masterball, 1, 3));
                GameRegistry.addShapelessRecipe(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "master_ball"))), new ItemStack(masterball, 1, 1), Blocks.STONE_BUTTON, new ItemStack(masterball, 1, 3));
            }
            else {
                GameRegistry.addShapelessRecipe(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "master_ball"))), new ItemStack(masterball, 1, 1), Blocks.STONE_BUTTON, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "iron_base"))));
                GameRegistry.addShapelessRecipe(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "master_ball"))), new ItemStack(masterball, 1, 1), Blocks.STONE_BUTTON, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "aluminum_base"))));
            }
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 0), new ItemStack(apricorn, 1, 1), 0.1F);
            if (PixConfig.rfDriverEnabled)
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(energeticDriver), "IGI", "RLR", "IGI", 'I', "ingotIron", 'R', "dustRedstone", 'G', "ingotGold", 'L', new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "mechanical_anvil")))));
            if (Loader.isModLoaded("IC2")&& PixConfig.ic2DriverEnabled)
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(electricDriver), "IBI", "CLC", "IBI", 'I', "ingotIron", 'B', "circuitBasic", 'C', "ingotCopper", 'L', new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "mechanical_anvil")))));
            if (Loader.isModLoaded("betterwithmods") && PixConfig.bwmDriverEnabled)
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mechanicalDriver), "IAI", "GLG", "IAI", 'I', "ingotIron", 'A', Block.REGISTRY.getObject(new ResourceLocation("betterwithmods", "axle")), 'L', new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "mechanical_anvil"))), 'G', "gearWood"));
            if (Loader.isModLoaded("betterwithmods") && PixConfig.bwmMasterBallCrafting) {
                BetterWithMods.registerMasterball();
            }
            else
                GameRegistry.addShapedRecipe(new ItemStack(masterball, 1, 0), "PpP", 'P', new ItemStack(apricorn, 1, 1), 'p', new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", "cooked_pink_apricorn"))));
        }
    }

    public static void initAddons() {
        if (Loader.isModLoaded("betterwithmods")) {
            BetterWithMods.registerBuoyancy();
        }
    }

    public static void registerItem(Item item, String name)
    {
        GameRegistry.register(item.setRegistryName(name));
    }

    public static void registerBlock(Block block, String name)
    {
        GameRegistry.register(block.setRegistryName(name));
        GameRegistry.register(new ItemBlock(block).setRegistryName(name));
    }

    public static void registerProduct(Block block, String name)
    {
        GameRegistry.register(block.setRegistryName(name));
        GameRegistry.register(new ItemBlockProduct(block).setRegistryName(name));
    }

    public static void registerSapling(Block block, String name)
    {
        GameRegistry.register(block.setRegistryName(name));
        GameRegistry.register(new ItemBlockSapling(block).setRegistryName(name));
    }

    public static void registerMetaBlock(Block block, String name)
    {
        GameRegistry.register(block.setRegistryName(name));
        GameRegistry.register(new ItemBlockMeta(block).setRegistryName(name));
    }

    private static void registerAnvilRecipe(String input, ItemStack output)
    {
        AnvilRegistry.getInstance().addRecipe(input, output);
    }

    private static void registerAnvilRecipe(ItemStack input, ItemStack output)
    {
        AnvilRegistry.getInstance().addRecipe(input, output);
    }

    private static void registerAnvilCrafting()
    {
        String[] discs = {"iron", "aluminum", "poke_ball", "great_ball", "ultra_ball", "level_ball", "moon_ball",
        "friend_ball", "love_ball", "safari_ball", "heavy_ball", "fast_ball", "repeat_ball", "timer_ball", "nest_ball", "net_ball",
        "dive_ball", "luxury_ball", "heal_ball", "dusk_ball", "premier_ball", "sport_ball", "quick_ball", "lure_ball"};
        for(int i = 0; i < discs.length; i++) {
            ItemStack stack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("pixelmon", discs[i] + "_disc")));
            registerAnvilRecipe(stack, TileEntityDriver.getHammerResult(stack));
        }
    }

    private static void registerPlateSmashing() {
        List<String> ores = new ArrayList<String>();
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ingot") && !ore.equals("ingotAluminum")) {
                ores.add(ore.substring(5));
            }
        }
        for (String ore : ores) {
            if (OreDictionary.doesOreNameExist("ingot" + ore) && OreDictionary.doesOreNameExist("plate" + ore)) {
                if (OreDictionary.getOres("ingot" + ore).size() > 0 && OreDictionary.getOres("plate" + ore).size() > 0)
                    registerAnvilRecipe("ingot" + ore, OreDictionary.getOres("plate" + ore).get(0).copy());
            }
        }
    }
}
