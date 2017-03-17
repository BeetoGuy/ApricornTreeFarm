package pixelplus.block.tile;

import net.minecraft.nbt.NBTTagCompound;

public class EnergyStorage
{
    protected int energy;
    protected int capacity;
    protected int maxReceive;

    public EnergyStorage(int capacity, int maxReceive)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
    }

    public EnergyStorage readFromNBT(NBTTagCompound tag)
    {
        this.energy = tag.getInteger("Energy");
        if(energy > capacity)
            energy = capacity;
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        if(energy < 0)
            energy = 0;
        tag.setInteger("Energy", energy);
        return tag;
    }

    public EnergyStorage setCapacity(int capacity)
    {
        this.capacity = capacity;

        if(energy > capacity)
            energy = capacity;
        return this;
    }

    public EnergyStorage setMaxTransfer(int maxTransfer)
    {
        this.maxReceive = maxTransfer;
        return this;
    }

    public int getMaxTransfer()
    {
        return maxReceive;
    }

    public void setEnergyStored(int energy)
    {
        if(energy > capacity)
            this.energy = capacity;
        else if(energy < 0)
            this.energy = 0;
        else
            this.energy = energy;
    }

    public void modifyEnergyStored(int energy)
    {
        this.energy += energy;

        if(this.energy > capacity)
            this.energy = capacity;
        else if(this.energy < 0)
            this.energy = 0;
    }

    public int receiveEnergy(long maxReceive, boolean simulate)
    {
        int energyReceived = (int)Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if(!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    public int getEnergyStored()
    {
        return energy;
    }

    public int getMaxEnergyStored()
    {
        return capacity;
    }
}
