package apritree.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockBerryPlaceholder extends Block {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

    public BlockBerryPlaceholder() {
        super(Material.PLANTS);
        this.setTranslationKey("apritree:berrybush");
        this.setRegistryName("berrybush");
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }
}
