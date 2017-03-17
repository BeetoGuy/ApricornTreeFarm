package apritree.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import apritree.utils.AdvAnvilCrafting;

import javax.annotation.Nonnull;

public class AnvilRecipeHandler implements IRecipeHandler<AdvAnvilCrafting>
{
    @Nonnull
    @Override
    public String getRecipeCategoryUid()
    {
        return "apritree.driver";
    }

    @Nonnull
    @Override
    public Class<AdvAnvilCrafting> getRecipeClass()
    {
        return AdvAnvilCrafting.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull AdvAnvilCrafting recipe)
    {
        return getRecipeCategoryUid();
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull AdvAnvilCrafting recipe)
    {
        return new AnvilRecipeWrapper(PixelJEIPlugin.helper, recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull AdvAnvilCrafting recipe)
    {
        return recipe.getOutput() != null && recipe.getOreInput() != null;
    }
}
