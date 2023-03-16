package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.block.entity.CreativeStarCoreBlockEntity;
import com.fusionflux.starminer.block.entity.StarCoreBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.CREATIVE_STAR_CORE;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE;

@SuppressWarnings("unused")
public interface SimplyStarminerBlockEntityTypes {
    Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES = new HashMap<>();

    BlockEntityType<StarCoreBlockEntity> STAR_CORE_BLOCK_ENTITY_TYPE = register(new Identifier(MODID, "star_core_entity"), FabricBlockEntityTypeBuilder.create(StarCoreBlockEntity::new, STAR_CORE).build(null));
    BlockEntityType<CreativeStarCoreBlockEntity> CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE = register(new Identifier(MODID, "creative_star_core_entity"), FabricBlockEntityTypeBuilder.create(CreativeStarCoreBlockEntity::new, CREATIVE_STAR_CORE).build(null));

    static <T extends BlockEntityType<?>> T register(Identifier id, T entry) {
        BLOCK_ENTITY_TYPES.put(id, entry);
        return entry;
    }

    static void init() {
        BLOCK_ENTITY_TYPES.forEach((id, entry) -> Registry.register(Registry.BLOCK_ENTITY_TYPE, id, entry));
    }
}
