package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.block.GravityPlateBlock;
import com.fusionflux.starminer.block.StarCoreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;

@SuppressWarnings("unused")
public interface SimplyStarminerBlocks {
    Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    Block STAR_CORE     = register(new Identifier(MODID, "star_core"),     new StarCoreBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK).nonOpaque()));
    Block GRAVITY_PLATE = register(new Identifier(MODID, "gravity_plate"), new GravityPlateBlock(QuiltBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)));

    static <T extends Block> T register(Identifier id, T entry) {
        BLOCKS.put(id, entry);
        return entry;
    }

    static void init() {
        BLOCKS.forEach((id, entry) -> Registry.register(Registry.BLOCK, id, entry));
    }
}
