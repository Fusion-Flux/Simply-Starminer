package com.fusionflux.starminer.block;

import com.fusionflux.starminer.block.entity.CreativeStarCoreBlockEntity;
import com.fusionflux.starminer.block.entity.StarCoreBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes.CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE;
import static com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes.STAR_CORE_BLOCK_ENTITY_TYPE;

@SuppressWarnings("deprecation")
public class CreativeStarCoreBlock extends BlockWithEntity {
    public CreativeStarCoreBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CreativeStarCoreBlockEntity(pos,state);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE, CreativeStarCoreBlockEntity::tick);
    }
}
