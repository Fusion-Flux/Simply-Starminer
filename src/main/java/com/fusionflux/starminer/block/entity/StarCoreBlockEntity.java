package com.fusionflux.starminer.block.entity;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.starminer.client.CoreGravityVerifier;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.duck.EntityAttachments;
import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

import static com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes.STAR_CORE_BLOCK_ENTITY_TYPE;

public class StarCoreBlockEntity extends BlockEntity {


    public StarCoreBlockEntity(BlockPos pos, BlockState state) {
        super(STAR_CORE_BLOCK_ENTITY_TYPE, pos, state);
    }

    public int radius = 0;
    public int blockScanRadius = 1;

    public static void tick(World world, BlockPos pos, BlockState state, StarCoreBlockEntity blockEntity) {
        int testNum = 1 + (blockEntity.blockScanRadius * 2);
        int blockScanRadius = blockEntity.blockScanRadius;

        BlockPos start = pos.add(blockScanRadius, blockScanRadius, blockScanRadius);

        blockEntity.radius = 0;

        for (int x = 0; x < testNum; x++) {
            for (int y = 0; y < testNum; y++) {
                for (int z = 0; z < testNum; z++) {
                    if (world.getBlockState(start.add(-x, -y, -z)).equals(SimplyStarminerBlocks.STAR_SURROUND.getDefaultState())) {
                        blockEntity.radius++;
                    }
                }
            }
        }

        if (blockEntity.radius <= 18) {
            blockEntity.blockScanRadius = 1;
        } else if (blockEntity.radius <= 82) {
            blockEntity.blockScanRadius = 2;
        } else {
            blockEntity.blockScanRadius = 3;
        }


        Box innerBox = (new Box(new BlockPos(pos.getX(), pos.getY(), pos.getZ()))).expand(blockEntity.radius);

        List<Entity> list = world.getEntitiesByClass(Entity.class, innerBox, e -> true);
        PacketByteBuf info = GravityVerifier.packInfo(pos);

        for (Entity entity : list) {
            if (entity != null) {
                boolean dontrotate = false;

                if (entity instanceof PlayerEntity)
                    if (((PlayerEntity) entity).getAbilities().flying)
                        dontrotate = true;
                // GravityChangerAPI.addGravity(entity, new Gravity(GravityChangerAPI.getGravityDirection(entity), 5, 2, "star_heart"));
                if (!dontrotate) {
                    if (entity instanceof ClientPlayerEntity) {
                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(GravityChangerAPI.getGravityDirection(entity)), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                    } else {
                        if (!(entity instanceof PlayerEntity))
                            GravityChangerAPI.addGravity(entity, new Gravity(GravityChangerAPI.getGravityDirection(entity), 5, 2, "star_heart"));
                    }

                    if (((EntityAttachments) entity).getStarGravityTimer() <= 0) {
                        double offsetX = Math.abs(innerBox.getCenter().getX() - entity.getPos().getX());
                        double offsetY = Math.abs(innerBox.getCenter().getY() - entity.getPos().getY());
                        double offsetZ = Math.abs(innerBox.getCenter().getZ() - entity.getPos().getZ());
                        if (offsetY > offsetX + 2 && offsetY > offsetZ + 2) {
                            if (entity.getPos().getY() > pos.getY()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.DOWN) {
                                    //GravityChangerAPI.addGravity(entity, new Gravity(Direction.DOWN, 5, 2, "star_heart"));
                                    //((EntityAttachments) entity).setStarGravityTimer(10);
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.DOWN), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.DOWN, 5, 2, "star_heart"));
                                        }
                                    }
                                }
                            }
                            if (entity.getPos().getY() < pos.getY()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.UP) {
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.UP), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.UP, 5, 2, "star_heart"));
                                        }
                                    }
                                }
                            }
                        }
                        if (offsetZ > offsetY + 2 && offsetZ > offsetX + 2) {
                            if (entity.getPos().getZ() > pos.getZ()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.NORTH) {
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.NORTH), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.NORTH, 5, 2, "star_heart"));
                                        }
                                    }
                                }
                            }
                            if (entity.getPos().getZ() < pos.getZ()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.SOUTH) {
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.SOUTH), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.SOUTH, 5, 2, "star_heart"));
                                        }
                                    }
                                }
                            }
                        }
                        if (offsetX > offsetY + 2 && offsetX > offsetZ + 2) {
                            if (entity.getPos().getX() > pos.getX()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.WEST) {
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.WEST), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.WEST, 5, 2, "star_heart"));
                                        }
                                    }
                                }
                            }
                            if (entity.getPos().getX() < pos.getX()) {
                                if (GravityChangerAPI.getGravityDirection(entity) != Direction.EAST) {
                                    if (entity instanceof ClientPlayerEntity) {
                                        ((EntityAttachments) entity).setStarGravityTimer(10);
                                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(Direction.EAST), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                    } else {
                                        if (!(entity instanceof PlayerEntity)) {
                                            ((EntityAttachments) entity).setStarGravityTimer(10);
                                            GravityChangerAPI.addGravity(entity, new Gravity(Direction.EAST, 5, 2, "star_heart"));
                                        }
                                    }
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
        tag.putInt("blockscanradius", blockScanRadius);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("radius", NbtElement.INT_TYPE)) {
            radius = tag.getInt("radius");
        }
        if (tag.contains("blockscanradius", NbtElement.INT_TYPE)) {
            blockScanRadius = tag.getInt("blockscanradius");
        }
    }
}
