package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
abstract class EntityMixin implements EntityAttachments {
    @Unique
    private Vec3d lastSSMVel = Vec3d.ZERO;

    @Shadow
    public abstract Vec3d getVelocity();

    int gravityPlateTimer = 0;
    int gravityStarTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (gravityPlateTimer > 0) {
            gravityPlateTimer--;
        }

        if (gravityStarTimer > 0) {
            gravityStarTimer--;
        }

        this.lastSSMVel = this.getVelocity();
    }

    @Override
    public int getPlateGravityTimer() {
        return this.gravityPlateTimer;
    }

    @Override
    public void setPlateGravityTimer(int gravityTimer) {
        this.gravityPlateTimer = gravityTimer;
    }

    @Override
    public int getStarGravityTimer() {
        return this.gravityStarTimer;
    }

    @Override
    public void setStarGravityTimer(int gravityTimer) {
        this.gravityStarTimer = gravityTimer;
    }

    @Override
    public Vec3d getLastSSMVel() {
        return this.lastSSMVel;
    }
}
