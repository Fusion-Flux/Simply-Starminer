package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.item.GravityAnchorItem;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
final class ItemRendererMixin {
    @Shadow @Final
    private ItemModels models;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel scratch_guiModel(BakedModel defaultModel, ItemStack stack, ModelTransformation.Mode renderMode) {
        if (renderMode == ModelTransformation.Mode.GUI) {
            return defaultModel;
        }
        if (!(stack.getItem() instanceof GravityAnchorItem)) {
            return defaultModel;
        }
        return models.getModelManager().getModel(new ModelIdentifier(Registry.ITEM.getId(stack.getItem()) + "_in_hand#inventory"));
    }
}
