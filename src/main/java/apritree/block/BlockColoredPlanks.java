package apritree.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockColoredPlanks extends Block {
    public static final PropertyEnum<EnumApricorns> APRICORNS = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() < 16);

    public BlockColoredPlanks() {
        super(Material.WOOD);
        this.setHardness(3.5F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setTranslationKey("apritree:planks_colored");
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(APRICORNS).getMeta();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = 0; i < 16; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(APRICORNS).getMeta();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(APRICORNS, EnumApricorns.byMeta(meta));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, APRICORNS);
    }
}
