package apritree.item;

import apritree.block.tile.TileEntityRoller;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public interface ICapabilityAddon {
    boolean hasCapability(Capability<?> capability, EnumFacing facing);

    <T> T getCapability(Capability<T> capability, EnumFacing facing, TileEntityRoller roller);

    void tick(TileEntityRoller roller);
}
