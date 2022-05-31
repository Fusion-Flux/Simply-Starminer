package com.fusionflux.starminer.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.ModelIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE;

@Environment(EnvType.CLIENT)
public class StarminerClient implements ClientModInitializer {

    public static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(STAR_CORE, RenderLayer.getTranslucent());
    }

    @Override
    public void onInitializeClient(ModContainer mod) {
        registerBlockRenderLayers();
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_down_in_hand#inventory"));
        });
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_up_in_hand#inventory"));
        });
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_east_in_hand#inventory"));
        });
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_north_in_hand#inventory"));
        });
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_south_in_hand#inventory"));
        });
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_west_in_hand#inventory"));
        });

    }
}
