package pixelplus.block.tile;

import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import pixelplus.block.BlockDriver;
import pixelplus.block.BlockEnergeticDriver;

@Optional.InterfaceList({
        @Optional.Interface(iface="net.darkhax.tesla.api.ITeslaConsumer", modid="tesla"),
        @Optional.Interface(iface="net.darkhax.tesla.api.ITeslaHolder", modid="tesla")
})
public class TileEntityEnergyDriver extends TileEntityDriver implements IEnergyReceiver, ITeslaConsumer, ITeslaHolder
{
    @CapabilityInject(ITeslaConsumer.class)
    public static Capability<ITeslaConsumer> TESLA_CONSUMER = null;
    @CapabilityInject(ITeslaHolder.class)
    public static Capability<ITeslaHolder> TESLA_HOLDER = null;

    private final EnergyStorage storage;
    private int energyRead;
    private int processProgress;

    public TileEntityEnergyDriver()
    {
        this.storage = new EnergyStorage(200000, 800);
    }

    @Override
    public void update()
    {
        if(!world.isRemote)
        {
            if (world.getBlockState(pos).getBlock() instanceof BlockDriver) {
                IBlockState state = world.getBlockState(pos);
                if (state.getValue(BlockDriver.ACTIVE) != requirementSatisfied())
                    world.setBlockState(pos, state.withProperty(BlockDriver.ACTIVE, requirementSatisfied()));
            }
            if(energyRead != storage.getEnergyStored())
                energyRead = storage.getEnergyStored();
        }
        super.update();
    }

    @Override
    public String getName()
    {
        return "inv.energetic.driver";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        storage.readFromNBT(tag);
        energyRead = storage.getEnergyStored();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        storage.writeToNBT(tag);
        return tag;
    }

    @Override
    public boolean indicatesPower()
    {
        return true;
    }

    @Override
    public int getScaledEnergy(int scale)
    {
        return energyRead * scale / 200000;
    }

    @Override
    public int getEnergy()
    {
        return Math.min(energyRead, 200000);
    }

    @Override
    public int getMaxEnergy()
    {
        return 200000;
    }

    @Override
    public boolean requirementSatisfied()
    {
        return storage.getEnergyStored() >= 40;
    }

    @Override
    public void processRequirement()
    {
        storage.modifyEnergyStored(-40);
        energyRead -= 40;
    }

    @Override
    public String getEnergyType()
    {
        if(Loader.isModLoaded("tesla"))
            return "energy.unit.tesla";
        return "energy.unit.rf";
    }

    @Override
    public void setAvailableEnergy(int value)
    {
        energyRead = value;
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing facing)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing facing)
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        if(this.getBlockType() instanceof BlockDriver)
            return facing != EnumFacing.DOWN && facing != world.getBlockState(pos).getValue(BlockDriver.FACING);
        return false;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(this.getBlockType() instanceof BlockEnergeticDriver) {
            if(facing != world.getBlockState(pos).getValue(BlockDriver.FACING))
                if (capability == TESLA_HOLDER || capability == TESLA_CONSUMER)
                return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(this.getBlockType() instanceof BlockEnergeticDriver) {
            if (facing != world.getBlockState(pos).getValue(BlockDriver.FACING)) {
                if (capability == TESLA_CONSUMER)
                    return TESLA_CONSUMER.cast(this);
                else if (capability == TESLA_HOLDER)
                    return TESLA_HOLDER.cast(this);
            }
        }
        return super.getCapability(capability, facing);
    }

    @Optional.Method(modid="tesla")
    @Override
    public long givePower(long power, boolean simulated)
    {
        return storage.receiveEnergy(power, simulated);
    }

    @Optional.Method(modid="tesla")
    @Override
    public long getStoredPower()
    {
        return storage.getEnergyStored();
    }

    @Optional.Method(modid="tesla")
    @Override
    public long getCapacity()
    {
        return storage.getMaxEnergyStored();
    }
}
