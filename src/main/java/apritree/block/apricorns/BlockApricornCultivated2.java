package apritree.block.apricorns;

import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockApricornCultivated2 extends BlockApricornPlant {
    public BlockApricornCultivated2() {
        super();
        this.setUnlocalizedName("apritree:apricorn_four");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS4, EnumApricorns.BROWN).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS4);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 12);
        meta -= apricorn.getMeta() - 12;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS4, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS4).getMeta() - 12;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS4, STAGE);
    }
}
