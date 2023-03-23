package com.fusionflux.starminer.block.entity;

import com.fusionflux.starminer.duck.EntityAttachments;
import com.fusionflux.starminer.optionslist.OptionsListBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

public abstract class AbstractStarCoreBlockEntity extends OptionsListBlockEntity {
    public AbstractStarCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Set<Direction> getEnabledDirections() {
        return EnumSet.allOf(Direction.class);
    }

    public abstract Box getRegionOfActivation();

    public abstract boolean doesItemMakeVisible(ItemStack stack);

    public void findNearbyEntities(World world) {
        world.getEntitiesByClass(Entity.class, getRegionOfActivation(), e -> true)
            .forEach(e -> ((EntityAttachments)e).getNearbyStarCores().put(this, 2));
    }
}
