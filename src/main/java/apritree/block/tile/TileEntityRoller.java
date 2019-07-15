package apritree.block.tile;

import apritree.block.BlockMachine;
import apritree.item.ICapabilityAddon;
import apritree.utils.AnvilRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityRoller extends TileEntity implements ITickable {
    public ItemStackHandler inventory = new RollerInventory(this);
    public ItemStackHandler sidedInv = new SidedInventory((RollerInventory)inventory);
    public EnergyCap storage = new EnergyCap();
    public int progressTime = 0;
    private boolean validateContents = false;
    private int energyRead = 0;

    @Override
    public void update() {
        if (this.world.isRemote) return;

        if (this.validateContents)
            validateContents();
        if (!inventory.getStackInSlot(2).isEmpty() && inventory.getStackInSlot(2).getItem() instanceof ICapabilityAddon) {
            ((ICapabilityAddon)inventory.getStackInSlot(2).getItem()).tick(this);
        }
        if(energyRead != storage.getEnergyStored())
            energyRead = storage.getEnergyStored();
        if (requirementSatisfied() && !inventory.getStackInSlot(0).isEmpty()) {
            if (isValidResult(inventory.getStackInSlot(0))) {
                progressTime++;
                processRequirement();
            }
            if (progressTime > 199) {
                processItem();
                progressTime = 0;
                this.validateContents = true;
            }
        } else if (progressTime > 0) {
            if (inventory.getStackInSlot(0).isEmpty() || !this.isValidInput(inventory.getStackInSlot(0)))
                progressTime = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag);
        if(tag.hasKey("ProcessCounter"))
            this.progressTime = tag.getInteger("ProcessCounter");
        if (tag.hasKey("Energy")) {
            energyRead = tag.getInteger("Energy");
            storage.setEnergy(energyRead);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        tag.merge(inventory.serializeNBT());
        tag.setInteger("ProcessCounter", this.progressTime);
        tag.setInteger("Energy", energyRead);
        return tag;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock() || oldState.getValue(BlockMachine.CHARGER) != newState.getValue(BlockMachine.CHARGER);
    }

    public int getEnergyRead() {
        return energyRead;
    }

    public boolean requirementSatisfied() {
        return storage.getEnergyStored() >= 20;
    }

    public void processRequirement() {
        energyRead -= storage.extractEnergy(20, false);
    }

    private boolean isValidResult(ItemStack stack) {
        if(!AnvilRegistry.getInstance().getOutput(stack).isEmpty())
            return inventory.getStackInSlot(1).isEmpty() || (itemMatches(AnvilRegistry.getInstance().getOutput(stack), inventory.getStackInSlot(1)) && inventory.getStackInSlot(1).getCount() + AnvilRegistry.getInstance().getOutput(stack).getCount() <= inventory.getStackInSlot(1).getMaxStackSize());
        return false;
    }

    private void validateContents() {
        if(!inventory.getStackInSlot(0).isEmpty()) {
            if(!AnvilRegistry.getInstance().inputExists(inventory.getStackInSlot(0))) {
                this.progressTime = 0;
            }
        }
        else
            this.progressTime = 0;
        this.validateContents = false;
    }

    private void processItem()
    {
        ItemStack result = ItemStack.EMPTY;
        if(!AnvilRegistry.getInstance().getOutput(inventory.getStackInSlot(0)).isEmpty())
            result = AnvilRegistry.getInstance().getOutput(inventory.getStackInSlot(0)).copy();
        if(!result.isEmpty())
        {
            if(!inventory.getStackInSlot(1).isEmpty() && itemMatches(inventory.getStackInSlot(1), result) && inventory.getStackInSlot(1).getCount() + result.getCount() <= inventory.getStackInSlot(1).getMaxStackSize()) {
                inventory.getStackInSlot(1).grow(result.getCount());
                inventory.getStackInSlot(0).shrink(1);
            }
            else if(inventory.getStackInSlot(1).isEmpty()) {
                inventory.setStackInSlot(1, result);
                inventory.getStackInSlot(0).shrink(1);
            }
            if(inventory.getStackInSlot(0).getCount() < 1)
                inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    public boolean isValidInput(ItemStack stack)
    {
        return AnvilRegistry.getInstance().inputExists(stack);
    }

    private boolean itemMatches(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = getUpdateTag();
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), tag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager mgr, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.getNbtCompound();
        readFromNBT(tag);
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY)
            return true;
        else if (inventory.getStackInSlot(2).getItem() instanceof ICapabilityAddon)
            return ((ICapabilityAddon)inventory.getStackInSlot(2).getItem()).hasCapability(capability, facing);
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing != null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(sidedInv);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        } else if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(storage);
        } else if (inventory.getStackInSlot(2).getItem() instanceof ICapabilityAddon) {
            return ((ICapabilityAddon)inventory.getStackInSlot(2).getItem()).getCapability(capability, facing, this);
        }
        return super.getCapability(capability, facing);
    }

    public class RollerInventory extends ItemStackHandler {
        private TileEntityRoller tile;
        public RollerInventory(TileEntityRoller tile) {
            super(3);
            this.tile = tile;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 0)
                return super.insertItem(slot, stack, simulate);
            else if (slot == 2 && stack.getItem() instanceof ICapabilityAddon)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }

        @Override
        public void onContentsChanged(int slot) {
            tile.markDirty();
        }
    }

    public class SidedInventory extends ItemStackHandler {
        private RollerInventory inv;
        public SidedInventory(RollerInventory inv) {
            super(3);
            this.inv = inv;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return inv.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 1)
                return inv.extractItem(slot, amount, simulate);
            return ItemStack.EMPTY;
        }

        @Override
        public void onContentsChanged(int slot) {
            inv.onContentsChanged(slot);
        }
    }

    public class EnergyCap extends EnergyStorage {
        public EnergyCap() {
            super(4000, 200, 20);
        }

        public void setEnergy(int energy) {
            this.energy = energy;
        }
    }
}
