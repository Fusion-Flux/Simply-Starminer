package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.util.EntityAttachments;
import me.andrew.gravitychanger.api.GravityChangerAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements EntityAttachments {
//
	int gravityTimer = 0;
	int remaningGravityTimer = 0;
	boolean swapgrav = false;

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if(gravityTimer>0)
		gravityTimer --;



		if(gravityTimer>0) {
			gravityTimer--;
			swapgrav = false;
		}

		if(remaningGravityTimer>0) {
			remaningGravityTimer--;
			swapgrav = false;
		}

		if(!swapgrav && remaningGravityTimer==0){
			GravityChangerAPI.setGravityDirection( (Entity) (Object)this, GravityChangerAPI.getDefaultGravityDirection((Entity) (Object)this));
			swapgrav =true;
		}

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
	public int getRemainingGravity() {
		return this.remaningGravityTimer;
	}

	@Override
	public void setRemainingGravity(int gravityTimer) {
		this.remaningGravityTimer = gravityTimer;
	}
}
