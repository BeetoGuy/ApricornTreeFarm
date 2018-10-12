package apritree.block;

import com.pixelmonmod.pixelmon.config.PixelmonItemsApricorns;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockApricornComplex extends BlockApricornPlant
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() < 4);

    public BlockApricornComplex()
    {
        super();
        this.setUnlocalizedName("apritree:apricorn_one");
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLACK).withProperty(STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public EnumApricorns getEnumApricorn(IBlockState state)
    {
        return state.getValue(APRICORNS);
    }

    @Override
    public Item getApricorn(IBlockState state)
    {
        switch(state.getValue(APRICORNS))
        {
            case WHITE:
                return PixelmonItemsApricorns.apricornWhite;
            case PINK:
                return PixelmonItemsApricorns.apricornPink;
            case GREEN:
                return PixelmonItemsApricorns.apricornGreen;
            default:
                return PixelmonItemsApricorns.apricornBlack;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta(meta % 4);
        meta -= apricorn.getMeta();
        int stage = meta / 4;
        return this.getDefaultState().withProperty(APRICORNS, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(APRICORNS).getMeta();
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, APRICORNS, STAGE);
    }
}
