package com.fusionflux.starminer.client.render.block.entity;

import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class StarCoreBlockEntityRenderer implements BlockEntityRenderer<AbstractStarCoreBlockEntity> {
    public StarCoreBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(AbstractStarCoreBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        if ((!(client.cameraEntity instanceof LivingEntity living) || !living.isHolding(entity::doesItemMakeVisible)) &&
            !client.player.isCreative() && !client.player.isSpectator()
        ) return;
        WorldRenderer.drawBox(
            matrices, vertexConsumers.getBuffer(RenderLayer.getLines()),
            entity.getRegionOfActivation().offset(entity.getPos().multiply(-1)),
            0.5f, 0.5f, 1.0f, 1.0f
        );
    }

    @Override
    public boolean rendersOutsideBoundingBox(AbstractStarCoreBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return 343;
    }
}
