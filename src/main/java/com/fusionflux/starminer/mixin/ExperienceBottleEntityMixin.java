package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceBottleEntity.class)
public class ExperienceBottleEntityMixin {
    @ModifyReturnValue(method = "getGravity", at = @At("RETURN"))
    private float multiplyGravity(float original) {
        return original * (float)((EntityAttachments)this).getGravityMultiplier();
    }
}
