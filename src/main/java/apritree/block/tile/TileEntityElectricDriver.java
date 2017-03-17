package apritree.block.tile;


import ic2.api.energy.prefab.BasicSink;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;
import apritree.block.BlockDriver;

public class TileEntityElectricDriver extends TileEntityDriver
{
    private double maxEnergy = 10000;

    private BasicSink energy;
    private int energyRead;

    public TileEntityElectricDriver()
    {
        if(Loader.isModLoaded("IC2"))
            energy = new BasicSink(this, 40000, 1);
    }

    @Override
    public void update()
    {
        if(!world.isRemote) {
            if (world.getBlockState(pos).getBlock() instanceof BlockDriver) {
                IBlockState state = world.getBlockState(pos);
                if (state.getValue(BlockDriver.ACTIVE) != requirementSatisfied())
                    world.setBlockState(pos, state.withProperty(BlockDriver.ACTIVE, requirementSatisfied()));
            }
            energy.update();
            if(energyRead != (int)energy.getEnergyStored())
                energyRead = (int)energy.getEnergyStored();
        }
        super.update();
    }

    @Override
    public String getName()
    {
        return "inv.electric.driver";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        if(energy != null) {
            energy.readFromNBT(tag);
            energyRead = MathHelper.floor(energy.getEnergyStored());
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        if(energy != null)
            energy.writeToNBT(tag);
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
        return energyRead * scale / 40000;
    }

    @Override
    public void setAvailableEnergy(int value)
    {
        this.energyRead = value;
    }

    @Override
    public int getEnergy()
    {
        return energy != null ? energyRead : 0;
    }

    @Override
    public int getMaxEnergy()
    {
        return 40000;
    }

    @Override
    public boolean requirementSatisfied()
    {
        return energy != null && energy.getEnergyStored() > 10 && isValidInput(inventory.getStackInSlot(0));
    }

    @Override
    public void processRequirement()
    {
        energy.useEnergy(10);
        energyRead -= 10;
    }

    @Override
    public String getEnergyType()
    {
        return "energy.unit.eu";
    }

    @Override
    public void onChunkUnload()
    {
        if(energy != null)
            energy.onChunkUnload();
        super.onChunkUnload();
    }

    @Override
    public void invalidate()
    {
        if(energy != null)
            energy.invalidate();
        super.invalidate();
    }
}
