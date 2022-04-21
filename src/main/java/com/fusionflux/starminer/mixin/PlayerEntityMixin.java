package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.StarMinerRefabricated;
import com.fusionflux.starminer.util.EntityAttachments;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements EntityAttachments {

	int gravityTimer = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if(gravityTimer>0)
		gravityTimer --;
	}

	@Override
	public int getGravityTimer() {
		return this.gravityTimer;
	}

	@Override
	public void setGravityTimer(int gravityTimer) {
		this.gravityTimer = gravityTimer;
	}

}
