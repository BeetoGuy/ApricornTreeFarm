package apritree.client.container;

import apritree.block.tile.TileEntityRoller;
import apritree.client.IGuiElements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRoller extends ContainerDynamic {
    private int lastRollerCounter;
    private int lastEnergyCounter;

    public ContainerRoller(IInventory player, TileEntityRoller roller, IGuiElements elements) {
        super(elements, roller);
        addSlotToContainer(new SlotItemHandler(roller.inventory, 0, 44, 43));
        addSlotToContainer(new SlotItemHandlerExtractOnly(roller.inventory, 1, 116, 43));
        addSlotToContainer(new SlotItemHandler(roller.inventory, 2, 79, 8));

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 76 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(player, i, 8 + i * 18, 134));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if (index < 3) {
                if (!mergeItemStack(stack1, 3, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(stack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            if (stack1.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (stack1.getCount() == stack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(player, stack1);
        }
        return stack;
    }

    @Override
    public void addListener(IContainerListener listener) {
        TileEntityRoller roller = (TileEntityRoller)getTileEntity();
        super.addListener(listener);
        listener.sendWindowProperty(this, 1, roller.progressTime);
        listener.sendWindowProperty(this, 0, roller.storage.getEnergyStored());
    }

    @Override
    public void detectAndSendChanges() {
        TileEntityRoller roller = (TileEntityRoller)getTileEntity();
        super.detectAndSendChanges();
        int rollerCounter = roller.progressTime;
        int energy = roller.getEnergyRead();
        for (IContainerListener listen : this.listeners) {
            if (lastRollerCounter != rollerCounter) {
                listen.sendWindowProperty(this, 1, rollerCounter);
                getElements().updateElement(1, rollerCounter);
            }
            if (lastEnergyCounter != energy) {
                listen.sendWindowProperty(this, 0, energy);
                getElements().updateElement(0, energy);
            }
        }
        lastRollerCounter = rollerCounter;
        lastEnergyCounter = energy;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            lastEnergyCounter = data;
            getElements().updateElement(0, data);
        } else if (id == 1) {
            lastRollerCounter = data;
            getElements().updateElement(1, data);
        }
    }

    public class SlotItemHandlerExtractOnly extends SlotItemHandler {
        public SlotItemHandlerExtractOnly(IItemHandler handler, int index, int xPos, int yPos) {
            super(handler, index, xPos, yPos);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }
}
