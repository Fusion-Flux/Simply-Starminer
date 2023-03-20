package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.block.entity.CreativeStarCoreBlockEntity;
import com.fusionflux.starminer.block.entity.StarCoreBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import static com.fusionflux.starminer.SimplyStarminer.MODID;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.CREATIVE_STAR_CORE;
import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE;

@SuppressWarnings("unused")
public interface SimplyStarminerBlockEntityTypes {
    BlockEntityType<StarCoreBlockEntity> STAR_CORE_BLOCK_ENTITY_TYPE = register(new Identifier(MODID, "star_core_entity"), QuiltBlockEntityTypeBuilder.create(StarCoreBlockEntity::new, STAR_CORE).build(null));
    BlockEntityType<CreativeStarCoreBlockEntity> CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE = register(new Identifier(MODID, "creative_star_core_entity"), QuiltBlockEntityTypeBuilder.create(CreativeStarCoreBlockEntity::new, CREATIVE_STAR_CORE).build(null));

    static <T extends BlockEntityType<?>> T register(Identifier id, T entry) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, entry);
    }

    static void init() {
    }
}
