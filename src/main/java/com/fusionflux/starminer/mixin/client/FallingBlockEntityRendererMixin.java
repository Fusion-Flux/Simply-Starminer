package com.fusionflux.starminer.mixin.client;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntityRenderer.class)
public class FallingBlockEntityRendererMixin {
    @Inject(
        method = "render(Lnet/minecraft/entity/FallingBlockEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At("HEAD")
    )
    private void fallingBlockEntityChanges(FallingBlockEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        final var animation = GravityChangerAPI.getGravityAnimation(fallingBlockEntity);
        if (animation.isPresent()) {
            matrixStack.pop(); // Pop the animation rotation matrix
            matrixStack.push(); // Make sure the stack still lines up
        }
    }
}
