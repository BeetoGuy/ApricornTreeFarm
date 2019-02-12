package apritree.compat.jei.category;

import apritree.compat.jei.wrapper.BreedingWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BreedingCategory implements IRecipeCategory<BreedingWrapper> {
    @Nonnull
    private final IDrawable background;

    @Nonnull
    private final String localizedName, uid;
    private static final ResourceLocation guiTexture = new ResourceLocation("apritree", "textures/gui/jei/breeding.png");

    public BreedingCategory(@Nonnull IGuiHelper helper, @Nonnull String uid, @Nonnull String unlocalizedName) {
        this.background = helper.createDrawable(guiTexture, 0, 0, 76, 20);
        this.localizedName = I18n.format(unlocalizedName);
        this.uid = uid;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull BreedingWrapper wrapper, @Nonnull IIngredients ing) {
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 2, 1);
        stacks.init(1, true, 29, 1);
        stacks.init(2, false, 57, 1);
        stacks.set(ing);
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public String getUid() {
        return this.uid;
    }

    @Override
    public String getModName() {
        return "apritree";
    }
}
