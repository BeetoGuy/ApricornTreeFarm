package apritree.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import apritree.ApriRegistry;

import java.util.Random;

public class WorldGenApricornTree extends WorldGenAbstractTree
{
    private static IBlockState LEAF;
    private static IBlockState LOG;

    public WorldGenApricornTree(boolean notify, IBlockState leaves, IBlockState logs)
    {
        super(notify);
        this.LEAF = leaves;
        this.LOG = logs;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        int height = rand.nextInt(3) + 5;

        boolean flag = true;

        if(pos.getY() > 0 && pos.getY() <= 256)
        {
            for(int y = pos.getY(); y <= pos.getY() + 1 + height; ++y)
            {
                int width = 1;

                if(y == pos.getY())
                    width = 0;

                if(y >= pos.getY() + 1 + height - 2)
                    width = 2;

                BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();

                for(int x = pos.getX() - width; x <= pos.getX() + width && flag; ++x)
                {
                    for(int z = pos.getZ() - width; z <= pos.getZ() + width && flag; ++z)
                    {
                        if(y >= 0 && y < world.getHeight())
                        {
                            if(!this.isReplaceable(world, mut.setPos(x, y, z)))
                                flag = false;
                        }
                        else
                            flag = false;
                    }
                }
            }

            if(!flag)
                return false;
            else
            {
                BlockPos down = pos.down();
                IBlockState state = world.getBlockState(down);
                boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (IPlantable) ApriRegistry.apricornSapling);
                if(isSoil && pos.getY() < world.getHeight() - height - 1)
                {
                    state.getBlock().onPlantGrow(state, world, down, pos);

                    for(int y = pos.getY() - 3 + height; y <= pos.getY() + height; ++y)
                    {
                        int w = y - (pos.getY() + height);
                        int width = 1 - w / 2;

                        for(int x = pos.getX() - width; x <= pos.getX() + width; ++x)
                        {
                            int xWide = x - pos.getX();
                            for(int z = pos.getZ() - width; z <= pos.getZ() + width; ++z)
                            {
                                int zWide = z - pos.getZ();

                                if(Math.abs(xWide) != width || Math.abs(zWide) != width || rand.nextInt(2) != 0 && w != 0)
                                {
                                    BlockPos leafPos = new BlockPos(x, y, z);
                                    IBlockState leafState = world.getBlockState(leafPos);
                                    if(leafState.getBlock().isAir(leafState, world, leafPos))
                                    {
                                        this.setBlockAndNotifyAdequately(world, leafPos, LEAF);
                                    }
                                }
                            }
                        }
                    }

                    for(int y = 0; y < height; ++y)
                    {
                        BlockPos up = pos.up(y);
                        IBlockState logState = world.getBlockState(up);
                        if(logState.getBlock().isAir(logState, world, up) || logState.getBlock().isLeaves(logState, world, up))
                        {
                            this.setBlockAndNotifyAdequately(world, up, LOG);
                        }
                    }
                    return true;
                }
                else
                    return false;
            }
        }
        else
            return false;
    }
}
