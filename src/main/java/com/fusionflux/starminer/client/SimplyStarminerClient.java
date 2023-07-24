package com.fusionflux.starminer.client;

import com.fusionflux.starminer.SimplyStarminer;
import com.fusionflux.starminer.client.render.block.entity.StarCoreBlockEntityRenderer;
import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.ModelIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.*;

@ClientOnly
public class SimplyStarminerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_down_in_hand","inventory"));
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_up_in_hand","inventory"));
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_east_in_hand","inventory"));
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_north_in_hand","inventory"));
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_south_in_hand","inventory"));
            out.accept(new ModelIdentifier("simply_starminer","gravity_anchor_west_in_hand","inventory"));
        });

        BlockRenderLayerMap.put(RenderLayer.getTranslucent(), CREATIVE_STAR_CORE, STAR_CORE, STAR_BONE, STAR_JELLO);

        BlockEntityRendererFactories.register(SimplyStarminerBlockEntityTypes.STAR_CORE_BLOCK_ENTITY_TYPE, StarCoreBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(SimplyStarminerBlockEntityTypes.CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE, StarCoreBlockEntityRenderer::new);
    }
}
