package com.fusionflux.starminer.block.entity;

import com.fusionflux.starminer.registry.SimplyStarminerBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes.STAR_CORE_BLOCK_ENTITY_TYPE;

public class StarCoreBlockEntity extends AbstractStarCoreBlockEntity {

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

        blockEntity.findNearbyEntities(world);
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

    @Override
    public int getRadius() {
        return Math.min(radius, 100);
    }
}
