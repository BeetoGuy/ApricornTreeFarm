package apritree.utils;

import apritree.block.EnumApricorns;
import com.google.common.collect.Lists;

import java.util.List;

public class BreedingRegistry {
    private static List<ApriBreeding> BREEDLIST = Lists.newArrayList();

    public static void addApriBreeding(EnumApricorns parent1, EnumApricorns parent2, EnumApricorns child, int chance) {
        BREEDLIST.add(new ApriBreeding(parent1, parent2, child, chance));
    }

    public static ApriBreeding getBreedingMatch(EnumApricorns parent1, EnumApricorns parent2) {
        for (ApriBreeding breed : BREEDLIST) {
            if (breed.parentsMatch(parent1, parent2)) {
                return breed;
            }
        }
        return null;
    }

    public static List<ApriBreeding> getBreedList() {
        return BREEDLIST;
    }
}
