package apritree.client.container;

import apritree.block.tile.TileEntityCharger;
import apritree.client.IGuiElements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCharger extends ContainerDynamic {
    private int lastChargeCounter;

    public ContainerCharger(IInventory player, TileEntityCharger charger, IGuiElements elements) {
        super(elements, charger);

        addSlotToContainer(new SlotItemHandler(charger.inventory, 0, 44, 43));
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
            if (index < 1) {
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
        TileEntityCharger charger = (TileEntityCharger)getTileEntity();
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, charger.getChargeUnits());
    }

    @Override
    public void detectAndSendChanges() {
        TileEntityCharger charger = (TileEntityCharger)getTileEntity();
        super.detectAndSendChanges();
        int chargeUnits = charger.getChargeUnits();
        for (IContainerListener listen : this.listeners) {
            if (lastChargeCounter != chargeUnits) {
                listen.sendWindowProperty(this, 0, chargeUnits);
                getElements().updateElement(0, chargeUnits);
                lastChargeCounter = chargeUnits;
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        lastChargeCounter = data;
        getElements().updateElement(0, data);
    }
}
