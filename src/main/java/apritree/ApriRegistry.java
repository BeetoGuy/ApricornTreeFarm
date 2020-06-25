package apritree;

import apritree.block.apricorns.*;
import apritree.block.leaves.*;
import apritree.block.logs.*;
import apritree.block.saplings.BlockApricornSapling;
import apritree.block.saplings.BlockCultivatedSapling;
import apritree.block.saplings.BlockSaplingUltra;
import apritree.block.saplings.BlockSpecialSapling;
import apritree.block.tile.TileEntityCharger;
import apritree.block.tile.TileEntityRoller;
import apritree.compat.IntegrationHandler;
import apritree.compat.ie.ImmersiveEngineering;
import apritree.item.ItemApricornTools;
import apritree.utils.BreedingRegistry;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.config.PixelmonCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import apritree.block.*;
import apritree.config.ApriConfig;
import apritree.item.ItemApricorn;
import apritree.item.ItemMasterballParts;
import apritree.utils.AnvilRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

@Mod.EventBusSubscriber(modid = "apritree")
public class ApriRegistry
{
    public static List<Block> BLOCKS = Lists.newArrayList();
    public static List<Item> ITEMS = Lists.newArrayList();
    public static Block apricornLeafOne = new BlockApricornLeafOne().setRegistryName("apricorn_leaf_one");
    public static Block apricornLeafTwo = new BlockApricornLeafTwo().setRegistryName("apricorn_leaf_two");
    public static Block apricornLeafThree = new BlockApricornLeafThree().setRegistryName("apricorn_leaf_three");
    public static Block apricornLeafFour = new BlockApricornLeafFour().setRegistryName("apricorn_leaf_four");
    public static Block apricornLeafFive = new BlockApricornLeafFive().setRegistryName("apricorn_leaf_five");
    public static Block apricornLeafUltra = new BlockLeafUltra().setRegistryName("apricorn_leaf_ultra");
    public static Block apricornOne = new BlockApricornComplex().setRegistryName("apricorn_one");
    public static Block apricornTwo = new BlockApricornPrimary().setRegistryName("apricorn_two");
    public static Block apricornThree = new BlockApricornCultivated().setRegistryName("apricorn_three");
    public static Block apricornFour = new BlockApricornCultivated2().setRegistryName("apricorn_four");
    public static Block apricornFive = new BlockApricornSpecial().setRegistryName("apricorn_five");
    public static Block apricornUltra = new BlockApricornUltra().setRegistryName("apricorn_ultra");
    public static Block apricornSapling = new BlockApricornSapling().setRegistryName("apricorn_sapling");
    public static Block apricornSaplingCultivated = new BlockCultivatedSapling().setRegistryName("apricorn_sapling_cultivated");
    public static Block apricornSaplingSpecial = new BlockSpecialSapling().setRegistryName("apricorn_sapling_special");
    public static Block apricornSaplingUltra = new BlockSaplingUltra().setRegistryName("apricorn_sapling_ultra");
    public static Block logOne = new BlockLog1().setRegistryName("apricorn_log_one");
    public static Block logTwo = new BlockLog2().setRegistryName("apricorn_log_two");
    public static Block logThree = new BlockLog3().setRegistryName("apricorn_log_three");
    public static Block logFour = new BlockLog4().setRegistryName("apricorn_log_four");
    public static Block logFive = new BlockLog5().setRegistryName("apricorn_log_five");
    public static Block ultraLog = new BlockLogUltra().setRegistryName("apricorn_log_ultra");
    public static Block plankColor = new BlockColoredPlanks().setRegistryName("apricorn_planks");
    public static Block plankSpecial = new BlockSpecialPlanks().setRegistryName("apricorn_planks_special");
    public static Block machine = new BlockMachine().setRegistryName("machine");
    public static Block apriWorkbench = new BlockApricornWorkbench().setRegistryName("apricorn_workbench");
    //These following blocks will not always be there
    public static Block berryPlaceholder = new BlockBerryPlaceholder();

    public static Item apricorn = new ItemApricorn().setRegistryName("apricorn");
    public static Item masterball = new ItemMasterballParts().setRegistryName("masterball_parts");
    public static Item ball_mold = new Item().setRegistryName("ball_mold").setTranslationKey("apritree:ball_mold").setCreativeTab(PixelmonCreativeTabs.utilityBlocks).setMaxStackSize(1);
    public static Item apricornTool = new ItemApricornTools().setRegistryName("apricorn_tools").setTranslationKey("apritree:apricorn_tools").setCreativeTab(CreativeTabs.TOOLS).setMaxStackSize(1);

    //These following items will not always be there
    public static Item mech_adapter = null;

    public static void init()
    {
        registerProduct(apricornLeafOne);
        registerProduct(apricornLeafTwo);
        registerProduct(apricornLeafThree);
        registerProduct(apricornLeafFour);
        registerProduct(apricornLeafFive);
        registerProduct(apricornLeafUltra);
        registerProduct(apricornOne);
        registerProduct(apricornTwo);
        registerProduct(apricornThree);
        registerProduct(apricornFour);
        registerProduct(apricornFive);
        registerProduct(apricornUltra);
        registerSapling(apricornSapling);
        registerSapling(apricornSaplingCultivated);
        registerSapling(apricornSaplingSpecial);
        registerSapling(apricornSaplingUltra);
        registerProduct(logOne);
        registerProduct(logTwo);
        registerProduct(logThree);
        registerProduct(logFour);
        registerProduct(logFive);
        registerProduct(ultraLog);
        registerMetaBlock(plankColor);
        registerMetaBlock(plankSpecial);
        registerMetaBlock(machine);
        registerBlock(apriWorkbench);
        GameRegistry.registerTileEntity(TileEntityRoller.class, new ResourceLocation("apritree", "roller"));
        GameRegistry.registerTileEntity(TileEntityCharger.class, new ResourceLocation("apritree", "charger"));
        registerItem(apricorn);
        registerItem(masterball);
        registerItem(ball_mold);
        registerItem(apricornTool);
        if (Loader.isModLoaded("immersiveengineering")) {
            registerBlock(berryPlaceholder, null);
        }
        IntegrationHandler.preInit();
    }

    public static void initCrafting()
    {
        registerAnvilCrafting();
        if (ApriConfig.gsBallCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 6), new ItemStack(masterball, 1, 7));
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 28), new ItemStack(apricorn, 1, 29), 0.1F);
            if (ApriConfig.beastBallCrafting)
                BreedingRegistry.addApriBreeding(EnumApricorns.ULTRA, EnumApricorns.GILDED, EnumApricorns.GS, ApriConfig.rareApriChance);
            else
                BreedingRegistry.addApriBreeding(EnumApricorns.BROWN, EnumApricorns.GILDED, EnumApricorns.GS, ApriConfig.rareApriChance);
        }
        if (ApriConfig.cherishBallCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 8), new ItemStack(masterball, 1, 9));
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 30), new ItemStack(apricorn, 1, 31), 0.1F);
            if (ApriConfig.gsBallCrafting)
                BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.GS, EnumApricorns.CHERISH, ApriConfig.rareApriChance);
            else if (ApriConfig.beastBallCrafting)
                BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.ULTRA, EnumApricorns.CHERISH, ApriConfig.rareApriChance);
            else if (ApriConfig.masterBallCrafting)
                BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.PURPLE, EnumApricorns.CHERISH, ApriConfig.rareApriChance);
            else
                BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.GILDED, EnumApricorns.CHERISH, ApriConfig.rareApriChance);

        }
        if (ApriConfig.masterBallCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 0), new ItemStack(masterball, 1, 1));
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 0), new ItemStack(apricorn, 1, 1), 0.1F);
            BreedingRegistry.addApriBreeding(EnumApricorns.BLUE, EnumApricorns.RED, EnumApricorns.PURPLE, ApriConfig.rareApriChance);
        } if (ApriConfig.beastBallCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 4), new ItemStack(masterball, 1, 5));
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 2), new ItemStack(apricorn, 1, 3), 0.1F);
        } if (ApriConfig.steelCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 2), new ItemStack(masterball, 1, 3));
        }
        registerAnvilRecipe(new ItemStack(masterball, 1, 10), new ItemStack(masterball, 1, 11));
        BreedingRegistry.addApriBreeding(EnumApricorns.BLUE, EnumApricorns.GREEN, EnumApricorns.CYAN, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.YELLOW, EnumApricorns.ORANGE, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.BLACK, EnumApricorns.WHITE, EnumApricorns.GRAY, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.GRAY, EnumApricorns.WHITE, EnumApricorns.LIGHTGRAY, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.ORANGE, EnumApricorns.BROWN, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.BLUE, EnumApricorns.WHITE, EnumApricorns.LIGHTBLUE, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.GREEN, EnumApricorns.WHITE, EnumApricorns.LIME, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.PINK, EnumApricorns.BLUE, EnumApricorns.MAGENTA, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.ORANGE, EnumApricorns.BLACK, EnumApricorns.GILDED, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.GREEN, EnumApricorns.BLACK, EnumApricorns.DARK, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.RED, EnumApricorns.BLACK, EnumApricorns.SPOTTED, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.BLUE, EnumApricorns.YELLOW, EnumApricorns.STRIPED, ApriConfig.commonApriChance);
        BreedingRegistry.addApriBreeding(EnumApricorns.MAGENTA, EnumApricorns.GILDED, EnumApricorns.DREAM, ApriConfig.commonApriChance);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 4), new ItemStack(apricorn, 1, 16), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 5), new ItemStack(apricorn, 1, 17), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 6), new ItemStack(apricorn, 1, 18), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 7), new ItemStack(apricorn, 1, 19), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 8), new ItemStack(apricorn, 1, 20), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 9), new ItemStack(apricorn, 1, 21), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 10), new ItemStack(apricorn, 1, 22), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 11), new ItemStack(apricorn, 1, 23), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 12), new ItemStack(apricorn, 1, 24), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 13), new ItemStack(apricorn, 1, 25), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 14), new ItemStack(apricorn, 1, 26), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 15), new ItemStack(apricorn, 1, 27), 0.1F);
        GameRegistry.addSmelting(new ItemStack(apricorn, 1, 32), new ItemStack(apricorn, 1, 33), 0.1F);
        for (int i = 0; i < 4; i++) {
            GameRegistry.addSmelting(new ItemStack(logOne, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
            GameRegistry.addSmelting(new ItemStack(logTwo, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
            GameRegistry.addSmelting(new ItemStack(logThree, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
            GameRegistry.addSmelting(new ItemStack(logFour, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
            GameRegistry.addSmelting(new ItemStack(logFive, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
            GameRegistry.addSmelting(new ItemStack(ultraLog, 1, i), new ItemStack(Items.COAL, 1, 1), 0.1F);
        }
        registerOre("treeLeaves", apricornLeafOne);
        registerOre("treeLeaves", apricornLeafTwo);
        registerOre("treeLeaves", apricornLeafThree);
        registerOre("treeLeaves", apricornLeafFour);
        registerOre("treeLeaves", apricornLeafFive);
        registerOre("treeLeaves", apricornLeafUltra);
        registerOre("treeSapling", apricornSapling);
        registerOre("treeSapling", apricornSaplingCultivated);
        registerOre("treeSapling", apricornSaplingSpecial);
        registerOre("treeSapling", apricornSaplingUltra);
        registerOre("logWood", logOne);
        registerOre("logWood", logTwo);
        registerOre("logWood", logThree);
        registerOre("logWood", logFour);
        registerOre("logWood", logFive);
        registerOre("logWood", ultraLog);
        registerOre("plankWood", plankColor);
        registerOre("plankWood", plankSpecial);
        //registerOre("plateAluminum", PixelmonItems.aluminiumPlate);
    }

    private static void registerOre(String name, Block block) {
        OreDictionary.registerOre(name, new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
    }

    private static void registerOre(String name, Item item) {
        OreDictionary.registerOre(name, new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
    }

    public static void initAddons() {
        if (Loader.isModLoaded("immersiveengineering")) {
            ImmersiveEngineering.addPressRecipes();
        }
    }

    public static void registerItem(Item item)
    {
        ITEMS.add(item);
    }

    public static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    public static void registerProduct(Block block)
    {
        registerBlock(block, new ItemBlockProduct(block));
    }

    public static void registerSapling(Block block)
    {
        registerBlock(block, new ItemBlockSapling(block));
    }

    public static void registerMetaBlock(Block block)
    {
        registerBlock(block, new ItemBlockMeta(block));
    }

    private static void registerBlock(Block block, ItemBlock item) {
        BLOCKS.add(block);
        if (item != null)
            ITEMS.add(item.setRegistryName(block.getRegistryName()));
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
            Item item = Item.REGISTRY.getObject(new ResourceLocation("pixelmon", discs[i] + "_disc"));
            Item output = Item.REGISTRY.getObject(new ResourceLocation("pixelmon", discs[i] + (i < 2 ? "_base" : "_lid")));
            if (item != null && item != Items.AIR && output != null && output != Items.AIR) {
                ItemStack stack = new ItemStack(item);
                ItemStack out = new ItemStack(output);
                registerAnvilRecipe(stack, out);
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        BLOCKS.forEach(evt.getRegistry()::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        ITEMS.forEach(evt.getRegistry()::register);
    }
}
