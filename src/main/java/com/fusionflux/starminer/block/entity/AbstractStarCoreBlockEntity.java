package com.fusionflux.starminer.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractStarCoreBlockEntity extends BlockEntity {
    public static final int SEARCH_RADIUS = 101;

    public AbstractStarCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getRadius();
}
