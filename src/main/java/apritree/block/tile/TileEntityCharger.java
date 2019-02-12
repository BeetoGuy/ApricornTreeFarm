package apritree.block.tile;

import apritree.block.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCharger extends TileEntity implements ITickable {
    private int chargeUnits = 0;
    public ItemStackHandler inventory = new ChargerInventory(this);

    @Override
    public void update() {
        if (chargeUnits > 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos pos = getPos().offset(facing);
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                        if (tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).receiveEnergy(20, false) > 0) {
                            chargeUnits -= 1;
                        }
                    }
                } if (chargeUnits == 0 && !refreshUnits()) break;
            }
        } else refreshUnits();
    }

    public int getChargeUnits() {
        return chargeUnits;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag);
        if(tag.hasKey("ChargeUnits"))
            this.chargeUnits = tag.getInteger("ChargeUnits");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        tag.merge(inventory.serializeNBT());
        tag.setInteger("ChargeUnits", this.chargeUnits);
        return tag;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock() || oldState.getValue(BlockMachine.CHARGER) != newState.getValue(BlockMachine.CHARGER);
    }

    private boolean refreshUnits() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            int burnTime = TileEntityFurnace.getItemBurnTime(stack);
            if (burnTime > 0) {
                chargeUnits = burnTime;
                stack.shrink(1);
                if (stack.getCount() < 1) {
                    if (stack.getItem().hasContainerItem(stack)) {
                        inventory.setStackInSlot(0, stack.getItem().getContainerItem(stack));
                    } else {
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                } else inventory.setStackInSlot(0, stack);
                return true;
            }
        } return false;
    }

    public class ChargerInventory extends ItemStackHandler {
        private TileEntityCharger charger;
        public ChargerInventory(TileEntityCharger charger) {
            super(1);
            this.charger = charger;
        }

        @Override
        public void onContentsChanged(int slot) {
            charger.markDirty();
        }
    }
}
