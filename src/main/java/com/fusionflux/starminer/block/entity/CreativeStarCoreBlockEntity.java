package com.fusionflux.starminer.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import static com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes.CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE;

public class CreativeStarCoreBlockEntity extends AbstractStarCoreBlockEntity {

    public CreativeStarCoreBlockEntity(BlockPos pos, BlockState state) {
        super(CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public int getRadius() {
        return 100;
    }
}
