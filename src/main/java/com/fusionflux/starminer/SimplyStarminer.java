package com.fusionflux.starminer;

import com.fusionflux.gravity_api.util.GravityChannel;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import com.fusionflux.starminer.registry.SimplyStarminerItems;
import com.fusionflux.starminer.registry.SimplyStarminerScreenHandlers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.points_of_interest.api.PointOfInterestHelper;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.STAR_CORE_ITEM;

public class SimplyStarminer implements ModInitializer {
    public static final String MODID = "simply_starminer";

    public static final ItemGroup STARMINER_GROUP = QuiltItemGroup.createWithIcon(new Identifier(MODID, "general"), () -> new ItemStack(STAR_CORE_ITEM));

    public static final RegistryKey<PointOfInterestType> STAR_CORE_POI = PointOfInterestHelper.register(
        new Identifier(MODID, "star_core"), 0, 1,
        SimplyStarminerBlocks.CREATIVE_STAR_CORE, SimplyStarminerBlocks.STAR_CORE
    );

    @Override
    public void onInitialize(ModContainer mod) {
        SimplyStarminerBlocks.init();
        SimplyStarminerItems.init();
        SimplyStarminerScreenHandlers.init();
        SimplyStarminerBlockEntityTypes.init();
        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(GravityVerifier.FIELD_GRAVITY_SOURCE, GravityVerifier::check);
        GravityChannel.UPDATE_GRAVITY.getVerifierRegistry().register(CoreGravityVerifier.FIELD_GRAVITY_SOURCE, CoreGravityVerifier::check);
    }
}
