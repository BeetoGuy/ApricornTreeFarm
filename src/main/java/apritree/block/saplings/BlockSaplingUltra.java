package apritree.block.saplings;

import apritree.ApriRegistry;
import apritree.block.BlockBaseSapling;
import apritree.block.StateLibrary;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockSaplingUltra extends BlockBaseSapling {
    public BlockSaplingUltra() {
        super();
        this.setUnlocalizedName("apritree:ultra_apricorn_sapling");
        this.setLightLevel(0.625F);
    }

    @Override
    public IBlockState getLogState(IBlockState state) {
        return ApriRegistry.ultraLog.getDefaultState();
    }

    @Override
    public IBlockState getLeafState(IBlockState state) {
        return ApriRegistry.apricornLeafUltra.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(StateLibrary.STAGE) == 1 ? 8 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int stage = meta == 8 ? 1 : 0;
        return this.getDefaultState().withProperty(StateLibrary.STAGE, stage);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.STAGE);
    }
}
