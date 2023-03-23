package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.05000000074505806))
    private double multiplyGravity(double constant) {
        return constant * ((EntityAttachments)this).getGravityMultiplier();
    }
}
