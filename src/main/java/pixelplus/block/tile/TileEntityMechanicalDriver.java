package pixelplus.block.tile;

import pixelplus.PixelRegistry;
import pixelplus.block.BlockMechanicalDriver;

public class TileEntityMechanicalDriver extends TileEntityDriver
{
    @Override
    public String getName()
    {
        return "inv.mechanical.driver";
    }

    @Override
    public boolean indicatesPower()
    {
        return false;
    }

    @Override
    public int getScaledEnergy(int scale)
    {
        return 0;
    }

    @Override
    public int getEnergy()
    {
        return 0;
    }

    @Override
    public void setAvailableEnergy(int value) {}

    @Override
    public int getMaxEnergy()
    {
        return 0;
    }

    @Override
    public String getEnergyType()
    {
        return "energy.type.mechanical";
    }

    @Override
    public boolean requirementSatisfied()
    {
        return this.getBlockType() == PixelRegistry.mechanicalDriver && world.getBlockState(pos).getValue(BlockMechanicalDriver.ACTIVE);
    }

    @Override
    public void processRequirement()
    {

    }
}
