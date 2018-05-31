package apritree.compat;

import apritree.ApriRegistry;
import apritree.utils.AdvAnvilCrafting;
import apritree.utils.AnvilRegistry;
import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.api.tool.ExternalHeaterHandler;
import blusunrize.immersiveengineering.common.IEContent;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityMechanicalAnvil;
import net.minecraft.item.ItemStack;

public class ImmersiveEngineering {
    public static void addPressRecipes() {
        BlueprintCraftingRecipe.addRecipe("molds", new ItemStack(ApriRegistry.ball_mold), "plateSteel", "plateSteel", "plateSteel", "plateSteel", "plateSteel", new ItemStack(IEContent.itemTool, 1, 1));
        for (AdvAnvilCrafting craft : AnvilRegistry.getInstance().getCraftingRecipes()) {
            MetalPressRecipe.addRecipe(craft.getOutput(), craft.getInput(), new ItemStack(ApriRegistry.ball_mold), 3200);
        }

        ExternalHeaterHandler.registerHeatableAdapter(TileEntityMechanicalAnvil.class, new AnvilHeatingWrapper());
    }
}
