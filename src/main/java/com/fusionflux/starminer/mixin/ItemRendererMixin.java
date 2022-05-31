package com.fusionflux.starminer.mixin;

import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.*;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    private @Final
    ItemModels models;

    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    private BakedModel scratch_guiModel(BakedModel defaultModel, ItemStack stack,
                                        ModelTransformation.Mode renderMode)
    {
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_DOWN))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_down_in_hand#inventory"));
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_EAST))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_east_in_hand#inventory"));
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_NORTH))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_north_in_hand#inventory"));
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_SOUTH))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_south_in_hand#inventory"));
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_UP))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_up_in_hand#inventory"));
        if (renderMode != ModelTransformation.Mode.GUI && stack.isOf(GRAVITY_ANCHOR_WEST))
            return models.getModelManager().getModel(new ModelIdentifier("simply_starminer:gravity_anchor_west_in_hand#inventory"));
        return defaultModel;
    }
}
