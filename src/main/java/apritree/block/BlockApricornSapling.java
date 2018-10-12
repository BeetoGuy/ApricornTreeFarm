package apritree.block;

import apritree.config.ApriConfig;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import apritree.ApriRegistry;
import apritree.worldgen.WorldGenApricornTree;

import java.util.List;
import java.util.Random;

public class BlockApricornSapling extends BlockBush implements IGrowable
{
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockApricornSapling()
    {
        super(Material.PLANTS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(APRICORNS, EnumApricorns.BLACK).withProperty(STAGE, 0));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setUnlocalizedName("apritree:apricorn_sapling");
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (state.getValue(STAGE) == 0)
        {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state, rand);
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator gen = new WorldGenApricornTree(true, getLeafState(state.getValue(APRICORNS)), getLogState(state.getValue(APRICORNS)));

        IBlockState air = Blocks.AIR.getDefaultState();
        world.setBlockState(pos, air, 4);

        if(!gen.generate(world, rand, pos))
        {
            world.setBlockState(pos, state, 4);
        }
    }

    private IBlockState getLogState(EnumApricorns apricorn)
    {
        switch(apricorn)
        {
            case BLACK: return Blocks.LOG2.getStateFromMeta(1);
            case WHITE: return Blocks.LOG.getStateFromMeta(2);
            case PINK: return Blocks.LOG.getStateFromMeta(3);
            case GREEN: return Blocks.LOG.getStateFromMeta(1);
            case RED : return Blocks.LOG2.getDefaultState();
            case PURPLE: return Blocks.LOG2.getStateFromMeta(1);
            default: return Blocks.LOG.getDefaultState();
        }
    }

    private IBlockState getLeafState(EnumApricorns apricorn)
    {
        switch(apricorn)
        {
            case BLACK: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
            case WHITE: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(BlockApricornLeafOne.APRICORNS, EnumApricorns.WHITE).withProperty(BlockLeaves.CHECK_DECAY, false);
            case PINK: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(BlockApricornLeafOne.APRICORNS, EnumApricorns.PINK).withProperty(BlockLeaves.CHECK_DECAY, false);
            case GREEN: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(BlockApricornLeafOne.APRICORNS, EnumApricorns.GREEN).withProperty(BlockLeaves.CHECK_DECAY, false);
            case RED : return ApriRegistry.apricornLeafTwo.getDefaultState().withProperty(BlockApricornLeafTwo.APRICORNS, EnumApricorns.RED).withProperty(BlockLeaves.CHECK_DECAY, false);
            case BLUE: return ApriRegistry.apricornLeafTwo.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
            case YELLOW: return ApriRegistry.apricornLeafTwo.getDefaultState().withProperty(BlockApricornLeafTwo.APRICORNS, EnumApricorns.YELLOW).withProperty(BlockLeaves.CHECK_DECAY, false);
            case PURPLE: return ApriRegistry.apricornLeafTwo.getDefaultState().withProperty(BlockApricornLeafTwo.APRICORNS, EnumApricorns.PURPLE).withProperty(BlockLeaves.CHECK_DECAY, false);
            default: return ApriRegistry.apricornLeafOne.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
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
        for(EnumApricorns apricorn : EnumApricorns.values()) {
            if (!ApriConfig.masterBallCrafting && apricorn == EnumApricorns.PURPLE) break;
            list.add(new ItemStack(this, 1, apricorn.getMeta()));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(STAGE) == 1 ? 8 : 0) + state.getValue(APRICORNS).getMeta();
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
        return this.getDefaultState().withProperty(STAGE, stage).withProperty(APRICORNS, EnumApricorns.byMeta(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {APRICORNS, STAGE});
    }
}
