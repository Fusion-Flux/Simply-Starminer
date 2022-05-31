package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.items.GravityAnchor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;
import static com.fusionflux.starminer.SimplyStarMiner.StarminerGroup;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.GRAVITY_PLATE;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE;

public interface SimplyStarminerItems {
    Item GRAVITY_ANCHOR_DOWN = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_down"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.DOWN));
    Item GRAVITY_ANCHOR_UP = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_up"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.UP));
    Item GRAVITY_ANCHOR_NORTH = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_north"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.NORTH));
    Item GRAVITY_ANCHOR_SOUTH = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_south"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.SOUTH));
    Item GRAVITY_ANCHOR_WEST = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_west"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.WEST));
    Item GRAVITY_ANCHOR_EAST = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_anchor_east"), new GravityAnchor(new QuiltItemSettings().group(StarminerGroup).maxCount(1), Direction.EAST));

    Item STAR_CORE_ITEM = Registry.register(Registry.ITEM, new Identifier(MODID, "star_core"), new BlockItem(STAR_CORE, new QuiltItemSettings().group(StarminerGroup)));
    Item GRAVITY_PLATE_ITEM = Registry.register(Registry.ITEM, new Identifier(MODID, "gravity_plate"), new BlockItem(GRAVITY_PLATE, new QuiltItemSettings().group(StarminerGroup)));

    static void init (){}

}
