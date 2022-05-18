package com.fusionflux.starminer;

import com.fusionflux.starminer.blockentites.StarCore;
import com.fusionflux.starminer.blockentites.StarCoreEntity;
import com.fusionflux.starminer.blocks.GravityPlate;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarMinerRefabricated implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final String MOD_ID = "simply_starminer";
	public static final StarCore STAR_CORE = new StarCore(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK));
	public static final GravityPlate GRAVITY_PLATE = new GravityPlate(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK));
	public static BlockEntityType<StarCoreEntity> STAR_CORE_ENTITY;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		STAR_CORE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(StarMinerRefabricated.MOD_ID, "redstone_randomizer_entity"), FabricBlockEntityTypeBuilder.create(StarCoreEntity::new, STAR_CORE).build(null));
		Registry.register(Registry.BLOCK, new Identifier(StarMinerRefabricated.MOD_ID, "star_core"), STAR_CORE);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "star_core"), new BlockItem(STAR_CORE, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_plate"), GRAVITY_PLATE);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_plate"), new BlockItem(GRAVITY_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));

		LOGGER.info("Hello Fabric world!");
	}
}
