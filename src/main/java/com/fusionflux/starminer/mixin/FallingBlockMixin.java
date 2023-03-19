package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.util.GeneralUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.FallingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
    @Redirect(
        method = "scheduledTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
        )
    )
    private BlockPos relativeToGravity(BlockPos instance, @Local ServerWorld world) {
        return instance.offset(GeneralUtil.getGravityForBlockPos(world, instance));
    }
}
