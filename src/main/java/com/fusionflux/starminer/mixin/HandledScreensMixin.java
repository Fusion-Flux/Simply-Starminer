package com.fusionflux.starminer.mixin;

import com.fusionflux.starminer.client.screen.StarCoreScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static com.fusionflux.starminer.registry.SimplyStarminerScreenHandlers.STAR_CORE_SCREEN_HANDLER;

@Mixin(HandledScreens.class)
abstract class HandledScreensMixin {
    @Shadow
    public static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void register(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> provider) {}

    static {
        register(STAR_CORE_SCREEN_HANDLER, StarCoreScreen::new);
    }
}
