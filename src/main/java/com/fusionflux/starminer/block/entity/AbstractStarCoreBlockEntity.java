package com.fusionflux.starminer.block.entity;

import com.fusionflux.starminer.duck.EntityAttachments;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public abstract class AbstractStarCoreBlockEntity extends BlockEntity {
    public AbstractStarCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getRadius();

    public void findNearbyEntities(World world) {
        world.getEntitiesByClass(Entity.class, new Box(pos).expand(getRadius()), e -> true)
            .forEach(e -> ((EntityAttachments)e).getNearbyStarCores().put(pos, 2));
    }
}
