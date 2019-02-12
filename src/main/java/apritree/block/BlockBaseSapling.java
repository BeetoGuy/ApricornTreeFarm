package apritree.block;

import apritree.worldgen.WorldGenApricornTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class BlockBaseSapling extends BlockBush implements IGrowable {
    public BlockBaseSapling() {
        super(Material.PLANTS);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return StateLibrary.SAPLING_AABB;
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
        if (state.getValue(StateLibrary.STAGE) == 0)
        {
            worldIn.setBlockState(pos, state.cycleProperty(StateLibrary.STAGE), 4);
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
        WorldGenerator gen = new WorldGenApricornTree(true, getLeafState(state), getLogState(state));

        IBlockState air = Blocks.AIR.getDefaultState();
        world.setBlockState(pos, air, 4);

        if(!gen.generate(world, rand, pos))
        {
            world.setBlockState(pos, state, 4);
        }
    }

    public abstract IBlockState getLeafState(IBlockState state);

    public abstract IBlockState getLogState(IBlockState state);
}
