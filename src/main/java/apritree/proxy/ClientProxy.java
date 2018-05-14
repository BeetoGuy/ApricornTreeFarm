package apritree.proxy;

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
import apritree.block.BlockApricornLeafOne;
import apritree.block.BlockApricornLeafTwo;
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
        variants = new String[]{"check_decay=true,decayable=false,type=black", "check_decay=true,decayable=false,type=white", "check_decay=true,decayable=false,type=pink", "check_decay=true,decayable=false,type=green"};
        registerBlockModel("apritree:apricorn_leaf_one", ApriRegistry.apricornLeafOne, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=blue", "check_decay=true,decayable=false,type=yellow", "check_decay=true,decayable=false,type=red", "check_decay=true,decayable=false,type=purple"};
        registerBlockModel("apritree:apricorn_leaf_two", ApriRegistry.apricornLeafTwo, variants);
        registerItemModel(ApriRegistry.apricorn, 0, "apritree:purple_apricorn");
        registerItemModel(ApriRegistry.apricorn, 1, "apritree:purple_apricorn_cooked");
        registerItemModel(ApriRegistry.masterball, 0, "apritree:masterball_disc");
        registerItemModel(ApriRegistry.masterball, 1, "apritree:masterball_lid");
        registerItemModel(ApriRegistry.masterball, 2, "apritree:steel_disc");
        registerItemModel(ApriRegistry.masterball, 3, "apritree:steel_base");
        registerItemModel(ApriRegistry.ball_mold, OreDictionary.WILDCARD_VALUE, "apritree:ball_mold");
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
                    if(state.getBlock() == ApriRegistry.apricornLeafOne)
                    {
                        int color = state.getValue(BlockApricornLeafOne.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                    else if(state.getBlock() == ApriRegistry.apricornLeafTwo)
                    {
                        int color = state.getValue(BlockApricornLeafTwo.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, new Block[] {ApriRegistry.apricornLeafOne, ApriRegistry.apricornLeafTwo});
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
        }, new Block[] {ApriRegistry.apricornOne, ApriRegistry.apricornTwo});
        final ItemColors itCol = Minecraft.getMinecraft().getItemColors();
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if(tintIndex > -1 && stack.getItem() instanceof ItemBlock)
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    int leafColor = ColorizerFoliage.getFoliageColorBasic();
                    if(block == ApriRegistry.apricornLeafOne)
                    {
                        int color = ApriRegistry.apricornLeafOne.getStateFromMeta(stack.getItemDamage()).getValue(BlockApricornLeafOne.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                    else if(block == ApriRegistry.apricornLeafTwo)
                    {
                        int color = ApriRegistry.apricornLeafTwo.getStateFromMeta(stack.getItemDamage()).getValue(BlockApricornLeafTwo.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, new Block[] {ApriRegistry.apricornLeafOne, ApriRegistry.apricornLeafTwo});
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
        }, new Block[] {ApriRegistry.apricornOne, ApriRegistry.apricornTwo});
    }

    public static void registerSaplingModel(String location, @Nonnull Block block, String... variants)
    {
        for(int i = 0; i < variants.length; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location + "_" + variants[i], "inventory"));
    }

    public static void registerBlockModel(String location, @Nonnull Block block, String... variants)
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
}
