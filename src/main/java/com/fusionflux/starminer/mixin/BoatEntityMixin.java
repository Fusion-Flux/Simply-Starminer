package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BoatEntity.class)
public class BoatEntityMixin {
    @ModifyConstant(method = "updateVelocity", constant = @Constant(doubleValue = -0.03999999910593033))
    private double multiplyGravity(double constant) {
        return constant * ((EntityAttachments)this).getGravityMultiplier();
    }
}
