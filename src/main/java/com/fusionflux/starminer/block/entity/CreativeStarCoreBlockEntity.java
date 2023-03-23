package com.fusionflux.starminer.block.entity;

import com.fusionflux.starminer.optionslist.OptionsListData;
import com.fusionflux.starminer.optionslist.OptionsListScreenHandler;
import com.fusionflux.starminer.registry.SimplyStarminerBlockEntityTypes;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public class CreativeStarCoreBlockEntity extends AbstractStarCoreBlockEntity implements ExtendedScreenHandlerFactory {
    @MidnightConfig.Entry(max = 342)
    private int radius = 100;

    @MidnightConfig.Entry
    private boolean enabledDown = true;

    @MidnightConfig.Entry
    private boolean enabledUp = true;

    @MidnightConfig.Entry
    private boolean enabledNorth = true;

    @MidnightConfig.Entry
    private boolean enabledSouth = true;

    @MidnightConfig.Entry
    private boolean enabledWest = true;

    @MidnightConfig.Entry
    private boolean enabledEast = true;

    public CreativeStarCoreBlockEntity(BlockPos pos, BlockState state) {
        super(SimplyStarminerBlockEntityTypes.CREATIVE_STAR_CORE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public Set<Direction> getEnabledDirections() {
        final Set<Direction> result = EnumSet.noneOf(Direction.class);
        if (enabledDown) {
            result.add(Direction.DOWN);
        }
        if (enabledUp) {
            result.add(Direction.UP);
        }
        if (enabledNorth) {
            result.add(Direction.NORTH);
        }
        if (enabledSouth) {
            result.add(Direction.SOUTH);
        }
        if (enabledWest) {
            result.add(Direction.WEST);
        }
        if (enabledEast) {
            result.add(Direction.EAST);
        }
        return result;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        OptionsListData.read(nbt, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        OptionsListData.write(nbt, this);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OptionsListScreenHandler(i, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return toNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }
}
