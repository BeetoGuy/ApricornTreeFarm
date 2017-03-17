package apritree.block;

import ic2.core.IC2;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import apritree.block.tile.TileEntityElectricDriver;

public class BlockElectricDriver extends BlockDriver
{
    public BlockElectricDriver()
    {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setCreativeTab(IC2.tabIC2);
        this.setUnlocalizedName("apritree:electric_driver");
    }

    @Override
    public int getDriverID()
    {
        return 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityElectricDriver();
    }
}
