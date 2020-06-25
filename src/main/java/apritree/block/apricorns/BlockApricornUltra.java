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

public class BlockApricornUltra extends BlockApricornPlant {
    public BlockApricornUltra() {
        super();
        this.setTickRandomly(true);
        this.setTranslationKey("apritree:ultra_apricorn");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS6, EnumApricorns.ULTRA).withProperty(STAGE, 0));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(state.getValue(StateLibrary.APRICORNS6) == EnumApricorns.ULTRA || state.getValue(StateLibrary.APRICORNS6) == EnumApricorns.DREAM)
            apricornHarvest(world, pos, state, rand, 30);
        else
            apricornHarvest(world, pos, state, rand, 151);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state) {
        return state.getValue(StateLibrary.APRICORNS6);
    }

    @Override
    public ItemStack getApricorn(IBlockState state) {
        return EnumApricorns.getApricornFromEnum(getEnumApricorn(state));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 2;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 20);
        meta -= apricorn.getMeta() - 20;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS6, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(StateLibrary.APRICORNS6).getMeta() - 20;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS6, STAGE);
    }
}
