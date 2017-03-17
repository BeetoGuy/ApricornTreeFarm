package pixelplus.worldgen.gen;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import pixelplus.PixelPlus;
import pixelplus.PixelRegistry;
import pixelplus.block.BlockApricornSapling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ApricornTreeGenerator implements IWorldGenerator {
    public static ApricornTreeGenerator INSTANCE = new ApricornTreeGenerator();

    protected IBlockState air = Blocks.AIR.getDefaultState();
    private IBlockState[] saplings;

    private ApricornTreeGenerator() {
        saplings = new IBlockState[7];
        for (int i = 0; i < 7; i++) {
            saplings[i] = PixelRegistry.apricornSapling.getStateFromMeta(i);
        }
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator gen, IChunkProvider provider) {
        if (world.provider.getDimensionType() != DimensionType.OVERWORLD) return;
        int randChance = world.getWorldType() == WorldType.FLAT ? 100 : 10;
        if (rand.nextInt(randChance) != 0) return;
        List<IBlockState> states = new ArrayList<IBlockState>();
        int x = chunkX * 16 + rand.nextInt(16);
        int z = chunkZ * 16 + rand.nextInt(16);
        int y = world.getHeight(new BlockPos(x, 0, z)).getY();
        BlockPos pos = findGround(world, x, y, z);

        if (isGround(world, pos.down()) && isReplacable(world, pos) && isAir(world, pos.up())) {
            if (world.getWorldType() == WorldType.FLAT) {
                for (int i = 0; i < saplings.length; i++)
                    states.add(saplings[i]);
            } else {
                for (int i = 0; i < saplings.length; i++) {
                    Biome biome = world.getBiome(pos);
                    BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
                    for (BiomeDictionary.Type type : types) {
                        if (Arrays.asList(saplings[i].getValue(BlockApricornSapling.APRICORNS).getBiomeTypes()).contains(type)) {
                            states.add(saplings[i]);
                        }
                    }
                }
            }
            if (states.size() > 0) {
                IBlockState saplingState = states.get(rand.nextInt(states.size()));
                ((BlockApricornSapling) saplingState.getBlock()).generateTree(world, pos, saplingState, rand);
                //PixelPlus.logger.debug("Generated tree at: x: " + x + ", y: " + y + ", z: " + z);
            }
        }
    }

    private boolean isAir(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos);
    }

    private boolean isReplacable(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isReplaceable(world, pos) || state.getBlock().isAir(state, world, pos);
    }

    private boolean isGround(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS;
    }

    private BlockPos findGround(World world, int x, int y, int z) {
        int height = y;
        boolean groundFound = false;
        int minHeight = world.getWorldType() == WorldType.FLAT ? 3 : 64;
        while (height > minHeight && !groundFound) {
            BlockPos pos = new BlockPos(x, height, z);
            if (!isGround(world, pos))
                height--;
            else
                groundFound = true;
        }
        return new BlockPos(x, height + 1, z);
    }
}
