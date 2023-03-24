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
import java.util.Set;

public class GeneralUtil {
    public static Direction findNearestDirection(Vec3d origin, Vec3d body, Set<Direction> enabledDirections, boolean invert) {
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

        final Direction result;
        if (Math.abs(relX) > Math.abs(relY) && Math.abs(relX) > Math.abs(relZ)) {
            result = (relX < 0 && enabledDirections.contains(Direction.WEST)) || !enabledDirections.contains(Direction.EAST) ? Direction.WEST : Direction.EAST;
        } else if (Math.abs(relY) > Math.abs(relZ) && Math.abs(relY) > Math.abs(relX)) {
            result = (relY < 0 && enabledDirections.contains(Direction.DOWN)) || !enabledDirections.contains(Direction.UP) ? Direction.DOWN : Direction.UP;
        } else {
            result = (relZ < 0 && enabledDirections.contains(Direction.NORTH)) || !enabledDirections.contains(Direction.SOUTH) ? Direction.NORTH : Direction.SOUTH;
        }

        return invert ? result.getOpposite() : result;
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

        final Direction direction = findNearestDirection(
            closest.getRegionOfActivation().getCenter(),
            closest.invertGravity() ? entity.getPos() : entity.getEyePos(),
            closest.getEnabledDirections(),
            closest.invertGravity()
        );
        if (entity.world.isClient && entity instanceof PlayerEntity) {
            addGravityClient(entity, CoreGravityVerifier.newFieldGravity(direction, closest.getGravityMultiplier()), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier.packInfo(closest.getPos()));
        } else if (!(entity instanceof PlayerEntity) && !entity.world.isClient) {
            GravityChangerAPI.addGravity(entity, new Gravity(direction, 5,closest.getGravityMultiplier(), 2, "star_heart"));
        }
        //((EntityAttachments)entity).setGravityMultiplier(closest.getGravityMultiplier());
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
            .map(e -> findNearestDirection(e.getRegionOfActivation().getCenter(), Vec3d.ofCenter(pos), e.getEnabledDirections(), e.invertGravity()))
            .orElse(Direction.DOWN);
    }
}
