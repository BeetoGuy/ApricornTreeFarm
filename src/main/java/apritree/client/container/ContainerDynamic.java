package apritree.client.container;

import apritree.client.IGuiElements;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerDynamic extends Container {
    private IGuiElements elements;
    private TileEntity tile;

    public ContainerDynamic(IGuiElements elements, TileEntity tile) {
        this.elements = elements;
        this.tile = tile;
    }

    public IGuiElements getElements() {
        return elements;
    }

    public TileEntity getTileEntity() {
        return tile;
    }
}
