package apritree.inventory;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import apritree.block.tile.TileEntityDriver;

import java.util.List;

public class GuiDriver extends GuiContainer
{
    private TileEntityDriver driver;
    private IInventory playerInv;

    public GuiDriver(IInventory playerInv, TileEntityDriver driver)
    {
        super(new ContainerDriver(playerInv, driver));
        this.playerInv = playerInv;
        this.ySize = 158;
        this.driver = driver;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String s = I18n.format(driver.getName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        int energy = driver.indicatesPower() ? 12 : 0;
        this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8 + energy, this.ySize - 96 + 2, 4210752);
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        if(driver.indicatesPower()) {
            if (x > xPos + 7 && x < xPos + 18 && y > yPos + 7 && y < yPos + 72)
            {
                List<String> text = Lists.newArrayList();
                text.add(I18n.format(driver.getEnergyType()) + ": " + driver.getEnergy() + "/" + driver.getMaxEnergy());
                this.drawHoveringText(text, x - xPos, y - yPos);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("apritree", "textures/gui/driver.png"));
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
        if(this.driver.indicatesPower())
            drawTexturedModalRect(xPos + 8, yPos + 8, 246, 0, 10, 64);

        if(this.driver.isOperating())
        {
            int scaledIcon = this.driver.getProgressTimeScaled(12);
            drawTexturedModalRect(xPos + 80, yPos + 44 + 12 - scaledIcon, 176, 12 - scaledIcon, 14, scaledIcon + 2);
        }
        if(this.driver.getEnergy() > 0)
        {
            int scaledIcon = this.driver.getScaledEnergy(64);
            drawTexturedModalRect(xPos + 8, yPos + 8 + 64 - scaledIcon, 236, 64 - scaledIcon, 10, scaledIcon);
        }
    }
}
