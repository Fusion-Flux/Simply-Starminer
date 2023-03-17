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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
                if (!dontrotate) {
                    double x = innerBox.getCenter().getX() - entity.getEyePos().getX();
                    double y = innerBox.getCenter().getY() - entity.getEyePos().getY();
                    double z = innerBox.getCenter().getZ() - entity.getEyePos().getZ();
                    Direction d;
                    if (Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z))
                        d = x < 0 ? Direction.WEST : Direction.EAST;
                    else if (Math.abs(y) > Math.abs(z) && Math.abs(y) > Math.abs(x))
                        d = y < 0 ? Direction.DOWN : Direction.UP;
                    else d = z < 0 ? Direction.NORTH : Direction.SOUTH;
                    if (world.isClient && entity instanceof PlayerEntity) {
                        GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, CoreGravityVerifier.newFieldGravity(d), CoreGravityVerifier.FIELD_GRAVITY_SOURCE, info);
                    } else {
                        if (!(entity instanceof PlayerEntity) && !world.isClient) {
                            GravityChangerAPI.addGravity(entity, new Gravity(d, 5, 2, "star_heart"));
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
