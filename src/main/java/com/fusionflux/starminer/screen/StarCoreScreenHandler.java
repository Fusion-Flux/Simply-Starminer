package com.fusionflux.starminer.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

import static com.fusionflux.starminer.registry.SimplyStarminerScreenHandlers.STAR_CORE_SCREEN_HANDLER;

public class StarCoreScreenHandler extends ScreenHandler {
    public StarCoreScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId,playerInventory, ScreenHandlerContext.EMPTY);
    }

    public StarCoreScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(STAR_CORE_SCREEN_HANDLER,syncId);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlot(new Slot(playerInventory,j+i*9+9,8+j*18,72+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory,i,8+i*18,130));
        }

    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}


//amongus