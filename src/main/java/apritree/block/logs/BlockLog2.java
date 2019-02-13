package apritree.block.logs;

import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLog2 extends BlockLog {
    public BlockLog2() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS2, EnumApricorns.BLUE).withProperty(LOG_AXIS, EnumAxis.Y));
        this.setUnlocalizedName("apritree:log_two");
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS2).getMeta() - 4;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(int i = 0; i < 4; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState().withProperty(StateLibrary.APRICORNS2, EnumApricorns.byMeta((meta % 4) + 4));
        switch (meta & 12) {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(StateLibrary.APRICORNS2).getMeta() - 4;
        switch(state.getValue(LOG_AXIS)) {
            case X: i |= 4; break;
            case Z: i |= 8; break;
            case NONE: i |= 12; break;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, StateLibrary.APRICORNS2, LOG_AXIS);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this, 1, state.getValue(StateLibrary.APRICORNS2).getMeta() - 4);
    }
}
