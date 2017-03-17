package pixelplus.proxy;

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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import pixelplus.PixelRegistry;
import pixelplus.block.BlockApricornLeafOne;
import pixelplus.block.BlockApricornLeafTwo;
import pixelplus.block.BlockApricornPlant;
import pixelplus.utils.ColorUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRendering()
    {
        String[] variants = {"black", "white", "pink", "green", "blue", "yellow", "red", "purple"};
        registerSaplingModel("pixelplus:apricorn_sapling", PixelRegistry.apricornSapling, variants);
        variants = new String[]{"active=false,facing=north"};
        if(Loader.isModLoaded("betterwithmods"))
            registerBlockModel("pixelplus:mech_driver", PixelRegistry.mechanicalDriver, variants);
        if(Loader.isModLoaded("IC2"))
            registerBlockModel("pixelplus:electric_driver", PixelRegistry.electricDriver, variants);
        registerBlockModel("pixelplus:energetic_driver", PixelRegistry.energeticDriver, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=black", "check_decay=true,decayable=false,type=white", "check_decay=true,decayable=false,type=pink", "check_decay=true,decayable=false,type=green"};
        registerBlockModel("pixelplus:apricorn_leaf_one", PixelRegistry.apricornLeafOne, variants);
        variants = new String[]{"check_decay=true,decayable=false,type=blue", "check_decay=true,decayable=false,type=yellow", "check_decay=true,decayable=false,type=red", "check_decay=true,decayable=false,type=purple"};
        registerBlockModel("pixelplus:apricorn_leaf_two", PixelRegistry.apricornLeafTwo, variants);
        registerItemModel(PixelRegistry.apricorn, 0, "pixelplus:purple_apricorn");
        registerItemModel(PixelRegistry.apricorn, 1, "pixelplus:purple_apricorn_cooked");
        registerItemModel(PixelRegistry.masterball, 0, "pixelplus:masterball_disc");
        registerItemModel(PixelRegistry.masterball, 1, "pixelplus:masterball_lid");
        registerItemModel(PixelRegistry.masterball, 2, "pixelplus:steel_disc");
        registerItemModel(PixelRegistry.masterball, 3, "pixelplus:steel_base");

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
                    if(state.getBlock() == PixelRegistry.apricornLeafOne)
                    {
                        int color = state.getValue(BlockApricornLeafOne.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                    else if(state.getBlock() == PixelRegistry.apricornLeafTwo)
                    {
                        int color = state.getValue(BlockApricornLeafTwo.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, new Block[] {PixelRegistry.apricornLeafOne, PixelRegistry.apricornLeafTwo});
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
        }, new Block[] {PixelRegistry.apricornOne, PixelRegistry.apricornTwo});
        final ItemColors itCol = Minecraft.getMinecraft().getItemColors();
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                if(tintIndex > -1 && stack.getItem() instanceof ItemBlock)
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    int leafColor = ColorizerFoliage.getFoliageColorBasic();
                    if(block == PixelRegistry.apricornLeafOne)
                    {
                        int color = PixelRegistry.apricornLeafOne.getStateFromMeta(stack.getItemDamage()).getValue(BlockApricornLeafOne.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                    else if(block == PixelRegistry.apricornLeafTwo)
                    {
                        int color = PixelRegistry.apricornLeafTwo.getStateFromMeta(stack.getItemDamage()).getValue(BlockApricornLeafTwo.APRICORNS).getColor();
                        return ColorUtil.blendColors(leafColor, color);
                    }
                }
                return -1;
            }
        }, new Block[] {PixelRegistry.apricornLeafOne, PixelRegistry.apricornLeafTwo});
        itCol.registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex)
            {
                if(tintIndex > -1 && stack.getItem() instanceof ItemBlock)
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if(block instanceof BlockApricornPlant)
                        return ((BlockApricornPlant)block).getEnumApricorn(block.getStateFromMeta(stack.getItemDamage())).getColor();
                }
                return -1;
            }
        }, new Block[] {PixelRegistry.apricornOne, PixelRegistry.apricornTwo});
    }

    public void registerSaplingModel(String location, @Nonnull Block block, String... variants)
    {
        for(int i = 0; i < variants.length; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location + "_" + variants[i], "inventory"));
    }

    public void registerBlockModel(String location, @Nonnull Block block, String... variants)
    {
        for(int i = 0; i < variants.length; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location, variants[i]));
    }

    public void registerItemModel(Item item, int meta, String location)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
    }
}
