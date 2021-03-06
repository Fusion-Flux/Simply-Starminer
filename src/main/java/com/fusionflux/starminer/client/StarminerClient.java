package com.fusionflux.starminer.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class StarminerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_down_in_hand#inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_up_in_hand#inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_east_in_hand#inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_north_in_hand#inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_south_in_hand#inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier("simply_starminer:gravity_anchor_west_in_hand#inventory")));
    }
}
