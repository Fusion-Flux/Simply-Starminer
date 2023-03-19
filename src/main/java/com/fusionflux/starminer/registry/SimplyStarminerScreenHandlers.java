package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.screen.StarCoreScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;

@SuppressWarnings("unused")
public interface SimplyStarminerScreenHandlers {
    ScreenHandlerType<StarCoreScreenHandler> STAR_CORE_SCREEN_HANDLER = register(new Identifier(MODID,"star_core"), new ScreenHandlerType<>(StarCoreScreenHandler::new));

    static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType<T> entry) {
        return Registry.register(Registry.SCREEN_HANDLER, id, entry);
    }

    static void init() {
    }
}
