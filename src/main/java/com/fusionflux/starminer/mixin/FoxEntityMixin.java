package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FoxEntity.class)
public class FoxEntityMixin {
    @ModifyVariable(method = "computeFallDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float diminishFallDamage(float value) {
        return value * (float)Math.sqrt(((EntityAttachments)this).getGravityMultiplier());
    }
}
