package com.fusionflux.starminer.item;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityAnchorVerifier;
import com.fusionflux.starminer.client.GravityAnchorVerifierStrong;
import com.fusionflux.starminer.client.GravityVerifier;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GravityAnchorItem extends Item {
    public final Direction gravityDirection;
    public boolean isStrong = false;
    public boolean isOn = false;

    public GravityAnchorItem(Settings settings, Direction gravityDirection) {
        super(settings);
        this.gravityDirection = gravityDirection;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (!player.isSneaking()) {
            isStrong = !isStrong;
            if (isStrong) {
                player.sendMessage(Text.of("Anchor Mode Set To Strong"), true);
            } else {
                player.sendMessage(Text.of("Anchor Mode Set To Weak"), true);
            }
        } else {
            isOn = !isOn;
            if (isOn) {
                player.sendMessage(Text.of("Anchor Enabled"), true);
            } else {
                player.sendMessage(Text.of("Anchor Disabled"), true);
            }
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isOn) {
            PacketByteBuf infoSt = GravityAnchorVerifierStrong.packInfo(entity.getBlockPos());
            PacketByteBuf infoWe = GravityAnchorVerifier.packInfo(entity.getBlockPos());
            if (entity.getWorld().isClient && entity instanceof PlayerEntity) {
                if (isStrong) {
                    GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, GravityAnchorVerifierStrong.newFieldGravity(gravityDirection), GravityAnchorVerifierStrong.FIELD_GRAVITY_SOURCE, infoSt);
                } else {
                    GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, GravityAnchorVerifier.newFieldGravity(gravityDirection), GravityAnchorVerifier.FIELD_GRAVITY_SOURCE, infoWe);
                }
            } else {
                if (!(entity instanceof PlayerEntity) && !world.isClient) {
                    if (isStrong) {
                        GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 99, 2, "gravity_anchor"));
                    } else {
                        GravityChangerAPI.addGravity(entity, new Gravity(gravityDirection, 0, 2, "gravity_anchor"));
                    }
                }
            }

        }
    }
}
