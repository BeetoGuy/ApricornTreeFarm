package apritree.compat.ie;

import apritree.ApriRegistry;
import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pixelmonmod.pixelmon.enums.EnumBerry;
import com.pixelmonmod.pixelmon.items.heldItems.ItemBerry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class BerryPlantHandler extends BelljarHandler.DefaultPlantHandler {
    private HashSet<ComparableItemStack> SEEDS = Sets.newHashSet();
    private List<Item> IMPLEMENTED = Lists.newArrayList();
    private Map<Item, EnumBerry> BERRIES = Maps.newHashMap();

    @Override
    public HashSet<ComparableItemStack> getSeedSet() {
        return SEEDS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockState[] getRenderedPlant(ItemStack seed, ItemStack soil, float growth, TileEntity tile) {
        int age = Math.min(5, Math.round(growth * 5));
        return new IBlockState[] {ApriRegistry.berryPlaceholder.getStateFromMeta(age)};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getRenderSize(ItemStack seed, ItemStack soil, float growth, TileEntity tile) {
        return .6875f;
    }

    @Override
    public float getGrowthStep(ItemStack seed, ItemStack soil, float growth, TileEntity tile, float fertilizer, boolean render) {
        return .0003125F * fertilizer;
    }

    public void register() {
        for (EnumBerry berry : EnumBerry.values()) {
            ItemStack seed = new ItemStack(berry.getBerry());
            if (!seed.isEmpty() && seed.getItem() instanceof ItemBerry) {
                if (berry.isImplemented) {
                    IMPLEMENTED.add(berry.getBerry());
                    BERRIES.put(berry.getBerry(), berry);
                }
                register(seed, new ItemStack[] {seed}, new ItemStack(Blocks.DIRT));
            }
        }
    }
}
