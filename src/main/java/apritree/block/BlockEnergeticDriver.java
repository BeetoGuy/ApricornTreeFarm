package apritree.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import apritree.block.tile.TileEntityEnergyDriver;

public class BlockEnergeticDriver extends BlockDriver
{
    public BlockEnergeticDriver()
    {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setUnlocalizedName("apritree:energetic_driver");
    }

    @Override
    public int getDriverID()
    {
        return 2;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityEnergyDriver();
    }
}
