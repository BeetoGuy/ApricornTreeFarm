package apritree.item;

import apritree.ApriRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemApricornTools extends Item {
    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return stack;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE) {
            ItemStack stack = player.getHeldItem(hand);
            world.setBlockState(pos, ApriRegistry.apriWorkbench.getDefaultState());
            stack.shrink(1);
            return EnumActionResult.SUCCESS;

        }
        return EnumActionResult.FAIL;
    }
}
