package apritree.block;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.math.AxisAlignedBB;

public class StateLibrary {
    public static final PropertyEnum<EnumApricorns> APRICORNS1 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() >= 0 && a.getMeta() < 4);
    public static final PropertyEnum<EnumApricorns> APRICORNS2 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() > 3 && a.getMeta() < 8);
    public static final PropertyEnum<EnumApricorns> APRICORNS3 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() > 7 && a.getMeta() < 12);
    public static final PropertyEnum<EnumApricorns> APRICORNS4 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() > 11 && a.getMeta() < 16);
    public static final PropertyEnum<EnumApricorns> APRICORNS5 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() > 15 && a.getMeta() < 20);
    public static final PropertyEnum<EnumApricorns> APRICORNS6 = PropertyEnum.create("type", EnumApricorns.class, a -> a.getMeta() > 19 && a.getMeta() < 23);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

    public static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
}
