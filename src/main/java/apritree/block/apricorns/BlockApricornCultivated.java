package apritree.block.apricorns;

import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockApricornCultivated extends BlockApricornPlant {
    public BlockApricornCultivated() {
        super();
        this.setTranslationKey("apritree:apricorn_three");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS3, EnumApricorns.ORANGE).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS3);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 8);
        meta -= apricorn.getMeta() - 8;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS3, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS3).getMeta() - 8;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS3, STAGE);
    }
}
