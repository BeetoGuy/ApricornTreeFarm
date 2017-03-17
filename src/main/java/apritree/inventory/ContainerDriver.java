package apritree.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import apritree.block.tile.TileEntityDriver;

import java.util.Iterator;

public class ContainerDriver extends Container
{
    private TileEntityDriver driver;
    private int lastDriverCounter;
    private int lastEnergyCounter;

    public ContainerDriver(IInventory player, TileEntityDriver driver)
    {
        this.driver = driver;
        addSlotToContainer(new SlotItemHandler(driver.inventory, 0, 44, 43));
        addSlotToContainer(new SlotItemHandlerExtractOnly(driver.inventory, 1, 116, 43));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 76 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(player, i, 8 + i * 18, 134));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if(index < 2)
            {
                if(!mergeItemStack(stack1, 2, this.inventorySlots.size(), true))
                    return null;
            }
            else if(!mergeItemStack(stack1, 0, 1, false))
                return null;
            if(stack1.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();
        }
        return stack;
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendProgressBarUpdate(this, 0, this.driver.progressTime);
        listener.sendProgressBarUpdate(this, 1, this.driver.getEnergy());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        Iterator<IContainerListener> it = this.listeners.iterator();
        while(it.hasNext())
        {
            IContainerListener craft = it.next();
            if(this.lastDriverCounter != this.driver.progressTime)
            {
                craft.sendProgressBarUpdate(this, 0, this.driver.progressTime);
            }
            if(this.lastEnergyCounter != this.driver.getEnergy())
                craft.sendProgressBarUpdate(this, 1, this.driver.getEnergy());
        }
        this.lastDriverCounter = this.driver.progressTime;
        this.lastEnergyCounter = this.driver.getEnergy();
    }

    @Override
    public void updateProgressBar(int index, int value)
    {
        if(index == 0)
            this.driver.progressTime = value;
        if(index == 1 && driver.indicatesPower())
            this.driver.setAvailableEnergy(value);
    }

    public class SlotItemHandlerExtractOnly extends SlotItemHandler {
        public SlotItemHandlerExtractOnly(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }
}
