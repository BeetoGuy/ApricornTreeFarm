package apritree.block;

import apritree.ApriTree;
import apritree.block.tile.TileEntityCharger;
import apritree.block.tile.TileEntityRoller;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BlockMachine extends BlockHorizontal {
    public static final PropertyBool CHARGER = PropertyBool.create("charger");

    public BlockMachine() {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setResistance(10.0F);
        this.setUnlocalizedName("apritree:machine");
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHARGER, false).withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 4));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;
        else {
            if (world.getTileEntity(pos) != null) {
                if (state.getValue(CHARGER)) {
                    player.openGui(ApriTree.INSTANCE, 1, world, pos.getX(), pos.getY(), pos.getZ());
                } else
                    player.openGui(ApriTree.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
            return false;
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(CHARGER) ? 4 : 0;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(CHARGER, stack.getMetadata() > 3).withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            ItemStackHandler handler = (ItemStackHandler)tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (handler != null)
            for (int i = 0; i < handler.getSlots(); i++) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, handler.getStackInSlot(i));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        if (state.getValue(CHARGER)) return new TileEntityCharger();
        else return new TileEntityRoller();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(CHARGER) ? 4 : 0;
        meta += state.getValue(FACING).getHorizontalIndex();
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean charger = meta > 3;
        EnumFacing facing = EnumFacing.getHorizontal(meta % 4);
        return this.getDefaultState().withProperty(CHARGER, charger).withProperty(FACING, facing);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHARGER, FACING);
    }
}
