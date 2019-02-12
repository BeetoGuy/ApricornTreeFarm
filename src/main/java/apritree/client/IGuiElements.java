package apritree.client;

import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public interface IGuiElements {
    default int getFullGuiXSize() {
        return 0;
    }

    default int getFullGuiYSize() {
        return 0;
    }

    Map<Integer, GuiHelper.GuiElement> getGuiElements();

    void updateElement(int ID, Object obj);

    GuiHelper.GuiElement getGuiElement(int ID);

    ResourceLocation getGuiTexture();
}
