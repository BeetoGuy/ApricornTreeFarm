package apritree.compat.betterwithmods;

import apritree.ApriRegistry;

public class BetterWithMods {
    public static void preInit() {
        ApriRegistry.mech_adapter = new ItemMechanicalAddon().setRegistryName("mechanical_augment");
        ApriRegistry.registerItem(ApriRegistry.mech_adapter);
    }
}
