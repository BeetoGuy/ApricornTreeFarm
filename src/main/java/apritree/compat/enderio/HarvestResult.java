package apritree.compat.enderio;

import crazypants.enderio.api.farm.IHarvestResult;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class HarvestResult implements IHarvestResult {
    private NonNullList<EntityItem> drops;
    private NonNullList<BlockPos> harvestedBlocks;

    public HarvestResult() {
        drops = NonNullList.create();
        harvestedBlocks = NonNullList.create();
    }

    @Override
    public NonNullList<EntityItem> getDrops() {
        return drops;
    }

    @Override
    public NonNullList<BlockPos> getHarvestedBlocks() {
        return harvestedBlocks;
    }
}
