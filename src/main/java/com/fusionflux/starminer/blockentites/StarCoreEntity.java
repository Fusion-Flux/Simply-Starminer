package com.fusionflux.starminer.blockentites;

import com.fusionflux.starminer.SimplyStarMiner;
import com.fusionflux.starminer.util.EntityAttachments;
import me.andrew.gravitychanger.api.GravityChangerAPI;
import me.andrew.gravitychanger.util.Gravity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class StarCoreEntity extends BlockEntity {


    public StarCoreEntity(BlockPos pos, BlockState state) {
        super(SimplyStarMiner.STAR_CORE_ENTITY, pos, state);
    }

    int radius = 20;

    public static void tick(World world, BlockPos pos, BlockState state, StarCoreEntity blockEntity) {
            Box innerBox = (new Box(new BlockPos(pos.getX(), pos.getY(), pos.getZ()))).expand(blockEntity.radius);

            List<Entity> list = world.getEntitiesByClass(Entity.class, innerBox, e -> true);

            for (Entity entity : list) {
                if (entity != null) {




                       boolean dontrotate = false;

                       //if(((EntityAttachments) entity).getPlateGravityTimer() != 0){
                       //    ((EntityAttachments) entity).setRemainingGravity(2);
                       //}

                       if (entity instanceof PlayerEntity)
                           if (((PlayerEntity) entity).getAbilities().flying)
                               dontrotate = true;
                       if (!dontrotate && !world.isClient) {
                           //listb.remove(entity);
                           GravityChangerAPI.addGravity(entity, new Gravity(GravityChangerAPI.getGravityDirection(entity), 5, 2, "star_core"));
                           if (((EntityAttachments) entity).getStarGravityTimer() <= 0) {
                               double offsetX = Math.abs(innerBox.getCenter().getX() - entity.getPos().getX());
                               double offsetY = Math.abs(innerBox.getCenter().getY() - entity.getPos().getY());
                               double offsetZ = Math.abs(innerBox.getCenter().getZ() - entity.getPos().getZ());
                               if (offsetY > offsetX + 2 && offsetY > offsetZ + 2) {
                                   if (entity.getPos().getY() > pos.getY()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.DOWN) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.DOWN, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                                   if (entity.getPos().getY() < pos.getY()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.UP) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.UP, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                               }
                               if (offsetZ > offsetY + 2 && offsetZ > offsetX + 2) {
                                   if (entity.getPos().getZ() > pos.getZ()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.NORTH) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.NORTH, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                                   if (entity.getPos().getZ() < pos.getZ()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.SOUTH) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.SOUTH, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                               }

                               if (offsetX > offsetY + 2 && offsetX > offsetZ + 2) {
                                   if (entity.getPos().getX() > pos.getX()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.WEST) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.WEST, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                                   if (entity.getPos().getX() < pos.getX()) {
                                       if (GravityChangerAPI.getGravityDirection(entity) != Direction.EAST) {
                                           GravityChangerAPI.addGravity(entity, new Gravity(Direction.EAST, 5, 2, "star_core"));
                                           ((EntityAttachments) entity).setStarGravityTimer(10);
                                       }
                                   }
                               }
                           }
                       }
                }
            }
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("radius", radius);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("radius", NbtElement.INT_TYPE)) {
            radius = tag.getInt("radius");
        }
    }
}
