package apritree.client;

import apritree.block.tile.TileEntityRoller;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class GuiElementsRoller implements IGuiElements {
    private Map<Integer, GuiHelper.GuiElement> elements = Maps.newHashMap();

    public GuiElementsRoller(TileEntityRoller roller) {
        elements.put(0, new GuiHelper.GuiElementEnergy(8, 8, 10, 64, 246, 0, 236, 0, roller.storage));
        elements.put(1, new GuiHelper.GuiElementProgress(80, 44, 14, 14, 220, 220, 176, 0, roller.progressTime, 200));
    }

    @Override
    public void updateElement(int ID, Object obj) {
        getGuiElement(ID).updateParameter(obj);
    }

    @Override
    public Map<Integer, GuiHelper.GuiElement> getGuiElements() {
        return elements;
    }

    @Override
    public int getFullGuiYSize() {
        return 158;
    }

    @Override
    public GuiHelper.GuiElement getGuiElement(int ID) {
        return elements.get(ID);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return new ResourceLocation("apritree", "textures/gui/driver.png");
    }
}
