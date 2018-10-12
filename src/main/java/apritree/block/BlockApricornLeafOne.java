package apritree.block;

import com.google.common.base.Predicate;
import com.pixelmonmod.pixelmon.config.PixelmonItemsApricorns;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import apritree.ApriRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockApricornLeafOne extends BlockLeaves
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, new Predicate<EnumApricorns>()
    {
        @Override
        public boolean apply(EnumApricorns apricorns)
        {
            return apricorns.getMeta() < 4;
        }
    });

    public BlockApricornLeafOne()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLACK).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
        this.setUnlocalizedName("apritree:apricorn_leaf_first");
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(DECAYABLE, false);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(world, pos, state, rand);
        if(!world.isRemote)
        {
            Block below = world.getBlockState(pos.down()).getBlock();
            if(below.isAir(world.getBlockState(pos.down()), world, pos.down()) || below.isReplaceable(world, pos.down()))
            {
                if(rand.nextInt(30) == 0) {
                    world.setBlockState(pos.down(), ApriRegistry.apricornOne.getDefaultState().withProperty(BlockApricornComplex.APRICORNS, state.getValue(APRICORNS)));
                    if(!state.getValue(DECAYABLE))
                        world.setBlockState(pos, state.withProperty(DECAYABLE, true));
                }
            }
        }
    }

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if(world.rand.nextInt(chance) == 0)
        {
            spawnAsEntity(world, pos, new ItemStack(getApricorn(state)));
        }
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

    public EnumApricorns getApricornType(int meta)
    {
        return EnumApricorns.byMeta((meta & 3) % 4);
    }

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
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ApriRegistry.apricornSapling);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(APRICORNS).getMeta();
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.byMetadata((meta & 3) % 4);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(APRICORNS).getMeta()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(APRICORNS, getApricornType(meta)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(APRICORNS).getMeta();

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
        return new BlockStateContainer(this, new IProperty[] {APRICORNS, CHECK_DECAY, DECAYABLE});
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
