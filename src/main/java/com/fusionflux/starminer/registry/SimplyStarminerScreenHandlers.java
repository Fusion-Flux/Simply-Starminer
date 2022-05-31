package com.fusionflux.starminer.registry;

import com.fusionflux.starminer.screenhandlers.StarCoreScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.fusionflux.starminer.SimplyStarMiner.MODID;

public interface SimplyStarminerScreenHandlers {
    ScreenHandlerType<StarCoreScreenHandler> STAR_CORE_SCREEN_HANDLER= Registry.register(Registry.SCREEN_HANDLER, new Identifier(MODID,"star_core"),new ScreenHandlerType<>(StarCoreScreenHandler::new));


    static void init (){}
}
