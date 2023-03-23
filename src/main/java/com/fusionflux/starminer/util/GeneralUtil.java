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
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class GeneralUtil {
    public static boolean isWithinCubeRadius(BlockPos a, BlockPos b, int radius) {
        final int distanceX = Math.abs(b.getX() - a.getX());
        final int distanceY = Math.abs(b.getY() - a.getY());
        final int distanceZ = Math.abs(b.getZ() - a.getZ());
        return Math.max(distanceX, Math.max(distanceY, distanceZ)) <= radius;
    }

    public static Direction findNearestDirection(Vec3d origin, Vec3d body, Set<Direction> enabledDirections) {
        if (enabledDirections.isEmpty()) {
            return Direction.DOWN; // Fallback
        }

        double relX = origin.x - body.x;
        double relY = origin.y - body.y;
        double relZ = origin.z - body.z;

        if (!enabledDirections.contains(Direction.WEST) && !enabledDirections.contains(Direction.EAST)) {
            relX = 0;
        }
        if (!enabledDirections.contains(Direction.DOWN) && !enabledDirections.contains(Direction.UP)) {
            relY = 0;
        }
        if (!enabledDirections.contains(Direction.NORTH) && !enabledDirections.contains(Direction.SOUTH)) {
            relZ = 0;
        }

        if (Math.abs(relX) > Math.abs(relY) && Math.abs(relX) > Math.abs(relZ)) {
            return (relX < 0 && enabledDirections.contains(Direction.WEST)) || !enabledDirections.contains(Direction.EAST) ? Direction.WEST : Direction.EAST;
        } else if (Math.abs(relY) > Math.abs(relZ) && Math.abs(relY) > Math.abs(relX)) {
            return (relY < 0 && enabledDirections.contains(Direction.DOWN)) || !enabledDirections.contains(Direction.UP) ? Direction.DOWN : Direction.UP;
        } else {
            return (relZ < 0 && enabledDirections.contains(Direction.NORTH)) || !enabledDirections.contains(Direction.SOUTH) ? Direction.NORTH : Direction.SOUTH;
        }
    }

    public static Direction findNearestDirection(Vec3d origin, Vec3d body) {
        return findNearestDirection(origin, body, EnumSet.allOf(Direction.class));
    }

    public static void setAppropriateEntityGravity(Entity entity) {
        AbstractStarCoreBlockEntity closest = null;
        double closestDist = Double.POSITIVE_INFINITY;
        final var it = ((EntityAttachments)entity).getNearbyStarCores().object2IntEntrySet().iterator();
        while (it.hasNext()) {
            final var entry = it.next();
            if (entry.getIntValue() <= 0) {
                it.remove();
                continue;
            }
            entry.setValue(entry.getIntValue() - 1);
            final double distance = entity.getPos().distanceTo(Vec3d.ofCenter(entry.getKey().getPos()));
            if (distance < closestDist) {
                closest = entry.getKey();
                closestDist = distance;
            }
        }

        if (closest == null) {
            ((EntityAttachments)entity).setGravityMultiplier(1);
            return;
        }

        final Direction direction = GeneralUtil.findNearestDirection(closest.getRegionOfActivation().getCenter(), entity.getEyePos(), closest.getEnabledDirections());
        if (entity.world.isClient && entity instanceof PlayerEntity) {
            addGravityClient(entity, CoreGravityVerifier.newFieldGravity(direction), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier.packInfo(closest.getPos()));
        } else if (!(entity instanceof PlayerEntity) && !entity.world.isClient) {
            GravityChangerAPI.addGravity(entity, new Gravity(direction, 5, 2, "star_heart"));
        }
        ((EntityAttachments)entity).setGravityMultiplier(closest.getGravityMultiplier());
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
            .filter(e -> e.getRegionOfActivation().contains(Vec3d.ofCenter(pos)))
            .min(Comparator.comparingDouble(e -> e.getPos().getSquaredDistance(pos)))
            .map(e -> GeneralUtil.findNearestDirection(e.getRegionOfActivation().getCenter(), Vec3d.ofCenter(pos), e.getEnabledDirections()))
            .orElse(Direction.DOWN);
    }
}
