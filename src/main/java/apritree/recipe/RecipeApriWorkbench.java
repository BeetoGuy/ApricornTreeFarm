package apritree.recipe;

import apritree.ApriRegistry;
import apritree.client.container.InventoryApricornCrafting;
import apritree.item.ItemApricornTools;
import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeApriWorkbench extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private ResourceLocation group;
    protected ItemStack result;
    protected Ingredient input;

    public RecipeApriWorkbench(ResourceLocation group, ItemStack result, Ingredient input) {
        this.group = group;
        this.result = result;
        this.input = input;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean compatibleWorkbench = inv instanceof InventoryApricornCrafting, hasTools = false, hasInput = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            boolean inRecipe = false;
            ItemStack slot = inv.getStackInSlot(i);

            if (!slot.isEmpty()) {
                if (input.apply(slot)) {
                    if (!hasInput) {
                        hasInput = true;
                        inRecipe = true;
                    } else {
                        return false;
                    }
                } else if (slot.getItem() instanceof ItemApricornTools) {
                    if (!hasTools) {
                        hasTools = true;
                        inRecipe = true;
                    } else if (!compatibleWorkbench) {
                        return false;
                    }
                }
                if (!inRecipe)
                    return false;
            }
        }
        return (compatibleWorkbench || hasTools) && hasInput;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result.copy();
    }

    @Override
    public String getGroup() {
        if (group != null)
            return group.toString();
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(input);
        ingredients.add(Ingredient.fromItem(ApriRegistry.apricornTool));
        return ingredients;
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            String group = JsonUtils.getString(json, "group", "");
            Ingredient apricorn = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "apricorn"), context);
            ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
            return new RecipeApriWorkbench(new ResourceLocation(group), result, apricorn);
        }
    }
}
