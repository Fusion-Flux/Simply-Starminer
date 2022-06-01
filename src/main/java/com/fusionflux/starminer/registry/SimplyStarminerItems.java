package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.item.GravityAnchorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;
import static com.fusionflux.starminer.SimplyStarMiner.STARMINER_GROUP;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.GRAVITY_PLATE;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE;

@SuppressWarnings("unused")
public interface SimplyStarminerItems {
    Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    Item GRAVITY_ANCHOR_DOWN  = register(new Identifier(MODID, "gravity_anchor_down"),  new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.DOWN));
    Item GRAVITY_ANCHOR_UP    = register(new Identifier(MODID, "gravity_anchor_up"),    new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.UP));
    Item GRAVITY_ANCHOR_NORTH = register(new Identifier(MODID, "gravity_anchor_north"), new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.NORTH));
    Item GRAVITY_ANCHOR_SOUTH = register(new Identifier(MODID, "gravity_anchor_south"), new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.SOUTH));
    Item GRAVITY_ANCHOR_WEST  = register(new Identifier(MODID, "gravity_anchor_west"),  new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.WEST));
    Item GRAVITY_ANCHOR_EAST  = register(new Identifier(MODID, "gravity_anchor_east"),  new GravityAnchorItem(new QuiltItemSettings().group(STARMINER_GROUP).maxCount(1), Direction.EAST));
    Item STAR_CORE_ITEM       = register(new Identifier(MODID, "star_core"),            new BlockItem(STAR_CORE, new QuiltItemSettings().group(STARMINER_GROUP)));
    Item GRAVITY_PLATE_ITEM   = register(new Identifier(MODID, "gravity_plate"),        new BlockItem(GRAVITY_PLATE, new QuiltItemSettings().group(STARMINER_GROUP)));

    static <T extends Item> T register(Identifier id, T entry) {
        ITEMS.put(id, entry);
        return entry;
    }

    static void init() {
        ITEMS.forEach((id, entry) -> Registry.register(Registry.ITEM, id, entry));
    }
}
