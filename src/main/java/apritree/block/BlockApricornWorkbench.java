package apritree.block;

import apritree.ApriTree;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockApricornWorkbench extends Block {
    public BlockApricornWorkbench() {
        super(Material.WOOD);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setUnlocalizedName("apritree:apricorn_workbench");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ApriTree.INSTANCE, 2, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
