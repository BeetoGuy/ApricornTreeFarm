package pixelplus.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import pixelplus.inventory.GuiDriver;
import pixelplus.utils.AdvAnvilCrafting;
import pixelplus.utils.AnvilRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class PixelJEIPlugin extends BlankModPlugin
{
    public static IJeiHelpers helper;

    @Override
    public void register(@Nonnull IModRegistry reg)
    {
        helper = reg.getJeiHelpers();

        IJeiHelpers helper = reg.getJeiHelpers();
        IGuiHelper guiHelper = helper.getGuiHelper();

        reg.addRecipeCategories(new AnvilRecipeCategory(guiHelper));
        reg.addRecipeHandlers(new AnvilRecipeHandler());
        reg.addRecipes(AnvilRegistry.getInstance().getCraftingRecipes());
        reg.addRecipeClickArea(GuiDriver.class, 80, 44, 14, 14, "pixelplus.driver");
    }
}
