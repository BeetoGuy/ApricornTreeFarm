package pixelplus.utils;

import net.minecraft.util.EnumFacing;

public class PlacementUtil
{
    public static EnumFacing rotateFacingAroundY(EnumFacing facing, boolean counterClockwise)
    {
        switch(facing)
        {
            case NORTH: return counterClockwise ? EnumFacing.WEST : EnumFacing.EAST;
            case WEST: return counterClockwise ? EnumFacing.SOUTH : EnumFacing.NORTH;
            case SOUTH: return counterClockwise ? EnumFacing.EAST : EnumFacing.WEST;
            case EAST: return counterClockwise ? EnumFacing.NORTH : EnumFacing.SOUTH;
            default: return facing;
        }
    }
}
