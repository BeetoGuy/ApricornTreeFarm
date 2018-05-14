package apritree.utils;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AnvilRegistry
{
    private static List<AdvAnvilCrafting> crafting = new ArrayList<AdvAnvilCrafting>();

    private static AnvilRegistry instance = new AnvilRegistry();

    public static AnvilRegistry getInstance()
    {
        return instance;
    }

    public void addRecipe(String input, ItemStack output)
    {
        crafting.add(new AdvAnvilCrafting(input, output));
    }

    public void addRecipe(ItemStack input, ItemStack output)
    {
        crafting.add(new AdvAnvilCrafting(input, output));
    }

    public boolean inputExists(ItemStack input)
    {
        if (input.isEmpty()) return false;
        for(AdvAnvilCrafting craft : crafting)
        {
            if(!craft.getOreInput().isEmpty())
            {
                for(ItemStack stack : craft.getOreInput())
                    if(matches(stack, input))
                        return true;
            }
            else if(matches(craft.getInput(), input))
                return true;
        }
        return false;
    }

    public ItemStack getOutput(ItemStack input)
    {
        for(AdvAnvilCrafting craft : crafting)
        {
            if(!craft.getOreInput().isEmpty())
            {
                for(ItemStack stack : craft.getOreInput())
                    if(matches(stack, input))
                        return craft.getOutput();
            }
            else if(matches(craft.getInput(), input))
                return craft.getOutput();
        }
        return ItemStack.EMPTY;
    }

    public List<AdvAnvilCrafting> getCraftingRecipes()
    {
        return crafting;
    }

    private boolean matches(ItemStack first, ItemStack second)
    {
        if(first.isEmpty() || second.isEmpty())
            return false;
        return first.getItem() == second.getItem() && first.getItemDamage() == second.getItemDamage();
    }
}
