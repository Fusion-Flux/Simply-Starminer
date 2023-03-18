package com.fusionflux.starminer.mixin;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
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

    @Shadow public World world;

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract Vec3d getPos();

    @Shadow public abstract Vec3d getEyePos();

    int gravityPlateTimer = 0;
    int gravityStarTimer = 0;

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

        if ((Object)this instanceof PlayerEntity player && player.getAbilities().flying) return;

        AbstractStarCoreBlockEntity closest = null;
        double closestDist = Double.POSITIVE_INFINITY;
        for (
            int cx = (int)Math.floor((getX() - AbstractStarCoreBlockEntity.SEARCH_RADIUS) / 16) - 1,
                ex = (int)Math.ceil((getX() + AbstractStarCoreBlockEntity.SEARCH_RADIUS) / 16) + 1;
            cx <= ex; cx++
        ) {
            for (
                int cz = (int)Math.floor((getZ() - AbstractStarCoreBlockEntity.SEARCH_RADIUS) / 16) - 1,
                ez = (int)Math.ceil((getZ() + AbstractStarCoreBlockEntity.SEARCH_RADIUS) / 16) + 1;
                cz <= ez; cz++
            ) {
                if (!world.isChunkLoaded(cx, cz)) continue;
                for (final BlockEntity entity : world.getChunk(cx, cz).getBlockEntities().values()) {
                    if (!(entity instanceof AbstractStarCoreBlockEntity core)) continue;
                    final double distance = getPos().distanceTo(Vec3d.ofCenter(entity.getPos()));
                    if (distance <= core.getRadius() && distance < closestDist) {
                        closest = core;
                        closestDist = distance;
                    }
                }
            }
        }

        if (closest == null) return;
        final double relX = closest.getPos().getX() + 0.5 - getEyePos().getX();
        final double relY = closest.getPos().getY() + 0.5 - getEyePos().getY();
        final double relZ = closest.getPos().getZ() + 0.5 - getEyePos().getZ();

        final Direction direction;
        if (Math.abs(relX) > Math.abs(relY) && Math.abs(relX) > Math.abs(relZ)) {
            direction = relX < 0 ? Direction.WEST : Direction.EAST;
        } else if (Math.abs(relY) > Math.abs(relZ) && Math.abs(relY) > Math.abs(relX)) {
            direction = relY < 0 ? Direction.DOWN : Direction.UP;
        } else {
            direction = relZ < 0 ? Direction.NORTH : Direction.SOUTH;
        }

        if (world.isClient && (Object)this instanceof PlayerEntity) {
            addGravityClient(CoreGravityVerifier.newFieldGravity(direction), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier.packInfo(closest.getPos()));
        } else if (!((Object)this instanceof PlayerEntity) && !world.isClient) {
            GravityChangerAPI.addGravity((Entity)(Object)this, new Gravity(direction, 5, 2, "star_heart"));
        }
    }

    @ClientOnly
    @SuppressWarnings("DataFlowIssue")
    private void addGravityClient(Gravity gravity, Identifier verifier, PacketByteBuf verifierInfo) {
        GravityChangerAPI.addGravityClient((ClientPlayerEntity)(Object)this, gravity, verifier, verifierInfo);
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
