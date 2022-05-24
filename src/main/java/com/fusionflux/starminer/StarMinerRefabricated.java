package com.fusionflux.starminer;

import com.fusionflux.starminer.blockentites.StarCore;
import com.fusionflux.starminer.blockentites.StarCoreEntity;
import com.fusionflux.starminer.blocks.GravityPlate;
import com.fusionflux.starminer.items.GravityAnchor;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarMinerRefabricated implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final String MOD_ID = "simply_starminer";
	public static final StarCore STAR_CORE = new StarCore(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque());
	public static final ItemGroup StarminerGroup = QuiltItemGroup.createWithIcon(id("general"), () -> new ItemStack(STAR_CORE));
	public static final GravityPlate GRAVITY_PLATE = new GravityPlate(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK));
	public static BlockEntityType<StarCoreEntity> STAR_CORE_ENTITY;

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final Item GRAVITY_ANCHOR_DOWN = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.DOWN);
	public static final Item GRAVITY_ANCHOR_UP = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.UP);
	public static final Item GRAVITY_ANCHOR_NORTH = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.NORTH);
	public static final Item GRAVITY_ANCHOR_SOUTH = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.SOUTH);
	public static final Item GRAVITY_ANCHOR_WEST = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.WEST);
	public static final Item GRAVITY_ANCHOR_EAST = new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.EAST);


	@Override
	public void onInitialize(ModContainer mod) {
		STAR_CORE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(StarMinerRefabricated.MOD_ID, "redstone_randomizer_entity"), FabricBlockEntityTypeBuilder.create(StarCoreEntity::new, STAR_CORE).build(null));
		Registry.register(Registry.BLOCK, new Identifier(StarMinerRefabricated.MOD_ID, "star_core"), STAR_CORE);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "star_core"), new BlockItem(STAR_CORE, new QuiltItemSettings().group(StarminerGroup)));

		Registry.register(Registry.BLOCK, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_plate"), GRAVITY_PLATE);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_plate"), new BlockItem(GRAVITY_PLATE, new QuiltItemSettings().group(StarminerGroup)));

		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_down"), GRAVITY_ANCHOR_DOWN);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_up"), GRAVITY_ANCHOR_UP);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_north"), GRAVITY_ANCHOR_NORTH);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_south"), GRAVITY_ANCHOR_SOUTH);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_west"), GRAVITY_ANCHOR_WEST);
		Registry.register(Registry.ITEM, new Identifier(StarMinerRefabricated.MOD_ID, "gravity_anchor_east"), GRAVITY_ANCHOR_EAST);


		LOGGER.info("Hello Fabric world!");
	}
}
