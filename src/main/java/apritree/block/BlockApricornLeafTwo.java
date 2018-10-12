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
import apritree.config.ApriConfig;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockApricornLeafTwo extends BlockLeaves
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, new Predicate<EnumApricorns>()
    {
        @Override
        public boolean apply(EnumApricorns apricorns)
        {
            return apricorns.getMeta() > 3;
        }
    });

    public BlockApricornLeafTwo()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLUE).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
        this.setUnlocalizedName("apritree:apricorn_leaf_second");
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(APRICORNS).getMeta();
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(DECAYABLE, false);
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
                int chance = state.getValue(APRICORNS) == EnumApricorns.PURPLE ? 151 : 30;
                if(rand.nextInt(chance) == 0) {
                    world.setBlockState(pos.down(), ApriRegistry.apricornTwo.getDefaultState().withProperty(BlockApricornPrimary.APRICORNS, state.getValue(APRICORNS)));
                    if(!state.getValue(DECAYABLE))
                        world.setBlockState(pos, state.withProperty(DECAYABLE, true));
                }
                else if(canHybridize(world, pos, state))
                {
                    if(rand.nextInt(807) == 0) {
                        world.setBlockState(pos.down(), ApriRegistry.apricornTwo.getDefaultState().withProperty(BlockApricornPrimary.APRICORNS, EnumApricorns.PURPLE));
                        if (!state.getValue(DECAYABLE))
                            world.setBlockState(pos, state.withProperty(DECAYABLE, true));
                    }
                }
            }
        }
    }

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if(world.rand.nextInt(chance) == 0 && state.getValue(APRICORNS) != EnumApricorns.PURPLE)
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

    public boolean canHybridize(World world, BlockPos pos, IBlockState state)
    {
        if (!ApriConfig.masterBallCrafting) return false;
        EnumApricorns apricornType = state.getValue(APRICORNS);
        if(apricornType == EnumApricorns.RED || apricornType == EnumApricorns.BLUE) {
            for (int x = -4; x < 5; x++) {
                for (int y = -2; y < 3; y++) {
                    for (int z = -4; z < 5; z++) {
                        BlockPos check = pos.add(x, y, z);
                        IBlockState checkState = world.getBlockState(check);
                        if (checkState.getBlock() == this) {
                            if (apricornType == EnumApricorns.BLUE && checkState.getValue(APRICORNS) == EnumApricorns.RED)
                                return true;
                            else if (apricornType == EnumApricorns.RED && checkState.getValue(APRICORNS) == EnumApricorns.BLUE)
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ApriRegistry.apricornSapling);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, state.getBlock().getMetaFromState(state) & 3);
    }

    public EnumApricorns getApricornType(int meta)
    {
        return EnumApricorns.byMeta((meta & 3) + 4);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.byMetadata((meta & 3) % 4);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(APRICORNS).getMeta() - 4));
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
        i = i | state.getValue(APRICORNS).getMeta() - 4;

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
