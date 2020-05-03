package apritree.item;

import com.pixelmonmod.pixelmon.config.PixelmonCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMasterballParts extends Item
{
    public ItemMasterballParts()
    {
        super();
        this.setTranslationKey("apritree:masterball_parts");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(PixelmonCreativeTabs.pokeball);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (tab == this.getCreativeTab())
        for(int i = 0; i < 8; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey() + "." + stack.getItemDamage();
    }
}
