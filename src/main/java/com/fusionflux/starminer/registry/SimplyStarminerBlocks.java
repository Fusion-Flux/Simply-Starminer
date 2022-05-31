package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.blockentites.StarCore;
import com.fusionflux.starminer.blockentites.StarCoreEntity;
import com.fusionflux.starminer.blocks.GravityPlate;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;

public interface SimplyStarminerBlocks {
    StarCore STAR_CORE = Registry.register(Registry.BLOCK, new Identifier(MODID, "star_core"), new StarCore(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    BlockEntityType<StarCoreEntity> STAR_CORE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "redstone_randomizer_entity"), FabricBlockEntityTypeBuilder.create(StarCoreEntity::new, STAR_CORE).build(null));;

    GravityPlate GRAVITY_PLATE = Registry.register(Registry.BLOCK, new Identifier(MODID, "gravity_plate"), new GravityPlate(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)));

    static void init (){}
}
