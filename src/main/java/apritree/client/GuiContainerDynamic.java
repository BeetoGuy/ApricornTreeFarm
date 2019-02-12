package apritree.client;

import apritree.client.container.ContainerDynamic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;

public class GuiContainerDynamic extends GuiContainer {
    public IGuiElements guiElements;

    public GuiContainerDynamic(ContainerDynamic container) {
        super(container);
        this.guiElements = container.getElements();
        if (guiElements.getFullGuiXSize() > 0) {
            this.xSize = guiElements.getFullGuiXSize();
        }
        if (guiElements.getFullGuiYSize() > 0) {
            this.ySize = guiElements.getFullGuiYSize();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        for (int i = 0; i < guiElements.getGuiElements().size(); i++) {
            GuiHelper.GuiElement element = guiElements.getGuiElement(i);
            element.drawForegroundText(this, mouseX, mouseY, xPos, yPos);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(guiElements.getGuiTexture());
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
        drawTextureElement(xPos, yPos);
    }

    private void drawTextureElement(int x, int y) {
        for (int i = 0; i < guiElements.getGuiElements().size(); i++) {
            GuiHelper.GuiElement element = guiElements.getGuiElement(i);
            element.placeInGui(this, this.fontRenderer, x, y);
        }
    }
}
