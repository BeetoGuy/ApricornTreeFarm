package apritree.compat.enderio;

import apritree.block.BlockApriLeafBase;
import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import crazypants.enderio.api.farm.AbstractFarmerJoe;
import crazypants.enderio.api.farm.IFarmer;
import crazypants.enderio.api.farm.IHarvestResult;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EIOApriFarmer extends AbstractFarmerJoe {
    @Override
    public boolean canHarvest(@Nonnull IFarmer farm, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockApricornPlant && state.getValue(BlockApricornPlant.STAGE) == 3;
    }

    @Override
    public boolean prepareBlock(@Nonnull IFarmer farm, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return false;
    }

    @Override
    public IHarvestResult harvestBlock(@Nonnull IFarmer farm, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        HarvestResult result = new HarvestResult();
        scanForTree(result, farm, pos);
        if (!result.getHarvestedBlocks().isEmpty()) {
            result.getHarvestedBlocks().forEach(p -> farm.getWorld().setBlockToAir(p));
        }
        return result;
    }

    @Override
    public boolean canPlant(@Nonnull ItemStack stack) {
        return false;
    }

    private void scanForTree(HarvestResult result, IFarmer farm, BlockPos pos) {
        World world = farm.getWorld();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockLog) {
            boolean foundApricorns = false;
            while (!foundApricorns && world.getBlockState(pos.up()) instanceof BlockLog) {
                pos = pos.up();
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    BlockPos off = pos.offset(facing);
                    if (world.isBlockLoaded(off)) {
                        Block block = world.getBlockState(off).getBlock();
                        if (block instanceof BlockApriLeafBase) {
                            foundApricorns = true;
                            for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, 0, 2))) {
                                if (world.isBlockLoaded(p) && canHarvest(farm, p, world.getBlockState(p))) {
                                    result.getDrops().add(new EntityItem(world, p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, EnumApricorns.getApricornFromEnum(((BlockApricornPlant)world.getBlockState(p).getBlock()).getEnumApricorn(world.getBlockState(p))).copy()));
                                    result.getHarvestedBlocks().add(p);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
