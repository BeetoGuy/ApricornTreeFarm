package apritree.compat.jei.wrapper;

import apritree.utils.AdvAnvilCrafting;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class AnvilWrapper<T extends AdvAnvilCrafting> implements IRecipeWrapper {
    protected final T recipe;
    private final IJeiHelpers helpers;

    public AnvilWrapper(IJeiHelpers helpers, @Nonnull T recipe) {
        this.recipe = recipe;
        this.helpers = helpers;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ing) {
        ing.setInput(ItemStack.class, recipe.getOreInput());
        ing.setOutput(ItemStack.class, recipe.getOutput());
    }
}
