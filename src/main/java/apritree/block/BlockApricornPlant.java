package apritree.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockApricornPlant extends Block implements IPlantable, IGrowable
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);

    public BlockApricornPlant()
    {
        super(Material.WOOD);
        this.setHardness(1.5F);
        this.setTickRandomly(true);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(state.getValue(STAGE))
        {
            case 1: return new AxisAlignedBB(0.375F, 0.75F - 0.0625F, 0.375F, 0.625F, 0.9375F, 0.625F);
            case 2: return new AxisAlignedBB(0.3125F, 0.625F - 0.0625F, 0.3125F, 0.6875F, 0.9375F, 0.6875F);
            case 3: return new AxisAlignedBB(0.25F, 0.5F - 0.0625F, 0.25F, 0.75F, 0.9375F, 0.75F);
            default: return new AxisAlignedBB(0.4375F, 0.8125F, 0.4375F, 0.5625F, 0.9375F, 0.5625F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbor)
    {
        Block blockAbove = world.getBlockState(pos.up()).getBlock();
        if(!(blockAbove instanceof BlockLeaves))
        {
            if(state.getValue(STAGE) == 3)
                dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(getApricorn(state)));
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            if(rand.nextInt(30) == 0)
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

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            if(state.getValue(STAGE) == 3)
            {
                dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(getApricorn(state)));
                world.setBlockToAir(pos);
                return true;
            }
        }
        return false;
    }

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(STAGE) == 3 ? getApricorn(state) : null;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(getApricorn(state));
    }

    public abstract EnumApricorns getEnumApricorn(IBlockState state);

    public abstract Item getApricorn(IBlockState state);

    public void dropApricorn(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem item = new EntityItem(world, x, y, z, stack);

        float velocity = 0.05F;
        item.motionX = (float)world.rand.nextGaussian() * velocity;
        item.motionY = (float)world.rand.nextGaussian() * velocity + 0.2F;
        item.motionZ = (float)world.rand.nextGaussian() * velocity;
        item.setDefaultPickupDelay();
        world.spawnEntity(item);
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean bool) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int i = state.getValue(STAGE) + 1;
        if (i > 3)
            i = 3;
        worldIn.setBlockState(pos, state.withProperty(STAGE, i), 2);
    }
}
