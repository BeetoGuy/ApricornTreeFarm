package apritree.block.apricorns;

import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockApricornSpecial extends BlockApricornPlant {
    public BlockApricornSpecial() {
        super();
        this.setTranslationKey("apritree:apricorn_five");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS5, EnumApricorns.GILDED).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS5);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 16);
        meta -= apricorn.getMeta() - 16;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS5, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS5).getMeta() - 16;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS5, STAGE);
    }
}
