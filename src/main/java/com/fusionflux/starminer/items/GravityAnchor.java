package com.fusionflux.starminer.items;

import me.andrew.gravitychanger.api.GravityChangerAPI;
import me.andrew.gravitychanger.util.Gravity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GravityAnchor extends Item {

    public final Direction gravityDirection;

    public boolean isStrong = false;

    public boolean isOn = true;

    public GravityAnchor(Settings settings, Direction gravityDirection) {
        super(settings);
        this.gravityDirection = gravityDirection;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            if (!user.isSneaking()) {
                isStrong = !isStrong;
                if (isStrong) {
                    user.sendMessage(Text.of("Anchor Mode Set To Strong"), true);
                } else {
                    user.sendMessage(Text.of("Anchor Mode Set To Weak"), true);
                }
            } else {
                isOn = !isOn;
                if (isOn) {
                    user.sendMessage(Text.of("Anchor Enabled"), true);
                } else {
                    user.sendMessage(Text.of("Anchor Disabled"), true);
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isOn)
            if (isStrong) {
                GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 99, 2, "gravity_anchor"));
            } else {
                GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 0, 2, "gravity_anchor"));
            }
    }


}
