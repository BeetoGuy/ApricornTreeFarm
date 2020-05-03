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

public class BlockApricornPrimary extends BlockApricornPlant
{
    public BlockApricornPrimary()
    {
        super();
        this.setTranslationKey("apritree:apricorn_two");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS2, EnumApricorns.BLUE).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS2);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(state.getValue(StateLibrary.APRICORNS2) == EnumApricorns.PURPLE)
            apricornHarvest(world, pos, state, rand, 151);
        else
            apricornHarvest(world, pos, state, rand, 30);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 4);
        meta -= apricorn.getMeta() - 4;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS2, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS2).getMeta() - 4;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS2, STAGE);
    }
}
