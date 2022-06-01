package com.fusionflux.starminer;

import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import com.fusionflux.starminer.registry.SimplyStarminerItems;
import com.fusionflux.starminer.registry.SimplyStarminerScreenHandlers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.STAR_CORE_ITEM;

public class SimplyStarMiner implements ModInitializer {
	public static final String MODID = "simply_starminer";
	public static final ItemGroup STARMINER_GROUP = QuiltItemGroup.createWithIcon(new Identifier(MODID, "general"), () -> new ItemStack(STAR_CORE_ITEM));

	@Override
	public void onInitialize(ModContainer mod) {
		SimplyStarminerBlocks.init();
		SimplyStarminerItems.init();
		SimplyStarminerScreenHandlers.init();
		SimplyStarminerBlockEntityTypes.init();
	}
}
