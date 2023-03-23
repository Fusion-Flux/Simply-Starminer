package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import static com.fusionflux.starminer.SimplyStarminer.MOD_ID;

@SuppressWarnings("unused")
public interface SimplyStarminerBlocks {
    Block CREATIVE_STAR_CORE = register(new Identifier(MOD_ID, "creative_star_nuclei"), new CreativeStarCoreBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block STAR_CORE = register(new Identifier(MOD_ID, "star_heart"), new StarCoreBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block GRAVITY_PLATE = register(new Identifier(MOD_ID, "gravity_plate"), new GravityPlateBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)));
    Block STAR_SURROUND = register(new Identifier(MOD_ID, "star_veins"), new Block(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block STAR_FLESH = register(new Identifier(MOD_ID, "star_flesh"), new Block(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque().resistance(10000).strength(10000)));
    CustomTransparentBlock STAR_BONE = register(new Identifier(MOD_ID, "star_bone"), new CustomTransparentBlock(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque().resistance(10000).strength(10000)));
    JelloBlock STAR_JELLO = register(new Identifier(MOD_ID, "star_gelatin"), new JelloBlock(QuiltBlockSettings.copyOf(Blocks.HONEY_BLOCK).nonOpaque()));

    static <T extends Block> T register(Identifier id, T entry) {
        return Registry.register(Registry.BLOCK, id, entry);
    }

    static void init() {
    }
}
