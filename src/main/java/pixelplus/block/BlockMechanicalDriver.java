package pixelplus.block;

import betterwithmods.BWMBlocks;
import betterwithmods.BWSounds;
import betterwithmods.api.block.IMechanicalBlock;
import betterwithmods.client.BWCreativeTabs;
import betterwithmods.util.InvUtils;
import betterwithmods.util.MechanicalUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import pixelplus.block.tile.TileEntityMechanicalDriver;

import java.util.Random;

public class BlockMechanicalDriver extends BlockDriver implements IMechanicalBlock
{
    public BlockMechanicalDriver()
    {
        super(Material.ROCK);
        this.setHardness(3.5F);
        this.setCreativeTab(BWCreativeTabs.BWTAB);
        this.setUnlocalizedName("pixelplus:mechanical_driver");
    }

    @Override
    public int getDriverID()
    {
        return 0;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(world, pos, state);
        world.scheduleBlockUpdate(pos, this, 10, 5);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        boolean gettingPower = isInputtingMechPower(world, pos);
        boolean isOn = state.getValue(ACTIVE);
        boolean isRedstonePowered = world.isBlockIndirectlyGettingPowered(pos) > 0;

        if(isRedstonePowered)
            gettingPower = false;

        if(isOn != gettingPower)
        {
            if(isOn)
                world.setBlockState(pos, state.withProperty(ACTIVE, false));
            else
                world.setBlockState(pos, state.withProperty(ACTIVE, true));
            world.playSound(null, pos, BWSounds.WOODCREAK, SoundCategory.BLOCKS, 0.25F, world.rand.nextFloat() * 0.25F + 0.25F);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
    {
        boolean isOn = state.getValue(ACTIVE);

        if(isOn)
        {
            boolean gettingPower = isInputtingMechPower(world, pos);
            boolean isRedstonePowered = world.isBlockIndirectlyGettingPowered(pos) > 0;

            if(isRedstonePowered)
                gettingPower = false;
            if(!gettingPower)
            {
                world.scheduleBlockUpdate(pos, this, 9, 5);
                return;
            }
        }
        world.scheduleBlockUpdate(pos, this, 10, 5);
    }

    @Override
    public boolean isMechanicalOn(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getValue(ACTIVE);
    }

    @Override
    public void setMechanicalOn(World world, BlockPos pos, boolean isOn)
    {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() == this && state.getValue(ACTIVE) != isOn)
            world.setBlockState(pos, state.withProperty(ACTIVE, isOn));
    }

    @Override
    public boolean isOutputtingMechPower(World world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean canInputMechanicalPower()
    {
        return true;
    }

    @Override
    public boolean canInputPowerToSide(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return facing != world.getBlockState(pos).getValue(FACING);
    }

    @Override
    public boolean canOutputMechanicalPower()
    {
        return false;
    }

    @Override
    public boolean isMechanicalOnFromState(IBlockState state)
    {
        return state.getValue(ACTIVE);
    }

    @Override
    public boolean isInputtingMechPower(World world, BlockPos pos)
    {
        EnumFacing dir = world.getBlockState(pos).getValue(FACING);
        boolean powered = false;
        for(EnumFacing face : EnumFacing.VALUES)
        {
            if(face != dir)
                powered = MechanicalUtil.isBlockPoweredByAxleOnSide(world, pos, face);
            if(powered)
                break;
        }
        return powered;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();
        boolean active = false;
        if(meta > 7)
        {
            active = true;
            meta -= 8;
        }
        return state.withProperty(FACING, EnumFacing.getFront(meta)).withProperty(ACTIVE, active);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() + (state.getValue(ACTIVE) ? 8 : 0);
    }

    @Override
    public void overpower(World world, BlockPos pos)
    {
        if(world.getBlockState(pos).getValue(ACTIVE))
        {
            if (OreDictionary.doesOreNameExist("nuggetIron") && OreDictionary.getOres("nuggetIron").size() > 0) {
                for (int i = 0; i < 4; i++)
                    InvUtils.ejectStackWithOffset(world, pos, OreDictionary.getOres("nuggetIron").get(0).copy());
            }
            InvUtils.ejectStackWithOffset(world, pos, new ItemStack(BWMBlocks.ROPE));
            InvUtils.ejectStackWithOffset(world, pos, new ItemStack(Items.GOLD_NUGGET, 2));
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.3F, world.rand.nextFloat() * 0.1F + 0.45F);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMechanicalDriver();
    }
}
