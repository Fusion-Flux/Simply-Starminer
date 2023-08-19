package com.fusionflux.starminer;

import com.fusionflux.gravity_api.util.GravityChannel;
import com.fusionflux.starminer.client.*;
import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import com.fusionflux.starminer.registry.SimplyStarminerItems;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.poi.api.PointOfInterestHelper;
import org.slf4j.Logger;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.*;

public class SimplyStarminer implements ModInitializer {
    public static final String MOD_ID = "simply_starminer";
    public static final Logger LOGGER = LogUtils.getLogger();

    //public static final ItemGroup STARMINER_GROUP = QuiltItemGroup.createWithIcon(new Identifier(MOD_ID, "general"), () -> new ItemStack(STAR_CORE_ITEM));
    public static final ItemGroup STARMINER_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(STAR_CORE_ITEM)).name(Text.translatable("itemGroup.simply_starminer.general")).entries((params, collector) -> {
        collector.addItem(GRAVITY_ANCHOR_DOWN);
        collector.addItem(GRAVITY_ANCHOR_UP);
        collector.addItem(GRAVITY_ANCHOR_NORTH);
        collector.addItem(GRAVITY_ANCHOR_SOUTH);
        collector.addItem(GRAVITY_ANCHOR_EAST);
        collector.addItem(GRAVITY_ANCHOR_WEST);
        collector.addItem(CREATIVE_STAR_CORE_ITEM);
        collector.addItem(STAR_CORE_ITEM);
        collector.addItem(STAR_SURROUND_ITEM);
        collector.addItem(STAR_BONE_ITEM);
        collector.addItem(STAR_JELLO_ITEM);
        collector.addItem(STAR_FLESH_ITEM);
        collector.addItem(GRAVITY_PLATE_ITEM);
        collector.addItem(GRAVITY_SPYGLASS);
    }).build();
    public static final RegistryKey<PointOfInterestType> STAR_CORE_POI = PointOfInterestHelper.register(
        new Identifier(MOD_ID, "star_core"), 0, 1,
        SimplyStarminerBlocks.CREATIVE_STAR_CORE, SimplyStarminerBlocks.STAR_CORE
    );



    public static final Identifier OPTIONS_LIST_CONFIGURE = new Identifier(MOD_ID, "options_list_configure");

    @Override
    public void onInitialize(ModContainer mod) {
        SimplyStarminerBlocks.init();
        SimplyStarminerItems.init();
        SimplyStarminerBlockEntityTypes.init();

        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(GravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier::check);
        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(CoreGravityVerifier.FIELD_GRAVITY_SOURCE, CoreGravityVerifier::check);
        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(GravityAnchorVerifier.FIELD_GRAVITY_SOURCE, GravityAnchorVerifier::check);

        Registry.register(Registries.ITEM_GROUP, new Identifier("simply_starminer", "general"), STARMINER_GROUP);
        MidnightConfig.init(MOD_ID, SimplyStarminerConfig.class);
    }
}
