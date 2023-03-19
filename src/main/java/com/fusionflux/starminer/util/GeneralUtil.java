package com.fusionflux.starminer.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class GeneralUtil {
    public static boolean isWithinCubeRadius(BlockPos a, BlockPos b, int radius) {
        final int distanceX = Math.abs(b.getX() - a.getX());
        final int distanceY = Math.abs(b.getY() - a.getY());
        final int distanceZ = Math.abs(b.getZ() - a.getZ());
        return Math.max(distanceX, Math.max(distanceY, distanceZ)) <= radius;
    }

    public static Direction findNearestDirection(Vec3d origin, Vec3d body) {
        final double relX = origin.x - body.x;
        final double relY = origin.y - body.y;
        final double relZ = origin.z - body.z;

        if (Math.abs(relX) > Math.abs(relY) && Math.abs(relX) > Math.abs(relZ)) {
            return relX < 0 ? Direction.WEST : Direction.EAST;
        } else if (Math.abs(relY) > Math.abs(relZ) && Math.abs(relY) > Math.abs(relX)) {
            return relY < 0 ? Direction.DOWN : Direction.UP;
        } else {
            return relZ < 0 ? Direction.NORTH : Direction.SOUTH;
        }
    }
}
