package apritree.compat.betterwithmods;

import apritree.block.BlockMachine;
import apritree.block.tile.TileEntityRoller;
import apritree.item.ICapabilityAddon;
import betterwithmods.api.BWMAPI;
import betterwithmods.api.capabilities.CapabilityMechanicalPower;
import betterwithmods.api.tile.IMechanicalPower;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class ItemMechanicalAddon extends Item implements ICapabilityAddon {
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityMechanicalPower.MECHANICAL_POWER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing, TileEntityRoller roller) {
        return capability == CapabilityMechanicalPower.MECHANICAL_POWER ? CapabilityMechanicalPower.MECHANICAL_POWER.cast(new RollerMechanicalWrapper(roller)) : null;
    }

    @Override
    public void tick(TileEntityRoller roller) {
        IMechanicalPower power = roller.getCapability(CapabilityMechanicalPower.MECHANICAL_POWER, EnumFacing.NORTH);
        if (power != null) {
            int input = power.calculateInput();
            if (input > 0) {
                roller.storage.receiveEnergy(20, false);
            }
        }
    }

    public class RollerMechanicalWrapper implements IMechanicalPower {
        private TileEntityRoller roller;

        public RollerMechanicalWrapper(TileEntityRoller roller) {
            this.roller = roller;
        }

        @Override
        public BlockPos getBlockPos() {
            return roller.getPos();
        }

        @Override
        public World getBlockWorld() {
            return roller.getWorld();
        }

        @Override
        public Block getBlock() {
            return roller.getBlockType();
        }

        @Override
        public int getMechanicalInput(EnumFacing facing) {
            if (getBlock() instanceof BlockMachine) {
                EnumFacing blockFace = getBlockWorld().getBlockState(getBlockPos()).getValue(BlockMachine.FACING);
                if (facing != blockFace) {
                    return BWMAPI.IMPLEMENTATION.getPowerOutput(getBlockWorld(), getBlockPos().offset(facing), facing.getOpposite());
                }
            }
            return 0;
        }

        @Override
        public int getMechanicalOutput(EnumFacing facing) {
            return -1;
        }

        @Override
        public int getMaximumInput(EnumFacing facing) {
            return 1;
        }

        @Override
        public int getMinimumInput(EnumFacing facing) {
            return 0;
        }
    }
}
