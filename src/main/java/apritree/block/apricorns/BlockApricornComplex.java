package apritree.block.apricorns;

import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockApricornComplex extends BlockApricornPlant
{
    public BlockApricornComplex()
    {
        super();
        this.setTranslationKey("apritree:apricorn_one");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS1, EnumApricorns.BLACK).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS1);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta(meta % 4);
        meta -= apricorn.getMeta();
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS1, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS1).getMeta();
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS1, STAGE);
    }
}
