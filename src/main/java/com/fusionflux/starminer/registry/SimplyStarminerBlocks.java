package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.block.*;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;

@SuppressWarnings("unused")
public interface SimplyStarminerBlocks {
    Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    Block CREATIVE_STAR_CORE     = register(new Identifier(MODID, "creative_star_core"),     new CreativeStarCoreBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block STAR_CORE     = register(new Identifier(MODID, "star_heart"),     new StarCoreBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block GRAVITY_PLATE = register(new Identifier(MODID, "gravity_plate"), new GravityPlateBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)));
    Block STAR_SURROUND     = register(new Identifier(MODID, "star_veins"),     new Block(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block STAR_FLESH     = register(new Identifier(MODID, "star_flesh"),     new Block(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    TransparentBlockCustom STAR_BONE     = register(new Identifier(MODID, "star_bone"),     new TransparentBlockCustom(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));
    JelloBlock STAR_JELLO     = register(new Identifier(MODID, "star_jello"),     new JelloBlock(QuiltBlockSettings.copyOf(Blocks.HONEY_BLOCK).nonOpaque()));

    static <T extends Block> T register(Identifier id, T entry) {
        BLOCKS.put(id, entry);
        return entry;
    }

    static void init() {
        BLOCKS.forEach((id, entry) -> Registry.register(Registry.BLOCK, id, entry));
    }
}
