package apritree.block.saplings;

import apritree.block.BlockBaseSapling;
import apritree.block.EnumApricorns;
import apritree.block.StateLibrary;
import apritree.config.ApriConfig;
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
import apritree.ApriRegistry;

public class BlockApricornSapling extends BlockBaseSapling
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() >= 0 && a.getMeta() < 8);

    public BlockApricornSapling()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLACK).withProperty(StateLibrary.STAGE, 0));
        this.setUnlocalizedName("apritree:apricorn_sapling");
    }

    @Override
    public IBlockState getLogState(IBlockState state)
    {
        EnumApricorns apricorn = state.getValue(APRICORNS);
        switch(apricorn)
        {
            case BLACK:
            case WHITE:
            case PINK:
            case GREEN: return ApriRegistry.logOne.getDefaultState().withProperty(StateLibrary.APRICORNS1, apricorn);
            default: return ApriRegistry.logTwo.getDefaultState().withProperty(StateLibrary.APRICORNS2, apricorn);
        }
    }

    @Override
    public IBlockState getLeafState(IBlockState state)
    {
        EnumApricorns apricorn = state.getValue(APRICORNS);
        switch(apricorn)
        {
            case BLACK:
            case WHITE:
            case PINK:
            case GREEN: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(StateLibrary.APRICORNS1, apricorn).withProperty(BlockLeaves.CHECK_DECAY, false);
            default: return ApriRegistry.apricornLeafTwo.getDefaultState().withProperty(StateLibrary.APRICORNS2, apricorn).withProperty(BlockLeaves.CHECK_DECAY, false);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(APRICORNS).getMeta();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(int i = 0; i < 8; i++) {
            if (!ApriConfig.masterBallCrafting && i == 7) break;
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(StateLibrary.STAGE) == 1 ? 8 : 0) + state.getValue(APRICORNS).getMeta();
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
        return this.getDefaultState().withProperty(StateLibrary.STAGE, stage).withProperty(APRICORNS, EnumApricorns.byMeta(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {APRICORNS, StateLibrary.STAGE});
    }
}
