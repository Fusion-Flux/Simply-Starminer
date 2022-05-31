package com.fusionflux.starminer;

import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import com.fusionflux.starminer.registry.SimplyStarminerItems;
import com.fusionflux.starminer.registry.SimplyStarminerScreenHandlers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fusionflux.starminer.registry.SimplyStarminerItems.STAR_CORE_ITEM;

public class SimplyStarMiner implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final String MODID = "simply_starminer";




	public static final ItemGroup StarminerGroup = QuiltItemGroup.createWithIcon(id("general"), () -> new ItemStack(STAR_CORE_ITEM));

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}


	@Override
	public void onInitialize(ModContainer mod) {
		SimplyStarminerBlocks.init();
		SimplyStarminerItems.init();
		SimplyStarminerScreenHandlers.init();

		LOGGER.info("Hello Fabric world!");
	}
}
