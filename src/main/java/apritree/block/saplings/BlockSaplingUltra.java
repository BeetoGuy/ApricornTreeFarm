package apritree.block.saplings;

import apritree.ApriRegistry;
import apritree.block.BlockBaseSapling;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSaplingUltra extends BlockBaseSapling {
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() >= 20);

    public BlockSaplingUltra() {
        super();
        this.setUnlocalizedName("apritree:ultra_apricorn_sapling");
        this.setLightLevel(0.625F);
    }

    @Override
    public IBlockState getLogState(IBlockState state) {
        return ApriRegistry.ultraLog.getDefaultState().withProperty(StateLibrary.APRICORNS6, state.getValue(APRICORNS));
    }

    @Override
    public IBlockState getLeafState(IBlockState state) {
        return ApriRegistry.apricornLeafUltra.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(StateLibrary.APRICORNS6, state.getValue(APRICORNS));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int i = 0; i < 2; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(APRICORNS).getMeta() - 20;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(StateLibrary.STAGE) == 1 ? 8 : 0) + state.getValue(APRICORNS).getMeta() - 20;
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
        return this.getDefaultState().withProperty(StateLibrary.STAGE, stage).withProperty(APRICORNS, EnumApricorns.byMeta(meta + 20));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, APRICORNS, StateLibrary.STAGE);
    }
}
