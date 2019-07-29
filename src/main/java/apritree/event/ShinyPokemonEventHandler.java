package apritree.event;

import apritree.ApriTree;
import apritree.config.ApriConfig;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.config.PixelmonConfig;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import com.pixelmonmod.pixelmon.worldGeneration.dimension.ultraspace.UltraSpace;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Objects;

public class ShinyPokemonEventHandler {
    private static final StatsType[] STATS_TYPES = {StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};

    @SubscribeEvent
    public void onPixelmonCatch(CaptureEvent.SuccessfulCapture evt) {
        if (!ApriConfig.shinyGSBall && !ApriConfig.perfectCherishBall) return;
        if (evt.pokeball.getType() != null) {
            EntityPixelmon pixelmon = evt.getPokemon();
            boolean changed = false;
            if (ApriConfig.shinyGSBall && evt.pokeball.getType() == EnumPokeballs.GSBall) {
                pixelmon.getPokemonData().setShiny(true);
                changed = true;
            }
            else if (ApriConfig.perfectCherishBall && evt.pokeball.getType() == EnumPokeballs.CherishBall) {
                makeIVsPerfect(pixelmon);
                changed = true;
            }
            if (changed)
                evt.setPokemon(pixelmon);
        }
    }

    private void makeIVsPerfect(EntityPixelmon pixelmon) {
        for (StatsType type : STATS_TYPES) {
            pixelmon.getPokemonData().getStats().ivs.set(type, IVStore.MAX_IVS);
        }
    }

    @SubscribeEvent
    public void onPixelmonBred(BreedEvent.MakeEgg evt) {
        if (!ApriConfig.gen2ShinyBreed && !ApriConfig.dittoLegend3IV) return;
        if (evt.parent1 != null && evt.parent2 != null) {
            if (ApriConfig.gen2ShinyBreed && (evt.parent1.isShiny() || evt.parent2.isShiny())) {
                int chance = evt.ranch.getWorld().provider.getDimension() == UltraSpace.DIM_ID ? (int)(64 * PixelmonConfig.ultraSpaceShinyModifier) : 64;
                if (!Objects.equals(evt.parent1.getOriginalTrainerUUID(), evt.parent2.getOriginalTrainerUUID()))
                    chance /= 2;
                if (Pixelmon.storageManager.getParty(evt.owner).getShinyCharm().isActive()) {
                    chance /= 3;
                }
                if (ApriTree.INSTANCE.rand.nextInt(chance) == 0) {
                    evt.getEgg().setShiny(true);
                }
            }
            if (ApriConfig.dittoLegend3IV && evt.parent1.isPokemon(EnumSpecies.Ditto) && evt.parent2.isPokemon(EnumSpecies.Ditto)) {
                if (EnumSpecies.legendaries.contains(evt.getEgg().getSpecies().name) || EnumSpecies.ultrabeasts.contains(evt.getEgg().getSpecies().name)) {
                    List<StatsType> stats = Lists.newArrayList(STATS_TYPES);
                    for (int i = 0; i < 3; i++) {
                        int statRand = ApriTree.INSTANCE.rand.nextInt(stats.size());
                        stats.remove(statRand);
                    }
                    for (StatsType stat : stats) {
                        evt.getEgg().getStats().ivs.set(stat, IVStore.MAX_IVS);
                    }
                }
            }
        }
    }
}
