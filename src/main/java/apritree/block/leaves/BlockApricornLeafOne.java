package apritree.block.leaves;

import apritree.block.BlockApriLeafBase;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import apritree.ApriRegistry;

import java.util.List;
import java.util.Random;

public class BlockApricornLeafOne extends BlockApriLeafBase
{
    public BlockApricornLeafOne()
    {
        super();
        this.setUnlocalizedName("apritree:apricorn_leaf_first");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS1, EnumApricorns.BLACK).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
    }

    @Override
    public EnumApricorns getApricornFromState(IBlockState state) {
        return state.getValue(StateLibrary.APRICORNS1);
    }

    public EnumApricorns getApricornType(int meta)
    {
        return EnumApricorns.byMeta(meta % 4);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ApriRegistry.apricornSapling);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS1).getMeta();
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(StateLibrary.APRICORNS1).getMeta()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS1, getApricornType(meta % 4)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(StateLibrary.APRICORNS1).getMeta();

        if (!state.getValue(DECAYABLE))
        {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS1, CHECK_DECAY, DECAYABLE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
