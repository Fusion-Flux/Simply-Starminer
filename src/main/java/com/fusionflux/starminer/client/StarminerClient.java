package com.fusionflux.starminer.client;

import com.fusionflux.starminer.StarMinerRefabricated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.json.ModelTransformation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class StarminerClient implements ClientModInitializer {

    public static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(StarMinerRefabricated.STAR_CORE, RenderLayer.getTranslucent());
    }

    @Override
    public void onInitializeClient(ModContainer mod) {
        registerBlockRenderLayers();
        BuiltinItemRendererRegistry.register(StarMinerRefabricated.GRAVITY_ANCHOR_DOWN, (stack, mode, matrices, vertexConsumers, light, overlay)->{
            if (mode == ModelTransformation.Mode.GUI) {
// do thing
            } else {
// do other thing
            }
        });
    }
}
