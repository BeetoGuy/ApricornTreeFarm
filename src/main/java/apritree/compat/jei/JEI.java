package apritree.compat.jei;

import apritree.ApriRegistry;
import apritree.compat.jei.category.AnvilCategory;
import apritree.compat.jei.category.BreedingCategory;
import apritree.compat.jei.wrapper.AnvilWrapper;
import apritree.compat.jei.wrapper.BreedingWrapper;
import apritree.recipe.RecipeApriWorkbench;
import apritree.utils.AdvAnvilCrafting;
import apritree.utils.AnvilRegistry;
import apritree.utils.ApriBreeding;
import apritree.utils.BreedingRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEI implements IModPlugin {
    public static IJeiHelpers HELPER;

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        final IJeiHelpers helpers = reg.getJeiHelpers();
        final IGuiHelper guiHelper = helpers.getGuiHelper();

        reg.addRecipeCategories(new AnvilCategory(guiHelper, "apritree.roller", "inv.roller"), new BreedingCategory(guiHelper, "apritree.breeding", "inv.breeding"));
    }

    @Override
    public void register(IModRegistry reg) {
        HELPER = reg.getJeiHelpers();
        reg.handleRecipes(RecipeApriWorkbench.class, r -> new ShapelessRecipeWrapper<>(HELPER, r), "minecraft.crafting");
        reg.handleRecipes(AdvAnvilCrafting.class, r -> new AnvilWrapper<>(HELPER, r), "apritree.roller");
        reg.handleRecipes(ApriBreeding.class, r -> new BreedingWrapper<>(HELPER, r), "apritree.breeding");
        reg.addRecipes(AnvilRegistry.getInstance().getCraftingRecipes(), "apritree.roller");
        reg.addRecipes(BreedingRegistry.getBreedList(), "apritree.breeding");
        reg.addRecipeCatalyst(new ItemStack(ApriRegistry.apriWorkbench), "minecraft.crafting");
        reg.addRecipeCatalyst(new ItemStack(ApriRegistry.machine), "apritree.roller");
        reg.addRecipeCatalyst(new ItemStack(ApriRegistry.apriWorkbench), "apritree.breeding");
    }
}
