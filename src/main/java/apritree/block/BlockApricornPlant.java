package apritree.block;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockApricornPlant extends Block implements IPlantable, IGrowable
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);
    private static final AxisAlignedBB STAGE_0 = new AxisAlignedBB(0.4375F, 0.8125F, 0.4375F, 0.5625F, 0.9375F, 0.5625F);
    private static final AxisAlignedBB STAGE_1 = new AxisAlignedBB(0.375F, 0.75F - 0.0625F, 0.375F, 0.625F, 0.9375F, 0.625F);
    private static final AxisAlignedBB STAGE_2 = new AxisAlignedBB(0.3125F, 0.625F - 0.0625F, 0.3125F, 0.6875F, 0.9375F, 0.6875F);
    private static final AxisAlignedBB STAGE_3 = new AxisAlignedBB(0.25F, 0.5F - 0.0625F, 0.25F, 0.75F, 0.9375F, 0.75F);

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
            case 1: return STAGE_1;
            case 2: return STAGE_2;
            case 3: return STAGE_3;
            default: return STAGE_0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbor, BlockPos neighborPos)
    {
        Block blockAbove = world.getBlockState(pos.up()).getBlock();
        if(!(blockAbove instanceof BlockLeaves))
        {
            if(state.getValue(STAGE) == 3)
                dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getApricorn(state).copy());
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(STAGE) == 3) {
            drops.add(getApricorn(state).copy());
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        apricornHarvest(world, pos, state, rand, 30);
    }

    public void apricornHarvest(World world, BlockPos pos, IBlockState state, Random rand, int chance) {
        if(rand.nextInt(chance) == 0) {
            if(state.getValue(STAGE) < 3)
                world.setBlockState(pos, state.cycleProperty(STAGE));
                //world.setBlockState(pos, state.withProperty(STAGE, state.getValue(STAGE) + 1));
            else {
                dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getApricorn(state).copy());
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (state.getValue(STAGE) == 3) {
            if (!world.isRemote) {
                ApricornEvent.PickApricorn evt = new ApricornEvent.PickApricorn(null, pos, (EntityPlayerMP)player, null, getApricorn(state).copy());
                Pixelmon.EVENT_BUS.post(evt);
                dropApricorn(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, evt.getPickedStack());
                world.setBlockToAir(pos);
            }
            return true;
        }
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(STAGE) == 3 ? getApricorn(state).getItem() : Items.AIR;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return getApricorn(state);
    }

    public abstract ItemStack getApricorn(IBlockState state);

    public abstract EnumApricorns getEnumApricorn(IBlockState state);

    public static void dropApricorn(World world, double x, double y, double z, ItemStack stack)
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
        return true;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean bool) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int chance = getEnumApricorn(state).getGrowthChance();
        apricornHarvest(worldIn, pos, state, rand, chance);/*
        int i = state.getValue(STAGE) + 1;
        if (i > 3)
            i = 3;
        worldIn.setBlockState(pos, state.withProperty(STAGE, i), 2);*/
    }
}
