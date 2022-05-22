package com.fusionflux.starminer.client;

import com.fusionflux.starminer.StarMinerRefabricated;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class StarminerClient implements ClientModInitializer {

    public static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(StarMinerRefabricated.STAR_CORE, RenderLayer.getTranslucent());
    }

    @Override
    public void onInitializeClient() {
registerBlockRenderLayers();
    }
}
