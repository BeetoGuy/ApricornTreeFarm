package apritree.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import apritree.utils.AdvAnvilCrafting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AnvilRecipeWrapper extends BlankRecipeWrapper
{
    @Nonnull
    private final AdvAnvilCrafting recipe;
    private final IJeiHelpers helpers;

    public AnvilRecipeWrapper(IJeiHelpers helpers, @Nonnull AdvAnvilCrafting recipe)
    {
        this.helpers = helpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = helpers.getStackHelper();
        List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(recipe.getOreInput());
        ingredients.setInputLists(ItemStack.class, inputs);
        List<ItemStack> outputs = new ArrayList<ItemStack>();
        outputs.add(recipe.getOutput().copy());
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    public AdvAnvilCrafting getRecipe()
    {
        return recipe;
    }
}
