package apritree.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class AnvilRecipeCategory extends BlankRecipeCategory<AnvilRecipeWrapper>
{
    private final IDrawable background;
    private final IDrawableAnimated pokeball;

    private final String localizedName = I18n.format("pixelplus.recipe.driver");

    public AnvilRecipeCategory(IGuiHelper helper)
    {
        background = helper.createDrawable(new ResourceLocation("apritree", "textures/gui/driver.png"), 37, 37, 103, 27);
        pokeball = helper.createAnimatedDrawable(helper.createDrawable(new ResourceLocation("apritree", "textures/gui/driver.png"), 176, 0, 14, 14), 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return "apritree.driver";
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft)
    {
        pokeball.draw(minecraft, 43, 7);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull AnvilRecipeWrapper wrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup stacks = layout.getItemStacks();

        stacks.init(0, true, 6, 5);
        stacks.init(1, false, 78, 5);
        List<ItemStack> inputs = ingredients.getInputs(ItemStack.class).get(0);
        stacks.set(0, inputs);

        stacks.set(1, wrapper.getRecipe().getOutput());
    }
}
