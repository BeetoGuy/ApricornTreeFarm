package apritree.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GuiHelper {
    public static final byte SMALL_ITEM = 0;
    public static final byte LARGE_ITEM = 1;
    public static final byte ENERGY_BAR = 2;
    public static final byte PROGRESS_BAR = 3;

    public static class GuiElementText extends GuiElement {
        private String textElement;
        private String fullElement;

        public GuiElementText(int placementX, int placementY, int widthX, int widthY, int elementX, int elementY, int activeX, int activeY, String textElement) {
            super(placementX, placementY, widthX, widthY, elementX, elementY, activeX, activeY);
            this.textElement = textElement;
            this.fullElement = textElement;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void drawForegroundText(GuiContainerDynamic container, int x, int y, int guiX, int guiY) {

        }

        @Override
        public void placeInGui(GuiContainerDynamic container, FontRenderer renderer, int x, int y) {
            renderer.drawString(fullElement, x + getPlacementX(), y + getPlacementY(), 4210752);
        }

        @Override
        public void updateParameter(Object obj) {
            fullElement = textElement + " " + obj.toString();
        }
    }

    public static class GuiElementEnergy extends GuiElement {
        private int maxEnergy;
        private int storedEnergy;

        public GuiElementEnergy(int placementX, int placementY, int widthX, int widthY, int elementX, int elementY, int activeX, int activeY, EnergyStorage storage) {
            super(placementX, placementY, widthX, widthY, elementX, elementY, activeX, activeY);
            this.maxEnergy = storage.getMaxEnergyStored();
            this.storedEnergy = storage.getEnergyStored();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void drawForegroundText(GuiContainerDynamic container, int x, int y, int guiX, int guiY) {
            if (x >= guiX + getPlacementX() && x <= guiX + getPlacementX() + getWidthX() && y >= guiY + getPlacementY() && y <= guiY + getPlacementY() + getWidthY()) {
                container.drawHoveringText(I18n.format("apritree.energy") + ": " + storedEnergy + "/" + maxEnergy, x - guiX, y - guiY);
            }
        }

        @Override
        public void placeInGui(GuiContainerDynamic container, FontRenderer renderer, int x, int y) {
            container.drawTexturedModalRect(x + getPlacementX(), y + getPlacementY(), getElementX(), getElementY(), getWidthX(), getWidthY());
            if (storedEnergy > 0) {
                int scaledIcon = getScaledEnergy();
                container.drawTexturedModalRect(x + getPlacementX(), y + getPlacementY() + getWidthY() - scaledIcon, getActiveX(), getActiveY() + getWidthY() - scaledIcon, getWidthX(), getWidthY());
            }
        }

        private int getScaledEnergy() {
            return storedEnergy * getWidthY() / maxEnergy;
        }

        @Override
        public void updateParameter(Object obj) {
            this.storedEnergy = (int)obj;
        }
    }

    public static class GuiElementProgress extends GuiElement {
        private int progress;
        private int maxProgress;

        public GuiElementProgress(int placementX, int placementY, int widthX, int widthY, int elementX, int elementY, int activeX, int activeY, int progress, int maxProgress) {
            super(placementX, placementY, widthX, widthY, elementX, elementY, activeX, activeY);
            this.progress = progress;
            this.maxProgress = maxProgress;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void drawForegroundText(GuiContainerDynamic container, int x, int y, int guiX, int guiY) {
            if (x >= guiX + getPlacementX() && x <= guiX + getPlacementX() + getWidthX() && y >= guiY + getPlacementY() && y <= guiY + getPlacementY() + getWidthY()) {
                container.drawHoveringText(I18n.format("apritree.progress") + ": " + progress + "/" + maxProgress, x - guiX, y - guiY);
            }
        }

        @Override
        public void placeInGui(GuiContainerDynamic container, FontRenderer renderer, int x, int y) {
            container.drawTexturedModalRect(x + getPlacementX(), y + getPlacementX(), getElementX(), getElementY(), getWidthX(), getWidthY());
            if (progress > 0) {
                int scaledIcon = getScaledProgress();
                container.drawTexturedModalRect(x + getPlacementX(), y + getPlacementY() + getWidthY() - scaledIcon, getActiveX(), getActiveY() + getWidthY() - scaledIcon, getWidthX(), getWidthY());
            }
        }

        private int getScaledProgress() {
            return progress * getWidthY() / maxProgress;
        }

        @Override
        public void updateParameter(Object obj) {
            this.progress = (int)obj;
        }
    }

    public abstract static class GuiElement {
        private int placementX;
        private int placementY;
        private int widthX;
        private int widthY;
        private int elementX;
        private int elementY;
        private int activeX;
        private int activeY;

        public GuiElement(int placementX, int placementY, int widthX, int widthY, int elementX, int elementY, int activeX, int activeY) {
            this.placementX = placementX;
            this.placementY = placementY;
            this.widthX = widthX;
            this.widthY = widthY;
            this.elementX = elementX;
            this.elementY = elementY;
            this.activeX = activeX;
            this.activeY = activeY;
        }

        public int getPlacementX() {
            return placementX;
        }

        public int getPlacementY() {
            return placementY;
        }

        public int getWidthX() {
            return widthX;
        }

        public int getWidthY() {
            return widthY;
        }

        public int getElementX() {
            return elementX;
        }

        public int getElementY() {
            return elementY;
        }

        public int getActiveX() {
            return activeX;
        }

        public int getActiveY() {
            return activeY;
        }

        public abstract void placeInGui(GuiContainerDynamic container, FontRenderer renderer, int x, int y);

        @SideOnly(Side.CLIENT)
        public abstract void drawForegroundText(GuiContainerDynamic container, int x, int y, int guiX, int guiY);

        public abstract void updateParameter(Object obj);
    }
}
