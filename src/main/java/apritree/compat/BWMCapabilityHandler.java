package apritree.compat;

import betterwithmods.api.tile.IMechanicalPower;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityMechanicalAnvil;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BWMCapabilityHandler {
    public static class MechanicalAnvilImpl implements IMechanicalPower {
        private TileEntityMechanicalAnvil anvil;

        public MechanicalAnvilImpl(TileEntityMechanicalAnvil anvil) {
            this.anvil = anvil;
        }

        @Override
        public Block getBlock() {
            return anvil.getBlockType();
        }

        @Override
        public World getBlockWorld() {
            return anvil.getWorld();
        }

        @Override
        public BlockPos getBlockPos() {
            return anvil.getPos();
        }

        @Override
        public int getMechanicalInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public int getMinimumInput(EnumFacing facing) {
            return 1;
        }

        @Override
        public int getMaximumInput(EnumFacing facing) {
            return 1;
        }

        @Override
        public int getMechanicalOutput(EnumFacing facing) {
            return 0;
        }
    }
}
