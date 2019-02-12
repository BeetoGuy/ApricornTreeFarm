package apritree.compat.jei.wrapper;

import apritree.utils.ApriBreeding;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BreedingWrapper<T extends ApriBreeding> implements IRecipeWrapper {
    protected final T recipe;
    private final IJeiHelpers helpers;

    public BreedingWrapper(IJeiHelpers helpers, @Nonnull T recipe) {
        this.recipe = recipe;
        this.helpers = helpers;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ing) {
        ing.setInputLists(ItemStack.class, recipe.getParentsJEI());
        ing.setOutput(ItemStack.class, recipe.getChildJEI());
    }
}
