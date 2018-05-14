package apritree.compat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ExternalTickingCap {
    @CapabilityInject(IExternalTick.class)
    public static Capability<IExternalTick> EXTERNALTICK = null;

    public static class Impl implements Capability.IStorage<IExternalTick> {
        @Override
        public NBTBase writeNBT(Capability<IExternalTick> cap, IExternalTick tick, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<IExternalTick> cap, IExternalTick tick, EnumFacing side, NBTBase nbt) {

        }
    }

    public static class Default implements IExternalTick {
        @Override
        public boolean canTick() {
            return false;
        }

        @Override
        public void doTick() {

        }
    }
}
