package com.fusionflux.starminer.block;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import com.fusionflux.gravity_api.util.RotationUtil;
import com.fusionflux.starminer.client.GravityVerifier;
import com.fusionflux.starminer.duck.EntityAttachments;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
    public static final BiMap<Direction, BooleanProperty> DIR_TO_PROPERTY = ImmutableBiMap.of(
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
        return (int) DIR_TO_PROPERTY.values().stream().filter(state::get).count();
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
            BooleanProperty property = DIR_TO_PROPERTY.get(dir);
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


    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isOf(this)) {
            return this.getAdjacentBlockCount(blockState) < DIR_TO_PROPERTY.size();
        } else {
            return false;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BooleanProperty direction = DIR_TO_PROPERTY.get(facing);
        return !this.hasAdjacentBlocks(state) ? Blocks.AIR.getDefaultState() : state.with(direction, false);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        this.addCollisionEffects(world, entity, pos, state);
    }

    /**
     * what. the. f**k.
     */
    private void addCollisionEffects(World world, Entity entity, BlockPos pos, BlockState state) {
        PacketByteBuf info = GravityVerifier.packInfo(pos);
        if ((entity.isOnGround() && entity.horizontalCollision) || (!entity.isOnGround() && entity.horizontalCollision) || (!entity.isOnGround() && !entity.horizontalCollision)) {
            if (((EntityAttachments) entity).getPlateGravityTimer() == 0) {
                Direction current = GravityChangerAPI.getGravityDirection(entity);

                double delta = -.9;
                ArrayList<Gravity> gravList = GravityChangerAPI.getGravityList(entity);
                for (Gravity grav : gravList) {
                    if (grav.source().equals("portalcubed:adhesion_gel")) {
                        delta = -.1;
                        break;
                    }
                }
                for (Direction direc : getDirections(state)) {
                    if (direc != current) {
                        Box gravbox = getGravityEffectBox(pos, direc, delta);
                        if (gravbox.intersects(entity.getBoundingBox())) {
                            if (world.isClient && entity instanceof PlayerEntity) {
                                GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, GravityVerifier.newFieldGravity(direc), GravityVerifier.FIELD_GRAVITY_SOURCE, info);
                                ((EntityAttachments) entity).setPlateGravityTimer(10);
                                break;
                            } else {
                                if (!(entity instanceof PlayerEntity) && !world.isClient) {
                                    GravityChangerAPI.addGravity(entity, new Gravity(direc, 10, 30, "adhesion_gel"));
                                    ((EntityAttachments) entity).setPlateGravityTimer(10);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (world.isClient && entity instanceof PlayerEntity) {
            GravityChangerAPI.addGravityClient((ClientPlayerEntity) entity, GravityVerifier.newFieldGravity(GravityChangerAPI.getGravityDirection(entity)), GravityVerifier.FIELD_GRAVITY_SOURCE, info);
        } else {
            if (!(entity instanceof PlayerEntity) && !world.isClient)
                GravityChangerAPI.addGravity(entity, new Gravity(GravityChangerAPI.getGravityDirection(entity), 10, 30, "adhesion_gel"));
        }
    }

    public static ArrayList<Direction> getDirections(BlockState blockState) {
        ArrayList<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (blockState.get(GravityPlateBlock.DIR_TO_PROPERTY.get(direction))) {
                list.add(direction);
            }
        }
        return list;
    }

    public Box getGravityEffectBox(BlockPos blockPos, Direction direction, double delta) {
        double minX = blockPos.getX();
        double minY = blockPos.getY();
        double minZ = blockPos.getZ();
        double maxX = blockPos.getX() + 1;
        double maxY = blockPos.getY() + 1;
        double maxZ = blockPos.getZ() + 1;
        switch (direction) {
            case DOWN -> maxY += delta;
            case UP -> minY -= delta;
            case NORTH -> maxZ += delta;
            case SOUTH -> minZ -= delta;
            case WEST -> maxX += delta;
            case EAST -> minX -= delta;
        }
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public Box getPlayerBox(Box playerBox, Direction direction, double delta) {
        double minX = playerBox.minX;
        double minY = playerBox.minY;
        double minZ = playerBox.minZ;
        double maxX = playerBox.maxX;
        double maxY = playerBox.maxY;
        double maxZ = playerBox.maxZ;
        switch (direction) {
            case DOWN -> maxY += delta;
            case UP -> minY -= delta;
            case NORTH -> maxZ += delta;
            case SOUTH -> minZ -= delta;
            case WEST -> maxX += delta;
            case EAST -> minX -= delta;
        }
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
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
