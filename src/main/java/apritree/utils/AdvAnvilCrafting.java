package apritree.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class AdvAnvilCrafting
{
    private Object input;
    private ItemStack output;

    public AdvAnvilCrafting(Object input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    public List<ItemStack> getOreInput()
    {
        if(input instanceof String)
        {
            if(OreDictionary.doesOreNameExist((String)input))
                return OreDictionary.getOres((String)input);
        }
        ArrayList<ItemStack> item = new ArrayList<ItemStack>();
        item.add((ItemStack)input);
        return item;
    }

    public ItemStack getInput()
    {
        if(input instanceof ItemStack)
            return (ItemStack)input;
        return null;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
