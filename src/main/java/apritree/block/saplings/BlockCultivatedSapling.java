package apritree.block.saplings;

import apritree.ApriRegistry;
import apritree.block.BlockBaseSapling;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCultivatedSapling extends BlockBaseSapling {
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() >= 8 && a.getMeta() < 16);

    public BlockCultivatedSapling() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.ORANGE).withProperty(StateLibrary.STAGE, 0));
        this.setTranslationKey("apritree:cultivated_apricorn_sapling");
    }

    @Override
    public IBlockState getLogState(IBlockState state) {
        EnumApricorns apricorn = state.getValue(APRICORNS);
        switch(apricorn) {
            case BROWN:
            case LIME:
            case LIGHTBLUE:
            case MAGENTA: return ApriRegistry.logFour.getDefaultState().withProperty(StateLibrary.APRICORNS4, apricorn);
            default: return ApriRegistry.logThree.getDefaultState().withProperty(StateLibrary.APRICORNS3, apricorn);
        }
    }

    @Override
    public IBlockState getLeafState(IBlockState state) {
        EnumApricorns apricorn = state.getValue(APRICORNS);
        switch(apricorn) {
            case BROWN:
            case LIME:
            case LIGHTBLUE:
            case MAGENTA: return ApriRegistry.apricornLeafFour.getDefaultState().withProperty(StateLibrary.APRICORNS4, apricorn).withProperty(BlockLeaves.CHECK_DECAY, false);
            default: return ApriRegistry.apricornLeafThree.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(StateLibrary.APRICORNS3, apricorn);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(APRICORNS).getMeta() - 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int i = 0; i < 8; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(StateLibrary.STAGE) == 1 ? 8 : 0) + state.getValue(APRICORNS).getMeta() - 8 ;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int stage = 0;
        if(meta > 7)
        {
            stage = 1;
            meta -= 8;
        }
        return this.getDefaultState().withProperty(StateLibrary.STAGE, stage).withProperty(APRICORNS, EnumApricorns.byMeta(meta + 8));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, APRICORNS, StateLibrary.STAGE);
    }
}
