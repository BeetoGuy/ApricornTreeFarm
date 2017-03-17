package apritree.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import apritree.block.tile.TileEntityElectricDriver;
import apritree.block.tile.TileEntityEnergyDriver;
import apritree.block.tile.TileEntityMechanicalDriver;

public class PixelGuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if(ID == 0)
        {
            if(tile instanceof TileEntityMechanicalDriver)
                return new ContainerDriver(player.inventory, (TileEntityMechanicalDriver)tile);
        }
        else if(ID == 1)
        {
            if(tile instanceof TileEntityElectricDriver)
                return new ContainerDriver(player.inventory, (TileEntityElectricDriver)tile);
        }
        else if(ID == 2)
        {
            if(tile instanceof TileEntityEnergyDriver)
                return new ContainerDriver(player.inventory, (TileEntityEnergyDriver)tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if(ID == 0)
        {
            if(tile instanceof TileEntityMechanicalDriver)
                return new GuiDriver(player.inventory, (TileEntityMechanicalDriver)tile);
        }
        else if(ID == 1)
        {
            if(tile instanceof TileEntityElectricDriver)
                return new GuiDriver(player.inventory, (TileEntityElectricDriver)tile);
        }
        else if(ID == 2)
        {
            if(tile instanceof TileEntityEnergyDriver)
                return new GuiDriver(player.inventory, (TileEntityEnergyDriver)tile);
        }
        return null;
    }
}
