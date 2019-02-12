package apritree.client;

import apritree.block.tile.TileEntityCharger;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.util.Map;

public class GuiElementsCharger implements IGuiElements {
    private Map<Integer, GuiHelper.GuiElement> elements = Maps.newHashMap();

    public GuiElementsCharger(TileEntityCharger charger) {
        elements.put(0, new GuiHelper.GuiElementText(82, 47, 0, 0, 0, 0, 0, 0, I18n.translateToLocal("inv.charger.level")));
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
        return new ResourceLocation("apritree", "textures/gui/charger.png");
    }
}
