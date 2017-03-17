package apritree.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import apritree.inventory.GuiDriver;
import apritree.utils.AnvilRegistry;

import javax.annotation.Nonnull;

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
        reg.addRecipeClickArea(GuiDriver.class, 80, 44, 14, 14, "apritree.driver");
    }
}
