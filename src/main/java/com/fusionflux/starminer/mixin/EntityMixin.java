package com.fusionflux.starminer.mixin;

import com.fusionflux.gravity_api.mixin.*;
import com.fusionflux.gravity_api.mixin.client.CameraMixin;
import com.fusionflux.gravity_api.mixin.client.ClientPlayNetworkHandlerMixin;
import com.fusionflux.gravity_api.mixin.client.EntityRenderMixin;
import com.fusionflux.gravity_api.mixin.client.PlayerEntityRendererMixin;
import com.fusionflux.gravity_api.util.QuaternionUtil;
import com.fusionflux.gravity_api.util.RotationUtil;
import com.fusionflux.starminer.SimplyStarminerConfig;
import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import com.fusionflux.starminer.duck.EntityAttachments;
import com.fusionflux.starminer.util.GeneralUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityAttachments {
    @Shadow public World world;

    int gravityPlateTimer = 0;
    int gravityStarTimer = 0;

    private final Object2IntMap<AbstractStarCoreBlockEntity> nearbyStarCores = new Object2IntOpenHashMap<>();

    private double gravityMultiplier = 1;

    @SuppressWarnings("ConstantValue")
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (gravityPlateTimer > 0) {
            gravityPlateTimer--;
        }

        if (gravityStarTimer > 0) {
            gravityStarTimer--;
        }

        if (
            (Object)this instanceof PlayerEntity player &&
                !SimplyStarminerConfig.creativeFlyingGravity &&
                player.getAbilities().flying
        ) {
            gravityMultiplier = 1;
            return;
        }
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
    public Object2IntMap<AbstractStarCoreBlockEntity> getNearbyStarCores() {
        return nearbyStarCores;
    }

    @Override
    public double getGravityMultiplier() {
        return gravityMultiplier;
    }

    @Override
    public void setGravityMultiplier(double gravityMultiplier) {
        this.gravityMultiplier = gravityMultiplier;
    }
}
