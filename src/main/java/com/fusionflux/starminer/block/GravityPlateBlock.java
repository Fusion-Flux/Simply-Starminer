package com.fusionflux.starminer.block;

import com.fusionflux.starminer.duck.EntityAttachments;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import me.andrew.gravitychanger.api.GravityChangerAPI;
import me.andrew.gravitychanger.util.Gravity;
import me.andrew.gravitychanger.util.RotationUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class GravityPlateBlock extends Block {
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0D, 15D, 0.0D, 16.0D, 16D, 16.0D);
    public static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0D, 0.00D, 0.0D, 16.0D, 1D, 16.0D);
    public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.00D, 0.0D, 0.0D, 1D, 16.0D, 16.0D);
    public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
    public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.00D, 16.0D, 16.0D, 1.0D);
    public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 15D, 16.0D, 16.0D, 16D);
    public static final BiMap<Direction, BooleanProperty> dirToProperty = ImmutableBiMap.of(
            Direction.NORTH, Properties.NORTH,
            Direction.SOUTH, Properties.SOUTH,
            Direction.EAST, Properties.EAST,
            Direction.WEST, Properties.WEST,
            Direction.UP, Properties.UP,
            Direction.DOWN, Properties.DOWN
    );
    public final Map<BlockState, VoxelShape> stateToShape = this.stateManager.getStates().stream().collect(Collectors.toUnmodifiableMap(key -> key, GravityPlateBlock::getVoxelShape));

    public GravityPlateBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(NORTH, true/*this is so setblock doesn't make an invisible block*/).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    private static VoxelShape getVoxelShape(BlockState state) {
        VoxelShape shape = VoxelShapes.empty();
        if (state.get(UP)) {
            shape = UP_SHAPE;
        }
        if (state.get(DOWN)) {
            shape = VoxelShapes.union(shape, DOWN_SHAPE);
        }
        if (state.get(NORTH)) {
            shape = VoxelShapes.union(shape, SOUTH_SHAPE);
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapes.union(shape, NORTH_SHAPE);
        }
        if (state.get(EAST)) {
            shape = VoxelShapes.union(shape, WEST_SHAPE);
        }
        if (state.get(WEST)) {
            shape = VoxelShapes.union(shape, EAST_SHAPE);
        }
        return shape;
    }

    public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos);
        return Block.isFaceFullSquare(state.getCollisionShape(world, pos), direction.getOpposite());
    }

    private boolean hasAdjacentBlocks(BlockState state) {
        return this.getAdjacentBlockCount(state) > 0;
    }

    private int getAdjacentBlockCount(BlockState state) {
        return (int) dirToProperty.values().stream().filter(state::get).count();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.stateToShape.get(state);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        boolean alreadyExisting = state.isOf(this);
        BlockState stateBuilder = alreadyExisting ? state : this.getDefaultState().with(NORTH, false);

        for (Direction dir : ctx.getPlacementDirections()) {
            BooleanProperty property = dirToProperty.get(dir);
            boolean bl2 = alreadyExisting && state.get(property);
            if (!bl2 && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), dir)) {
                return stateBuilder.with(property, true);
            }
        }

        return alreadyExisting ? stateBuilder : null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.hasAdjacentBlocks(state);
    }

    private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
        if (shouldConnectTo(world, pos.offset(side), side)) {
            return true;
        } else if (side.getAxis() == Direction.Axis.Y) {
            return false;
        }
        return false;
    }

// lets see what removing this causes
//    private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
//        BlockState blockState = null;
//        Iterator<Direction> var6 = Direction.Type.HORIZONTAL.iterator();
//        // todo, get back to this
//        while (true) {
//            Direction dir;
//            BooleanProperty property;
//            do {
//                if (!var6.hasNext()) {
//                    return state;
//                }
//
//                dir = var6.next();
//                property = getFacingProperty(dir);
//            } while (!state.get(property));
//
//            boolean bl = this.shouldHaveSide(world, pos, dir);
//            if (!bl) {
//                if (blockState == null) {
//                    blockState = Blocks.AIR.getDefaultState();
//                }
//
//                bl = blockState.isOf(this) && blockState.get(property);
//            }
//
//            state = state.with(property, bl);
//        }
//    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isOf(this)) {
            return this.getAdjacentBlockCount(blockState) < dirToProperty.size();
        } else {
            return false;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BooleanProperty direction = dirToProperty.get(facing);
//        BlockState blockState = this.getPlacementShape(state, world, pos);
        return !this.hasAdjacentBlocks(state) ? Blocks.AIR.getDefaultState() : state.with(direction, false);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        this.addCollisionEffects(world, entity, pos, state);
    }


    public Vec3d getGravityFromState(BlockState state) {
        Vec3d result = Vec3d.ZERO;
        final Vec3d[] finalResult = {result}; // bruh
        BiMap<BooleanProperty, Direction> propertyToDir = dirToProperty.inverse();
        state.getProperties().stream().map(property -> ((BooleanProperty) property)).filter(property -> state.get(property) && !property.getName().equals("waterlogged")).map(property -> Vec3d.of(propertyToDir.get(property).getVector())).forEach(vec -> finalResult[0] = finalResult[0].add(vec));
        result = finalResult[0];
        if (state.get(Properties.SOUTH) && state.get(Properties.NORTH)) {
            result = result.add(0, 0, 2);
        }
        if (state.get(Properties.EAST) && state.get(Properties.WEST)) {
            result = result.add(2, 0, 0);
        }

//        if (state.get(Properties.UP) && state.get(Properties.DOWN)) { this probably shouldn't be here, if you're being squeezed you shouldn't fly up, todo, check this for me
//            result = result.add(0, 2, 0);
//        }

        return result;
    }

    /**
     * what. the. f**k.
     */
    private void addCollisionEffects(World world, Entity entity, BlockPos pos, BlockState state) {
        Vec3d vec3dLast = ((EntityAttachments) entity).getLastSSMVel();

        Vec3d direction = getGravityFromState(state);

        Vec3d preChange;

        direction = RotationUtil.vecWorldToPlayer(direction, GravityChangerAPI.getGravityDirection(entity));
        GravityChangerAPI.addGravity(entity, new Gravity(GravityChangerAPI.getGravityDirection(entity), 10, 30, "gravity_plate"));
        if (((EntityAttachments) entity).getPlateGravityTimer() == 0) {
            if (entity.verticalCollision) {
                if (direction.y == 1 || Math.abs(direction.y) == 2 && vec3dLast.getY() > 0) {
                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                    preChange = RotationUtil.vecPlayerToWorld(new Vec3d(0, 1, 0), GravityChangerAPI.getGravityDirection(entity));
                    GravityChangerAPI.addGravity(entity, new Gravity(Direction.fromVector((int) preChange.x, (int) preChange.y, (int) preChange.z), 10, 30, "gravity_plate"));
                }
            }
            if (entity.horizontalCollision) {
                if (direction.z == -1 || Math.abs(direction.z) == 2 && vec3dLast.getZ() < 0) {
                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                    preChange = RotationUtil.vecPlayerToWorld(new Vec3d(0, 0, -1), GravityChangerAPI.getGravityDirection(entity));
                    GravityChangerAPI.addGravity(entity, new Gravity(Direction.fromVector((int) preChange.x, (int) preChange.y, (int) preChange.z), 10, 30, "gravity_plate"));
                }
                if (direction.z == 1 || Math.abs(direction.z) == 2 && vec3dLast.getZ() > 0) {
                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                    preChange = RotationUtil.vecPlayerToWorld(new Vec3d(0, 0, 1), GravityChangerAPI.getGravityDirection(entity));
                    GravityChangerAPI.addGravity(entity, new Gravity(Direction.fromVector((int) preChange.x, (int) preChange.y, (int) preChange.z), 10, 30, "gravity_plate"));
                }
                if (direction.x == 1 || Math.abs(direction.x) == 2 && vec3dLast.getX() > 0) {
                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                    preChange = RotationUtil.vecPlayerToWorld(new Vec3d(1, 0, 0), GravityChangerAPI.getGravityDirection(entity));
                    GravityChangerAPI.addGravity(entity, new Gravity(Direction.fromVector((int) preChange.x, (int) preChange.y, (int) preChange.z), 10, 30, "gravity_plate"));
                }
                if (direction.x == -1 || Math.abs(direction.x) == 2 && vec3dLast.getX() < 0) {
                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                    preChange = RotationUtil.vecPlayerToWorld(new Vec3d(-1, 0, 0), GravityChangerAPI.getGravityDirection(entity));
                    GravityChangerAPI.addGravity(entity, new Gravity(Direction.fromVector((int) preChange.x, (int) preChange.y, (int) preChange.z), 10, 30, "gravity_plate"));
                }
            }
        }
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
