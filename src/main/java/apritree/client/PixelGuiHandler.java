package apritree.client;

import apritree.block.tile.TileEntityCharger;
import apritree.block.tile.TileEntityRoller;
import apritree.client.container.ContainerApricornWorkbench;
import apritree.client.container.ContainerCharger;
import apritree.client.container.ContainerRoller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class PixelGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (ID == 0 && tile instanceof TileEntityRoller) {
            TileEntityRoller roller = (TileEntityRoller)tile;
            return new ContainerRoller(player.inventory, roller, new GuiElementsRoller(roller));
        } else if (ID == 1 && tile instanceof TileEntityCharger) {
            TileEntityCharger charger = (TileEntityCharger)tile;
            return new ContainerCharger(player.inventory, charger, new GuiElementsCharger(charger));
        } else if (ID == 2) {
            return new ContainerApricornWorkbench(player.inventory, world, pos);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (ID == 0 && tile instanceof TileEntityRoller) {
            TileEntityRoller roller = (TileEntityRoller)tile;
            IGuiElements elements = new GuiElementsRoller(roller);
            return new GuiContainerDynamic(new ContainerRoller(player.inventory, roller, elements));
        } else if (ID == 1 && tile instanceof TileEntityCharger) {
            TileEntityCharger charger = (TileEntityCharger)tile;
            IGuiElements elements = new GuiElementsCharger(charger);
            return new GuiContainerDynamic(new ContainerCharger(player.inventory, charger, elements));
        } else if (ID == 2) {
            return new GuiApriCrafting(player.inventory, world, pos);
        }
        return null;
    }
}
