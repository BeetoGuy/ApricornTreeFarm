package apritree.block.leaves;

import apritree.ApriRegistry;
import apritree.block.BlockApriLeafBase;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockApricornLeafFour extends BlockApriLeafBase {
    public BlockApricornLeafFour() {
        super();
        this.setUnlocalizedName("apritree:apricorn_leaf_fourth");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.APRICORNS4, EnumApricorns.BROWN).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
    }

    @Override
    public EnumApricorns getApricornFromState(IBlockState state) {
        return state.getValue(StateLibrary.APRICORNS4);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS4).getMeta() - 8;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ApriRegistry.apricornSaplingCultivated);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, state.getBlock().getMetaFromState(state) & 3);
    }

    public EnumApricorns getApricornType(int meta)
    {
        return EnumApricorns.byMeta((meta % 4) + 12);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(StateLibrary.APRICORNS4).getMeta() - 12));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(StateLibrary.APRICORNS4, getApricornType(meta)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(StateLibrary.APRICORNS4).getMeta() - 12;

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
        return new BlockStateContainer(this, StateLibrary.APRICORNS4, CHECK_DECAY, DECAYABLE);
    }
}
