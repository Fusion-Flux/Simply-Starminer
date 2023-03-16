package com.fusionflux.starminer.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.*;

@Mixin(RenderLayers.class)
final class RenderLayersMixin {
    @Inject(method = "method_23685", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getCutout()Lnet/minecraft/client/render/RenderLayer;"))
    private static void renderTypes(HashMap<Block, RenderLayer> map, CallbackInfo ci) {
        map.put(CREATIVE_STAR_CORE, RenderLayer.getTranslucent());
        map.put(STAR_CORE, RenderLayer.getTranslucent());
        map.put(STAR_BONE, RenderLayer.getTranslucent());
        map.put(STAR_JELLO, RenderLayer.getTranslucent());
    }
}
