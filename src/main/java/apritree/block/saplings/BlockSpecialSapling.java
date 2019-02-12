package apritree.block.saplings;

import apritree.ApriRegistry;
import apritree.block.BlockBaseSapling;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpecialSapling extends BlockBaseSapling {
    public BlockSpecialSapling() {
        super();
        this.setUnlocalizedName("apritree:special_apricorn_sapling");
        this.setDefaultState(this.blockState.getBaseState().withProperty(StateLibrary.STAGE, 0).withProperty(StateLibrary.APRICORNS5, EnumApricorns.GILDED));
    }

    @Override
    public IBlockState getLogState(IBlockState state) {
        return ApriRegistry.logFive.getDefaultState().withProperty(StateLibrary.APRICORNS5, state.getValue(StateLibrary.APRICORNS5));
    }

    @Override
    public IBlockState getLeafState(IBlockState state) {
        return ApriRegistry.apricornLeafFive.getDefaultState().withProperty(StateLibrary.APRICORNS5, state.getValue(StateLibrary.APRICORNS5));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(StateLibrary.APRICORNS5).getMeta() - 16;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(int i = 0; i < 4; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(StateLibrary.STAGE) == 1 ? 8 : 0) + state.getValue(StateLibrary.APRICORNS5).getMeta() - 16 ;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int stage = 0;
        if(meta > 7)
        {
            stage = 1;
            meta -= 8;
        }
        return this.getDefaultState().withProperty(StateLibrary.STAGE, stage).withProperty(StateLibrary.APRICORNS5, EnumApricorns.byMeta(meta + 16));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, StateLibrary.APRICORNS5, StateLibrary.STAGE);
    }
}
