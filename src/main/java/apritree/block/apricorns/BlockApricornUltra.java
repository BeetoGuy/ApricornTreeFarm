package apritree.block.apricorns;

import apritree.block.BlockApricornPlant;
import apritree.block.EnumApricorns;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockApricornUltra extends BlockApricornPlant {
    public BlockApricornUltra() {
        super();
        this.setTickRandomly(true);
        this.setUnlocalizedName("apritree:ultra_apricorn");
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state) {
        return EnumApricorns.ULTRA;
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
        return this.getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE);
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STAGE);
    }
}
