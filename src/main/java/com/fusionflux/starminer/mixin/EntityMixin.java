package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.SimplyStarminerConfig;
import com.fusionflux.starminer.duck.EntityAttachments;
import com.fusionflux.starminer.util.GeneralUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityAttachments {
    @Unique
    private Vec3d lastSSMVel = Vec3d.ZERO;

    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow public World world;

    int gravityPlateTimer = 0;
    int gravityStarTimer = 0;

    private final Object2IntMap<BlockPos> nearbyStarCores = new Object2IntOpenHashMap<>();

    @SuppressWarnings("ConstantValue")
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (gravityPlateTimer > 0) {
            gravityPlateTimer--;
        }

        if (gravityStarTimer > 0) {
            gravityStarTimer--;
        }

        this.lastSSMVel = this.getVelocity();

        if (
            (Object)this instanceof PlayerEntity player &&
                !SimplyStarminerConfig.creativeFlyingGravity &&
                player.getAbilities().flying
        ) return;
        GeneralUtil.setAppropriateEntityGravity((Entity)(Object)this);
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

    @Override
    public Object2IntMap<BlockPos> getNearbyStarCores() {
        return nearbyStarCores;
    }
}
