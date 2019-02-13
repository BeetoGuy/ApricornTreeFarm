package apritree.compat.betterwithmods;

import apritree.ApriRegistry;
import betterwithmods.client.BWCreativeTabs;

public class BetterWithMods {
    public static void preInit() {
        ApriRegistry.mech_adapter = new ItemMechanicalAddon().setRegistryName("mechanical_augment").setUnlocalizedName("apritree:mechanical_augment").setCreativeTab(BWCreativeTabs.BWTAB);
        ApriRegistry.registerItem(ApriRegistry.mech_adapter);
    }
}
