package apritree.block;

import apritree.utils.ApriBreeding;
import apritree.utils.BreedingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockApriLeafBase extends BlockLeaves {
    public BlockApriLeafBase() {
        super();
    }

    public abstract EnumApricorns getApricornFromState(IBlockState state);

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return Blocks.LEAVES.getRenderLayer();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(DECAYABLE, false);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isRemote) {
            Block below = world.getBlockState(pos.down()).getBlock();
            if(below.isAir(world.getBlockState(pos.down()), world, pos.down()) || below.isReplaceable(world, pos.down())) {
                EnumApricorns apricorn = getApricornFromState(state);
                if (rand.nextInt(apricorn.getGrowthChance()) == 0) {
                    IBlockState plantState = EnumApricorns.getApriBlock(apricorn);
                    world.setBlockState(pos.down(), plantState);
                    if(!state.getValue(DECAYABLE))
                        world.setBlockState(pos, state.withProperty(DECAYABLE, true));
                } else {
                    getHybridState(world, pos, apricorn, rand);
                }
            }
        }
    }

    public void getHybridState(World world, BlockPos pos, EnumApricorns apricorn, Random rand) {
        int x = rand.nextInt(9) - 5;
        int y = rand.nextInt(5) - 3;
        int z = rand.nextInt(9) - 5;
        BlockPos check = pos.add(x,y,z);
        IBlockState toCheck = world.getBlockState(check);
        if (toCheck.getBlock() instanceof BlockApriLeafBase) {
            EnumApricorns checking = ((BlockApriLeafBase)toCheck.getBlock()).getApricornFromState(toCheck);
            if (checking != null) {
                ApriBreeding match = BreedingRegistry.getBreedingMatch(apricorn, checking);
                if (match != null && rand.nextInt(match.getBreedChance()) == 0) {
                    world.setBlockState(pos.down(), EnumApricorns.getApriBlock(match.getChild()));
                }
            }
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

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if(world.rand.nextInt(chance) == 0 && getApricornFromState(state) != EnumApricorns.PURPLE)
        {
            spawnAsEntity(world, pos, EnumApricorns.getApricornFromEnum(getApricornFromState(state)));
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, state.getBlock().getMetaFromState(state) & 3);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.byMetadata((meta & 3) % 4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
