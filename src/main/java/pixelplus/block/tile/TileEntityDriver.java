package pixelplus.block.tile;

import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityMechanicalAnvil;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import com.pixelmonmod.pixelmon.items.ItemPokeballDisc;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pixelplus.block.BlockDriver;
import pixelplus.utils.AnvilRegistry;

public abstract class TileEntityDriver extends TileEntity implements ITickable
{
    public ItemStackHandler inventory = new DriverInventory(this);
    public ItemStackHandler sidedInv = new SidedDriverInventory((DriverInventory)inventory);
    public int progressTime;
    private boolean validateContents;

    @Override
    public void update()
    {
        if(this.world.isRemote)
            return;

        if(this.validateContents)
            validateContents();
        if(requirementSatisfied() && inventory.getStackInSlot(0) != null)
        {
            if(isValidResult(inventory.getStackInSlot(0))) {
                progressTime++;
                processRequirement();
            }
            if(progressTime > 199)
            {
                processItem();
                world.playSound(null, pos, SoundEvents.ENTITY_IRONGOLEM_DEATH, SoundCategory.BLOCKS, 0.7F, 1.0F);
                progressTime = 0;
                this.validateContents = true;
            }
        }
        else if(progressTime > 0) {
            if(inventory.getStackInSlot(0) == null)
                progressTime = 0;
            else if(!this.isValidInput(inventory.getStackInSlot(0)))
                progressTime = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag);
        if(tag.hasKey("ProcessCounter"))
            this.progressTime = tag.getInteger("ProcessCounter");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.merge(inventory.serializeNBT());
        tag.setInteger("ProcessCounter", this.progressTime);
        return tag;
    }

    public abstract boolean requirementSatisfied();

    public abstract void processRequirement();

    public abstract boolean indicatesPower();

    public abstract int getScaledEnergy(int scale);

    public abstract int getEnergy();

    public abstract void setAvailableEnergy(int value);

    public abstract int getMaxEnergy();

    public abstract String getEnergyType();

    public int getProgressTimeScaled(int scale)
    {
        return progressTime * scale / 200;
    }

    public boolean isOperating()
    {
        return progressTime > 0 && inventory.getStackInSlot(0) != null;
    }

    private void validateContents()
    {
        if(inventory.getStackInSlot(0) != null)
        {
            if(!AnvilRegistry.getInstance().inputExists(inventory.getStackInSlot(0)))
            {
                this.progressTime = 0;
            }
        }
        else
            this.progressTime = 0;
        this.validateContents = false;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public abstract String getName();

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentTranslation(getName());
    }

    private boolean isValidResult(ItemStack stack)
    {
        if(AnvilRegistry.getInstance().getOutput(stack) != null)
            return inventory.getStackInSlot(1) == null || (itemMatches(AnvilRegistry.getInstance().getOutput(stack), inventory.getStackInSlot(1)) && inventory.getStackInSlot(1).stackSize + AnvilRegistry.getInstance().getOutput(stack).stackSize <= 64);
        return false;
    }

    public static ItemStack getHammerResult(ItemStack stack)
    {
        return TileEntityMechanicalAnvil.getHammerResult(stack);
    }

    private void processItem()
    {
        ItemStack result = null;
        //if(TileEntityMechanicalAnvil.getHammerResult(inventory.getStackInSlot(0)) != null)
            //result = TileEntityMechanicalAnvil.getHammerResult(inventory.getStackInSlot(0)).copy();
        if(AnvilRegistry.getInstance().getOutput(inventory.getStackInSlot(0)) != null)
            result = AnvilRegistry.getInstance().getOutput(inventory.getStackInSlot(0)).copy();
        if(result != null)
        {
            if(inventory.getStackInSlot(1) != null && itemMatches(inventory.getStackInSlot(1), result)) {
                inventory.getStackInSlot(1).stackSize += result.stackSize;
                inventory.getStackInSlot(0).stackSize--;
            }
            else if(inventory.getStackInSlot(1) == null) {
                inventory.setStackInSlot(1, result);
                inventory.getStackInSlot(0).stackSize--;
            }
            if(inventory.getStackInSlot(0).stackSize < 1)
                inventory.setStackInSlot(0, null);
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
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound tag = getUpdateTag();
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), tag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager mgr, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound tag = pkt.getNbtCompound();
        readFromNBT(tag);
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing != null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(sidedInv);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public class DriverInventory extends ItemStackHandler {
        private TileEntityDriver tile;
        public DriverInventory(TileEntityDriver tile) {
            super(2);
            this.tile = tile;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 0)
                return super.insertItem(slot, stack, simulate);
            return null;
        }

        @Override
        public void onContentsChanged(int slot) {
            tile.markDirty();
        }
    }

    public class SidedDriverInventory extends ItemStackHandler {
        private DriverInventory inv;
        public SidedDriverInventory(DriverInventory inv) {
            super(2);
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
            return null;
        }

        @Override
        public void onContentsChanged(int slot) {
            inv.onContentsChanged(slot);
        }
    }
}
