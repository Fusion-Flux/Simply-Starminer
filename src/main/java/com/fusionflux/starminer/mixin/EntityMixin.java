package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.util.EntityAttachments;
import me.andrew.gravitychanger.api.GravityChangerAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityAttachments {
	@Shadow public abstract Vec3d getVelocity();

	//
@Unique
private Vec3d lastSSMVel = Vec3d.ZERO;

	int gravityTimer = 0;
	int plateGravityTimer = 0;
	int remaningGravityTimer = 0;
	boolean swapgrav = false;

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		//if(gravityTimer>0)
		//gravityTimer --;



		if(gravityTimer>0) {
			gravityTimer--;
			swapgrav = false;
		}

		if(remaningGravityTimer>0) {
			remaningGravityTimer--;
			swapgrav = false;
		}

		if(plateGravityTimer>0) {
			plateGravityTimer--;
			swapgrav = false;
		}

		if(!swapgrav && remaningGravityTimer==0 && plateGravityTimer==0){
			GravityChangerAPI.setGravityDirection( (Entity) (Object)this, GravityChangerAPI.getDefaultGravityDirection((Entity) (Object)this));
			swapgrav =true;
		}
		this.lastSSMVel = this.getVelocity();
	}

	@Override
	public int getGravityTimer() {
		return this.gravityTimer;
	}

	@Override
	public void setGravityTimer(int gravityTimer) {
		this.gravityTimer = gravityTimer;
	}

	@Override
	public int getPlateGravityTimer() {
		return this.plateGravityTimer;
	}

	@Override
	public void setPlateGravityTimer(int gravityTimer) {
		this.plateGravityTimer = gravityTimer;
	}

	@Override
	public int getRemainingGravity() {
		return this.remaningGravityTimer;
	}

	@Override
	public void setRemainingGravity(int gravityTimer) {
		this.remaningGravityTimer = gravityTimer;
	}

	@Override
	public Vec3d getLastSSMVel() {
		return this.lastSSMVel;
	}
}
