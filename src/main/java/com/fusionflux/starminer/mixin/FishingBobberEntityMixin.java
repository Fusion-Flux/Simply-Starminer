package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.03))
    private double multiplyGravity(double constant) {
        return constant * ((EntityAttachments)this).getGravityMultiplier();
    }
}
