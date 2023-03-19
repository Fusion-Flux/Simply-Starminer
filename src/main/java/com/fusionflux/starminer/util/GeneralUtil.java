package com.fusionflux.starminer.util;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.starminer.SimplyStarminer;
import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Comparator;
import java.util.Objects;

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

    public static void setAppropriateEntityGravity(Entity entity) {
        BlockPos closest = null;
        double closestDist = Double.POSITIVE_INFINITY;
        final var it = ((EntityAttachments)entity).getNearbyStarCores().object2IntEntrySet().iterator();
        while (it.hasNext()) {
            final var entry = it.next();
            if (entry.getIntValue() <= 0) {
                it.remove();
                continue;
            }
            entry.setValue(entry.getIntValue() - 1);
            final double distance = entity.getPos().distanceTo(Vec3d.ofCenter(entry.getKey()));
            if (distance < closestDist) {
                closest = entry.getKey();
                closestDist = distance;
            }
        }

        if (closest == null) return;

        final Direction direction = GeneralUtil.findNearestDirection(Vec3d.ofCenter(closest), entity.getEyePos());
        if (entity.world.isClient && entity instanceof PlayerEntity) {
            addGravityClient(entity, CoreGravityVerifier.newFieldGravity(direction), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier.packInfo(closest));
        } else if (!(entity instanceof PlayerEntity) && !entity.world.isClient) {
            GravityChangerAPI.addGravity(entity, new Gravity(direction, 5, 2, "star_heart"));
        }
    }

    @ClientOnly
    private static void addGravityClient(Entity entity, Gravity gravity, Identifier verifier, PacketByteBuf verifierInfo) {
        GravityChangerAPI.addGravityClient((ClientPlayerEntity)entity, gravity, verifier, verifierInfo);
    }

    public static Direction getGravityForBlockPos(ServerWorld world, BlockPos pos) {
        return world.getPointOfInterestStorage()
            .getInSquare(
                h -> h.isRegistryKey(SimplyStarminer.STAR_CORE_POI),
                pos, 343, PointOfInterestStorage.OccupationStatus.ANY
            )
            .map(poi -> (AbstractStarCoreBlockEntity)world.getBlockEntity(poi.getPos()))
            .filter(Objects::nonNull)
            .filter(e -> GeneralUtil.isWithinCubeRadius(e.getPos(), pos, e.getRadius()))
            .min(Comparator.comparingDouble(e -> e.getPos().getSquaredDistance(pos)))
            .map(e -> GeneralUtil.findNearestDirection(Vec3d.ofCenter(e.getPos()), Vec3d.ofCenter(pos)))
            .orElse(Direction.DOWN);
    }
}
