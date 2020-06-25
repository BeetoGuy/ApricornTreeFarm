package apritree.compat.ie;

import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import com.pixelmonmod.pixelmon.blocks.enums.EnumMultiPos;
import com.pixelmonmod.pixelmon.blocks.machines.BlockMechanicalAnvil;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityMechanicalAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;

public class AnvilHeatingWrapper extends ExternalHeaterHandler.HeatableAdapter<TileEntityMechanicalAnvil> {
    boolean canCook(TileEntityMechanicalAnvil anvil) {
        IBlockState state = anvil.getWorld().getBlockState(anvil.getPos());
        if (!(state.getBlock() instanceof BlockMechanicalAnvil)) return false;
        if (state.getValue(BlockMechanicalAnvil.MULTIPOS) != EnumMultiPos.BASE) return false;
        ItemStack input = anvil.func_70301_a(0);
        if (input.isEmpty()) return false;
        ItemStack output = TileEntityMechanicalAnvil.getHammerResult(input);
        if (output.isEmpty()) return false;
        ItemStack existingOutput = anvil.func_70301_a(2);
        if (existingOutput.isEmpty()) return true;
        if (!existingOutput.isItemEqual(output)) return false;
        int stackSize = existingOutput.getCount() + output.getCount();
        return stackSize <= anvil.func_70297_j_() && stackSize <= output.getMaxStackSize();
    }

    @Override
    public int doHeatTick(TileEntityMechanicalAnvil anvil, int energyAvailable, boolean redstone) {
        int energyConsumed = 0;
        boolean canCook = canCook(anvil);
        if (canCook) {
            boolean running = anvil.isRunning();
            int burnTime = anvil.fuelBurnTime;
            if (burnTime < 200) {
                anvil.currentFuelBurnTime = 200;
                int heatAttempt = 4;
                int heatEnergyRatio = Math.max(1, ExternalHeaterHandler.defaultFurnaceEnergyCost);
                int energyToUse = Math.min(energyAvailable, heatAttempt * heatEnergyRatio);
                int heat = energyToUse / heatEnergyRatio;
                if (heat > 0) {
                    if (burnTime + heat > 200) {
                        int reduction = burnTime + heat - 200;
                        heat -= reduction;
                    }
                    if (!anvil.getWorld().isRemote) {
                        anvil.fuelBurnTime += heat;//= Math.min(burnTime + heat, 200);
                        if (!running) {
                            ((WorldServer) anvil.getWorld()).getPlayerChunkMap().markBlockForUpdate(anvil.getPos());
                        }
                    }
                    energyConsumed += heat * heatEnergyRatio;
                }
            }
        }
        return energyConsumed;
    }
}
