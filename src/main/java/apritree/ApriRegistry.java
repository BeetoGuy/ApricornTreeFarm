package apritree;

import apritree.compat.ImmersiveEngineering;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityMechanicalAnvil;
import com.pixelmonmod.pixelmon.config.PixelmonCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import apritree.block.*;
import apritree.config.ApriConfig;
import apritree.item.ItemApricorn;
import apritree.item.ItemMasterballParts;
import apritree.utils.AnvilRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "apritree")
public class ApriRegistry
{
    public static List<Block> BLOCKS = Lists.newArrayList();
    public static List<Item> ITEMS = Lists.newArrayList();
    public static Block apricornLeafOne = new BlockApricornLeafOne().setRegistryName("apricorn_leaf_one");
    public static Block apricornLeafTwo = new BlockApricornLeafTwo().setRegistryName("apricorn_leaf_two");
    public static Block apricornOne = new BlockApricornComplex().setRegistryName("apricorn_one");
    public static Block apricornTwo = new BlockApricornPrimary().setRegistryName("apricorn_two");
    public static Block apricornSapling = new BlockApricornSapling().setRegistryName("apricorn_sapling");

    public static Item apricorn = new ItemApricorn().setRegistryName("apricorn");
    public static Item masterball = new ItemMasterballParts().setRegistryName("masterball_parts");
    public static Item ball_mold = new Item().setRegistryName("ball_mold").setUnlocalizedName("apritree:ball_mold").setCreativeTab(PixelmonCreativeTabs.utilityBlocks);

    public static void init()
    {
        registerProduct(apricornLeafOne);
        registerProduct(apricornLeafTwo);
        registerProduct(apricornOne);
        registerProduct(apricornTwo);
        registerSapling(apricornSapling);
        registerItem(apricorn);
        registerItem(masterball);
        registerItem(ball_mold);
    }

    public static void initCrafting()
    {
        registerAnvilCrafting();
        if (ApriConfig.masterBallCrafting) {
            registerAnvilRecipe(new ItemStack(masterball, 1, 0), new ItemStack(masterball, 1, 1));
            GameRegistry.addSmelting(new ItemStack(apricorn, 1, 0), new ItemStack(apricorn, 1, 1), 0.1F);
        }
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
