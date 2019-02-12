package apritree.compat.industforegoing;

import apritree.block.BlockApriLeafBase;
import apritree.block.BlockApricornPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ApriCache {
    private NonNullList<BlockPos> pos;
    private boolean isApriTree;

    private World world;
    private BlockPos base;

    public ApriCache(World world, BlockPos base) {
        pos = NonNullList.create();
        this.world = world;
        this.base = base;
    }

    public NonNullList<ItemStack> grabCachedDrops() {
        NonNullList<ItemStack> drops = NonNullList.create();
        if (!pos.isEmpty()) {
            for (BlockPos p : pos) {
                world.getBlockState(p).getBlock().getDrops(drops, world, p, world.getBlockState(p), 0);
                world.setBlockToAir(p);
            }
            pos.clear();
        }
        return drops;
    }

    public boolean isApriTree() {
        return isApriTree;
    }

    public boolean isCached() {
        return !pos.isEmpty();
    }

    public void scanTree() {
        BlockPos pos = base;
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockLog) {
            boolean isFound = false;
            while (!isFound && world.getBlockState(pos.up()) instanceof BlockLog) {
                pos = pos.up();
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    BlockPos off = pos.offset(facing);
                    if (world.isBlockLoaded(off)) {
                        Block block = world.getBlockState(off).getBlock();
                        if (block instanceof BlockApriLeafBase) {
                            isFound = true;
                            isApriTree = true;
                            for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, 0, 2))) {
                                if (world.isBlockLoaded(p) && canHarvest(world.getBlockState(p))) {
                                    this.pos.add(p);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean canHarvest(IBlockState state) {
        return state.getBlock() instanceof BlockApricornPlant && state.getValue(BlockApricornPlant.STAGE) == 3;
    }
}
