package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    private double multiplyGravity(double constant) {
        return constant * ((EntityAttachments)this).getGravityMultiplier();
    }

    @ModifyVariable(method = "computeFallDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float diminishFallDamage(float value) {
        return value * (float)Math.sqrt(((EntityAttachments)this).getGravityMultiplier());
    }
}
