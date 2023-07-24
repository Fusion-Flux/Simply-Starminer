package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.item.GravityAnchorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static com.fusionflux.starminer.SimplyStarminer.MOD_ID;
import static com.fusionflux.starminer.SimplyStarminer.STARMINER_GROUP;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.*;

@SuppressWarnings("unused")
public interface SimplyStarminerItems {
    Item GRAVITY_ANCHOR_DOWN = register(new Identifier(MOD_ID, "gravity_anchor_down"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.DOWN));
    Item GRAVITY_ANCHOR_UP = register(new Identifier(MOD_ID, "gravity_anchor_up"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.UP));
    Item GRAVITY_ANCHOR_NORTH = register(new Identifier(MOD_ID, "gravity_anchor_north"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.NORTH));
    Item GRAVITY_ANCHOR_SOUTH = register(new Identifier(MOD_ID, "gravity_anchor_south"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.SOUTH));
    Item GRAVITY_ANCHOR_WEST = register(new Identifier(MOD_ID, "gravity_anchor_west"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.WEST));
    Item GRAVITY_ANCHOR_EAST = register(new Identifier(MOD_ID, "gravity_anchor_east"), new GravityAnchorItem(new QuiltItemSettings().maxCount(1), Direction.EAST));
    Item CREATIVE_STAR_CORE_ITEM = register(new Identifier(MOD_ID, "creative_star_nuclei"), new BlockItem(CREATIVE_STAR_CORE, new QuiltItemSettings()));
    Item STAR_CORE_ITEM = register(new Identifier(MOD_ID, "star_heart"), new BlockItem(STAR_CORE, new QuiltItemSettings()));
    Item STAR_SURROUND_ITEM = register(new Identifier(MOD_ID, "star_veins"), new BlockItem(STAR_SURROUND, new QuiltItemSettings()));
    Item STAR_BONE_ITEM = register(new Identifier(MOD_ID, "star_bone"), new BlockItem(STAR_BONE, new QuiltItemSettings()));
    Item STAR_JELLO_ITEM = register(new Identifier(MOD_ID, "star_gelatin"), new BlockItem(STAR_JELLO, new QuiltItemSettings()));
    Item STAR_FLESH_ITEM = register(new Identifier(MOD_ID, "star_flesh"), new BlockItem(STAR_FLESH, new QuiltItemSettings()));
    Item GRAVITY_PLATE_ITEM = register(new Identifier(MOD_ID, "gravity_plate"), new BlockItem(GRAVITY_PLATE, new QuiltItemSettings()));
    Item GRAVITY_SPYGLASS = register(new Identifier(MOD_ID, "gravity_spyglass"), new Item(new QuiltItemSettings().maxCount(1)));

    static <T extends Item> T register(Identifier id, T entry) {
        return Registry.register(Registries.ITEM, id, entry);
    }

    static void init() {
    }
}
