package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.SimplyStarminer;
import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import com.fusionflux.starminer.util.GeneralUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.FallingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Comparator;
import java.util.Objects;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
    @Redirect(
        method = "scheduledTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
        )
    )
    private BlockPos relativeToGravity(BlockPos instance, @Local ServerWorld world) {
        return world.getPointOfInterestStorage().getInSquare(
            h -> h.isRegistryKey(SimplyStarminer.STAR_CORE_POI),
            instance, 343, PointOfInterestStorage.OccupationStatus.ANY
        ).map(poi -> (AbstractStarCoreBlockEntity)world.getBlockEntity(poi.getPos()))
            .filter(Objects::nonNull)
            .filter(e -> GeneralUtil.isWithinCubeRadius(e.getPos(), instance, e.getRadius()))
            .min(Comparator.comparingDouble(e -> e.getPos().getSquaredDistance(instance)))
            .map(e -> GeneralUtil.findNearestDirection(Vec3d.ofCenter(e.getPos()), Vec3d.ofCenter(instance)))
            .map(instance::offset)
            .orElseGet(instance::down);
    }
}
