package apritree.block.logs;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockLogUltra extends BlockLog {
    public BlockLogUltra() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
        this.setUnlocalizedName("apritree:log_ultra");
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta == 0 ? this.getDefaultState() : meta == 4 ? this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.X) : meta == 8 ? this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Z) : this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.NONE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        switch(state.getValue(LOG_AXIS)) {
            case X: i += 4; break;
            case Z: i += 8; break;
            case NONE: i += 12; break;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this, 1, 0);
    }
}
