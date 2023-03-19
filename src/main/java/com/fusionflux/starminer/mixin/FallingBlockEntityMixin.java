package com.fusionflux.starminer.mixin;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.starminer.util.GeneralUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "fall", at = @At("RETURN"))
    private static FallingBlockEntity applyGravityF(FallingBlockEntity entity, @Local BlockPos pos) {
        final Direction gravity = GeneralUtil.getGravityForBlockPos((ServerWorld)entity.world, pos);
        GravityChangerAPI.addGravity(entity, new Gravity(gravity, 5, 2, "star_heart"));
        entity.setPosition(Vec3d.ofCenter(pos).add(Vec3d.of(gravity.getVector())));
        return entity;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyGravityT(CallbackInfo ci) {
        GeneralUtil.setAppropriateEntityGravity(this);
    }

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
        )
    )
    private BlockPos relativeToGravity(BlockPos instance) {
        return instance.offset(GeneralUtil.getGravityForBlockPos((ServerWorld)world, instance));
    }
}
