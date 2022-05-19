package com.fusionflux.starminer.items;

import me.andrew.gravitychanger.api.GravityChangerAPI;
import me.andrew.gravitychanger.util.Gravity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GravityAnchor extends Item {

    public final Direction gravityDirection;

    public final boolean isStrong = true;

    public GravityAnchor(Settings settings, Direction gravityDirection) {
        super(settings);
        this.gravityDirection = gravityDirection;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(isStrong) {
            GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 99, 2, "gravity_anchor"));
        }else{
            GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 0, 2, "gravity_anchor"));
        }
        }


}
