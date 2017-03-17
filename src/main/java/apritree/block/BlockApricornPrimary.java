package apritree.block;

import com.google.common.base.Predicate;
import com.pixelmonmod.pixelmon.config.PixelmonItemsApricorns;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import apritree.ApriRegistry;

import java.util.Random;

public class BlockApricornPrimary extends BlockApricornPlant
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, new Predicate<EnumApricorns>()
    {
        @Override
        public boolean apply(EnumApricorns apricorns)
        {
            return apricorns.getMeta() > 3;
        }
    });

    public BlockApricornPrimary()
    {
        super();
        this.setUnlocalizedName("apritree:apricorn_two");
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLUE).withProperty(STAGE, 0));
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
            case RED:
                return PixelmonItemsApricorns.apricornRed;
            case BLUE:
                return PixelmonItemsApricorns.apricornBlue;
            case PURPLE:
                return ApriRegistry.apricorn;
            default:
                return PixelmonItemsApricorns.apricornYellow;
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(state.getValue(APRICORNS) == EnumApricorns.PURPLE)
        {
            if(!world.isRemote)
            {
                if(rand.nextInt(151) == 0)
                {
                    if(state.getValue(STAGE) < 3)
                        world.setBlockState(pos, state.withProperty(STAGE, state.getValue(STAGE) + 1));
                    else
                    {
                        dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(getApricorn(state)));
                        world.setBlockToAir(pos);
                    }
                }
            }
        }
        else
            super.updateTick(world, pos, state, rand);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumApricorns apricorn = EnumApricorns.byMeta((meta % 4) + 4);
        meta = apricorn.getMeta() - 4;
        int stage = meta / 4;
        return this.getDefaultState().withProperty(APRICORNS, apricorn).withProperty(STAGE, stage);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(APRICORNS).getMeta() - 4;
        meta += state.getValue(STAGE) * 4;
        return meta;
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, APRICORNS, STAGE);
    }
}
