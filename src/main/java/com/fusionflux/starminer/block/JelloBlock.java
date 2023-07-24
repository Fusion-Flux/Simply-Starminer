package com.fusionflux.starminer.block;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.*;
@SuppressWarnings("deprecation")
public class JelloBlock extends TransparentBlock {
    private static final double field_31101 = 0.13D;
    private static final double field_31102 = 0.08D;
    private static final double field_31103 = 0.05D;
    private static final int TICKS_PER_SECOND = 20;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public JelloBlock(Settings settings) {
        super(settings);
    }

    private static boolean hasHoneyBlockEffects(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof AbstractMinecartEntity || entity instanceof TntEntity || entity instanceof BoatEntity;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
            addParticles(entity,10);

        if (entity.handleFallDamage(fallDistance, 0.2F, world.getDamageSources().fall())) {
            entity.playSound(this.soundGroup.getFallSound(), this.soundGroup.getVolume() * 0.5F, this.soundGroup.getPitch() * 0.75F);
        }

    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (this.isSliding(pos, entity)) {
            this.triggerAdvancement(entity, pos);
            this.updateSlidingVelocity(entity);
            this.addCollisionEffects(world, entity);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    private boolean isSliding(BlockPos pos, Entity entity) {
        if (entity.isOnGround()) {
            return false;
        } else if (entity.getY() > (double)pos.getY() + 0.9375D - 1.0E-7D) {
            return false;
        } else if (entity.getVelocity().y >= -0.08D) {
            return false;
        } else {
            double d = Math.abs((double)pos.getX() + 0.5D - entity.getX());
            double e = Math.abs((double)pos.getZ() + 0.5D - entity.getZ());
            double f = 0.4375D + (double)(entity.getWidth() / 2.0F);
            return d + 1.0E-7D > f || e + 1.0E-7D > f;
        }
    }

    private void triggerAdvancement(Entity entity, BlockPos pos) {
        if (entity instanceof ServerPlayerEntity && entity.getWorld().getTime() % 20L == 0L) {
            Criteria.SLIDE_DOWN_BLOCK.trigger((ServerPlayerEntity)entity, entity.getWorld().getBlockState(pos));
        }

    }

    private void updateSlidingVelocity(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < -0.13D) {
            double d = -0.05D / vec3d.y;
            entity.setVelocity(new Vec3d(vec3d.x * d, -0.05D, vec3d.z * d));
        } else {
            entity.setVelocity(new Vec3d(vec3d.x, -0.05D, vec3d.z));
        }

        entity.resetFallDistance();
    }

    private void addCollisionEffects(World world, Entity entity) {
        if (hasHoneyBlockEffects(entity)) {
            if (world.random.nextInt(5) == 0) {
                entity.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
            }

            if (world.random.nextInt(5) == 0) {
                addParticles(entity,5);
            }
        }

    }

    public static void addRegularParticles(Entity entity) {
        addParticles(entity, 5);
    }

    public static void addRichParticles(Entity entity) {
        addParticles(entity, 10);
    }

    private static void addParticles(Entity entity, int count) {
        if (entity.getWorld().isClient) {
            BlockState blockState = STAR_JELLO.getDefaultState();

            for(int i = 0; i < count; ++i) {
                entity.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), entity.getX(), entity.getY(), entity.getZ(), 0.0D, 0.0D, 0.0D);
            }

        }
    }
}