package com.fusionflux.starminer;

import com.fusionflux.gravity_api.util.GravityChannel;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.optionslist.OptionsListBlockEntity;
import com.fusionflux.starminer.optionslist.OptionsListData;
import com.fusionflux.starminer.optionslist.OptionsListScreenHandler;
import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import com.fusionflux.starminer.registry.SimplyStarminerItems;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.points_of_interest.api.PointOfInterestHelper;
import org.slf4j.Logger;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.STAR_CORE_ITEM;

public class SimplyStarminer implements ModInitializer {
    public static final String MOD_ID = "simply_starminer";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ItemGroup STARMINER_GROUP = QuiltItemGroup.createWithIcon(new Identifier(MOD_ID, "general"), () -> new ItemStack(STAR_CORE_ITEM));

    public static final RegistryKey<PointOfInterestType> STAR_CORE_POI = PointOfInterestHelper.register(
        new Identifier(MOD_ID, "star_core"), 0, 1,
        SimplyStarminerBlocks.CREATIVE_STAR_CORE, SimplyStarminerBlocks.STAR_CORE
    );

    public static final ScreenHandlerType<OptionsListScreenHandler> OPTIONS_LIST_SCREEN_HANDLER = Registry.register(
        Registry.SCREEN_HANDLER, new Identifier(MOD_ID, "options_list"),
        new ExtendedScreenHandlerType<>(OptionsListScreenHandler::new)
    );

    public static final Identifier OPTIONS_LIST_CONFIGURE = new Identifier(MOD_ID, "options_list_configure");

    @Override
    public void onInitialize(ModContainer mod) {
        SimplyStarminerBlocks.init();
        SimplyStarminerItems.init();
        SimplyStarminerBlockEntityTypes.init();

        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(GravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier::check);
        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(CoreGravityVerifier.FIELD_GRAVITY_SOURCE, CoreGravityVerifier::check);

        ServerPlayNetworking.registerGlobalReceiver(OPTIONS_LIST_CONFIGURE, (server, player, handler, buf, responseSender) -> {
            final BlockPos origin = buf.readBlockPos();
            final String json = buf.readString();
            server.execute(() -> {
                if (!(player.getWorld().getBlockEntity(origin) instanceof OptionsListBlockEntity optionsListBlockEntity) || !player.isCreative()) {
                    return;
                }
                OptionsListData.read(json, optionsListBlockEntity);
                optionsListBlockEntity.updateListeners();
            });
        });

        MidnightConfig.init(MOD_ID, SimplyStarminerConfig.class);
    }
}
