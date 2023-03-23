package com.fusionflux.starminer.block.entity;

import com.fusionflux.starminer.duck.EntityAttachments;
import com.fusionflux.starminer.optionslist.OptionsListBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Set;

public abstract class AbstractStarCoreBlockEntity extends OptionsListBlockEntity {
    public AbstractStarCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getRadius();

    public abstract Set<Direction> getEnabledDirections();

    public void findNearbyEntities(World world) {
        world.getEntitiesByClass(Entity.class, new Box(pos).expand(getRadius()), e -> true)
            .forEach(e -> ((EntityAttachments)e).getNearbyStarCores().put(this, 2));
    }
}
