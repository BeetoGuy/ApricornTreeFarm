package apritree.compat.industforegoing;

import apritree.block.BlockApricornPlant;
import com.buuz135.industrial.api.plant.PlantRecollectable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class IFApricornHarvesting extends PlantRecollectable {
    public IFApricornHarvesting() {
        super("apricorns");
    }

    @Override
    public boolean canBeHarvested(World world, BlockPos pos, IBlockState state) {
        return state.getBlock() instanceof BlockApricornPlant && state.getValue(BlockApricornPlant.STAGE) == 3;
    }

    @Override
    public List<ItemStack> doHarvestOperation(World world, BlockPos pos, IBlockState state) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        state.getBlock().getDrops(stacks, world, pos, state, 0);
        world.setBlockToAir(pos);
        return stacks;
    }

    @Override
    public boolean shouldCheckNextPlant(World world, BlockPos pos, IBlockState state) {
        return true;
    }
}
