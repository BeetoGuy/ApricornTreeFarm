package apritree.block.leaves;

import apritree.ApriRegistry;
import apritree.block.BlockApriLeafBase;
import apritree.block.EnumApricorns;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockLeafUltra extends BlockApriLeafBase {
    public BlockLeafUltra() {
        super();
        this.setUnlocalizedName("apritree:ultra_leaves");
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
        this.setLightLevel(.625F);
    }

    @Override
    public EnumApricorns getApricornFromState(IBlockState state) {
        return EnumApricorns.ULTRA;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ApriRegistry.apricornSaplingUltra);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        return Arrays.asList(new ItemStack(this, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
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
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }
}
