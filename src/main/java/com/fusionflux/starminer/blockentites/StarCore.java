package com.fusionflux.starminer.blockentites;

import com.fusionflux.starminer.SimplyStarMiner;
import com.fusionflux.starminer.screenhandlers.StarCoreScreenHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.fusionflux.starminer.registry.SimplyStarminerBlocks.STAR_CORE_ENTITY;

public class StarCore extends BlockWithEntity {
    public StarCore(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StarCoreEntity(pos,state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
                player.swingHand(hand);
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    buf.writeInt(((StarCoreEntity) world.getBlockEntity(pos)).radius);
                    return new LiteralText(new String(buf.array()));
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new StarCoreScreenHandler(syncId,inv,ScreenHandlerContext.create(world,pos));
                }
            });
            player.incrementStat(Stats.INTERACT_WITH_ANVIL);
            return ActionResult.CONSUME;
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

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
            return new AnvilScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
        }, new TranslatableText("Star Core"));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, STAR_CORE_ENTITY, StarCoreEntity::tick);
    }
}
