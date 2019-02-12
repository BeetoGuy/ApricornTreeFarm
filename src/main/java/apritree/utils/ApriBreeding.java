package apritree.utils;

import apritree.block.EnumApricorns;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ApriBreeding {
    private EnumApricorns parent1;
    private EnumApricorns parent2;
    private EnumApricorns child;
    private int breedChance;

    public ApriBreeding(EnumApricorns parent1, EnumApricorns parent2, EnumApricorns child, int breedChance) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.child = child;
        this.breedChance = breedChance;
    }

    public EnumApricorns getFirstParent() {
        return parent1;
    }

    public EnumApricorns getSecondParent() {
        return parent2;
    }

    public boolean parentsMatch(EnumApricorns parent1, EnumApricorns parent2) {
        return (parent1 == this.parent1 && parent2 == this.parent2) || (parent1 == this.parent2 && parent2 == this.parent1);
    }

    public EnumApricorns getChild() {
        return this.child;
    }

    public int getBreedChance() {
        return this.breedChance;
    }

    public List<List<ItemStack>> getParentsJEI() {
        List<List<ItemStack>> list = Lists.newArrayList();
        List<ItemStack> base = Lists.newArrayList();
        base.add(EnumApricorns.getApricornFromEnum(parent1));
        list.add(base);
        base = Lists.newArrayList();
        base.add(EnumApricorns.getApricornFromEnum(parent2));
        list.add(base);
        return list;
    }

    public ItemStack getChildJEI() {
        return EnumApricorns.getApricornFromEnum(child);
    }
}
