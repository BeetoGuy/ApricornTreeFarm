package apritree.proxy;

import apritree.block.BlockApriLeafBase;
import apritree.block.BlockColoredPlanks;
import apritree.block.StateLibrary;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import apritree.ApriRegistry;
import apritree.block.BlockApricornPlant;
import apritree.utils.ColorUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = "apritree", value = Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @SubscribeEvent
    public static void registerRendering(ModelRegistryEvent evt)
    {
        String[] variants = {"black", "white", "pink", "green", "blue", "yellow", "red", "purple"};
        registerSaplingModel("apritree:apricorn_sapling", ApriRegistry.apricornSapling, variants);
        variants = new String[] {"orange", "cyan", "gray", "lightgray","brown", "lightblue", "lime", "magenta"};
        registerSaplingModel("apritree:apricorn_sapling", ApriRegistry.apricornSaplingCultivated, variants);
        variants = new String[] {"black", "green", "red", "lime"};
        registerSaplingModel("apritree:apricorn_sapling", ApriRegistry.apricornSaplingSpecial, variants);
        registerSaplingModel("apritree:apricorn_sapling", ApriRegistry.apricornSaplingUltra, "ultra_item");
        registerBlockModel("apritree:apricorn_log_ultra", ApriRegistry.ultraLog);
        variants = new String[]{"check_decay=true,decayable=false,type=black", "check_decay=true,decayable=false,type=white", "check_decay=true,decayable=false,type=pink", "check_decay=true,decayable=false,type=green"};
        registerBlockModel("apritree:apricorn_leaf_one", ApriRegistry.apricornLeafOne, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=blue", "check_decay=true,decayable=false,type=yellow", "check_decay=true,decayable=false,type=red", "check_decay=true,decayable=false,type=purple"};
        registerBlockModel("apritree:apricorn_leaf_two", ApriRegistry.apricornLeafTwo, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=orange", "check_decay=true,decayable=false,type=cyan", "check_decay=true,decayable=false,type=gray", "check_decay=true,decayable=false,type=light_gray"};
        registerBlockModel("apritree:apricorn_leaf_three", ApriRegistry.apricornLeafThree, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=brown", "check_decay=true,decayable=false,type=light_blue", "check_decay=true,decayable=false,type=lime", "check_decay=true,decayable=false,type=magenta"};
        registerBlockModel("apritree:apricorn_leaf_four", ApriRegistry.apricornLeafFour, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=gilded", "check_decay=true,decayable=false,type=dark", "check_decay=true,decayable=false,type=spotted", "check_decay=true,decayable=false,type=striped"};
        registerBlockModel("apritree:apricorn_leaf_five", ApriRegistry.apricornLeafFive, variants);
        registerBlockModel("apritree:apricorn_leaf_ultra", ApriRegistry.apricornLeafUltra, new String[] {"check_decay=true,decayable=false"});
        variants = new String[] {"axis=y,type=black", "axis=y,type=white", "axis=y,type=pink", "axis=y,type=green"};
        registerBlockModel("apritree:apricorn_log_one", ApriRegistry.logOne, variants);
        variants = new String[] {"axis=y,type=blue", "axis=y,type=yellow", "axis=y,type=red", "axis=y,type=purple"};
        registerBlockModel("apritree:apricorn_log_two", ApriRegistry.logTwo, variants);
        variants = new String[] {"axis=y,type=orange", "axis=y,type=cyan", "axis=y,type=gray", "axis=y,type=light_gray"};
        registerBlockModel("apritree:apricorn_log_three", ApriRegistry.logThree, variants);
        variants = new String[] {"axis=y,type=brown", "axis=y,type=light_blue", "axis=y,type=lime", "axis=y,type=magenta"};
        registerBlockModel("apritree:apricorn_log_four", ApriRegistry.logFour, variants);
        variants = new String[] {"axis=y,type=gilded", "axis=y,type=dark", "axis=y,type=spotted", "axis=y,type=striped"};
        registerBlockModel("apritree:apricorn_log_five", ApriRegistry.logFive, variants);
        variants = new String[] {"type=black", "type=white", "type=pink", "type=green", "type=blue", "type=yellow", "type=red", "type=purple", "type=orange", "type=cyan", "type=gray", "type=light_gray",
        "type=brown", "type=light_blue", "type=lime", "type=magenta"};
        registerBlockModel("apritree:apricorn_planks", ApriRegistry.plankColor, variants);
        variants = new String[] {"type=gilded", "type=dark", "type=spotted", "type=striped", "type=ultra"};
        registerBlockModel("apritree:apricorn_planks_special", ApriRegistry.plankSpecial, variants);
        registerBlockModel("apritree:machine", ApriRegistry.machine, new int[] {0, 4}, "charger=false,facing=north", "charger=true,facing=north");
        registerBlockModel("apritree:apricorn_workbench", ApriRegistry.apriWorkbench);
        variants = new String[]{"purple_apricorn", "purple_apricorn_cooked", "ultra_apricorn", "ultra_apricorn_cooked", "orange_apricorn", "cyan_apricorn", "gray_apricorn", "light_gray_apricorn", "brown_apricorn",
                "light_blue_apricorn", "lime_apricorn", "magenta_apricorn", "gilded_apricorn", "dark_apricorn", "spotted_apricorn", "striped_apricorn",
                "orange_apricorn_cooked", "cyan_apricorn_cooked", "gray_apricorn_cooked", "light_gray_apricorn_cooked", "brown_apricorn_cooked", "light_blue_apricorn_cooked", "lime_apricorn_cooked", "magenta_apricorn_cooked",
                "gilded_apricorn_cooked", "dark_apricorn_cooked", "spotted_apricorn_cooked", "striped_apricorn_cooked"};
        registerItemModels(ApriRegistry.apricorn, variants);
        variants = new String[] {"masterball_disc", "masterball_lid", "steel_disc", "steel_base", "beastball_disc", "beastball_lid"};
        registerItemModels(ApriRegistry.masterball, variants);
        registerItemModel(ApriRegistry.ball_mold, OreDictionary.WILDCARD_VALUE, "apritree:ball_mold");
        registerItemModel(ApriRegistry.apricornTool, OreDictionary.WILDCARD_VALUE, "apritree:apricorn_tools");
        if (ApriRegistry.mech_adapter != null)
            registerItemModel(ApriRegistry.mech_adapter, OreDictionary.WILDCARD_VALUE, "apritree:mechanical_augment");
    }

    @Override
    public void registerColors()
    {
        final BlockColors col = Minecraft.getMinecraft().getBlockColors();
        col.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex)
            {
                if(tintIndex > -1)
                {
                    int leafColor = world != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : ColorizerFoliage.getFoliageColorBasic();
                    if (state.getBlock() instanceof BlockApriLeafBase) {
                        int color = ((BlockApriLeafBase)state.getBlock()).getApricornFromState(state).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, ApriRegistry.apricornLeafOne, ApriRegistry.apricornLeafTwo, ApriRegistry.apricornLeafThree, ApriRegistry.apricornLeafFour, ApriRegistry.apricornLeafFive);
        col.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                if(tintIndex > -1)
                {
                    if(state.getBlock() instanceof BlockApricornPlant)
                        return ((BlockApricornPlant)state.getBlock()).getEnumApricorn(state).getColor();
                }
                return -1;
            }
        }, ApriRegistry.apricornOne, ApriRegistry.apricornTwo, ApriRegistry.apricornThree, ApriRegistry.apricornFour);
        col.registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                if (tintIndex > -1) {
                    if (state.getBlock() == ApriRegistry.plankColor)
                        return state.getValue(BlockColoredPlanks.APRICORNS).getColor();
                }
                return -1;
            }
        }, ApriRegistry.plankColor);
        final ItemColors itCol = Minecraft.getMinecraft().getItemColors();
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if(tintIndex > -1 && stack.getItem() instanceof ItemBlock)
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    int leafColor = ColorizerFoliage.getFoliageColorBasic();
                    if (block instanceof BlockApriLeafBase) {
                        int color = ((BlockApriLeafBase)block).getApricornFromState(block.getStateFromMeta(stack.getItemDamage())).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, ApriRegistry.apricornLeafOne, ApriRegistry.apricornLeafTwo, ApriRegistry.apricornLeafThree, ApriRegistry.apricornLeafFour, ApriRegistry.apricornLeafFive);
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                if(tintIndex > -1 && stack.getItem() instanceof ItemBlock)
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if(block instanceof BlockApricornPlant)
                        return ((BlockApricornPlant)block).getEnumApricorn(block.getStateFromMeta(stack.getItemDamage())).getColor();
                }
                return -1;
            }
        }, ApriRegistry.apricornOne, ApriRegistry.apricornTwo, ApriRegistry.apricornThree, ApriRegistry.apricornFour);
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if (tintIndex > -1 && stack.getItem() instanceof ItemBlock) {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == ApriRegistry.plankColor) {
                        return ApriRegistry.plankColor.getStateFromMeta(stack.getItemDamage()).getValue(BlockColoredPlanks.APRICORNS).getColor();
                    }
                }
                return -1;
            }
        }, ApriRegistry.plankColor);
    }

    public static void registerSaplingModel(String location, @Nonnull Block block, String... variants)
    {
        for(int i = 0; i < variants.length; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location + "_" + variants[i], "inventory"));
    }

    public static void registerBlockModel(String location, @Nonnull Block block, int[] meta, String... variants) {
        for (int i = 0; i < meta.length; i++) {
            int m = meta[i];
            String variant = variants[i];
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), m, new ModelResourceLocation(location, variant));
        }
    }

    public static void registerBlockModel(String location, @Nonnull Block block) {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), stack -> new ModelResourceLocation(location, "inventory"));
    }

    public static void registerBlockModel(String location, @Nonnull Block block, String[] variants)
    {
        for(int i = 0; i < variants.length; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location, variants[i]));
    }

    public static void registerItemModel(Item item, int meta, String location)
    {
        if (meta == OreDictionary.WILDCARD_VALUE)
            ModelLoader.setCustomMeshDefinition(item, stack -> new ModelResourceLocation(location, "inventory"));
        else
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
    }

    public static void registerItemModels(Item item, String[] locations) {
        for (int i = 0; i < locations.length; i++)
            registerItemModel(item, i, "apritree:" + locations[i]);
    }
}
