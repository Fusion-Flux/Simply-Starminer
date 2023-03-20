package com.fusionflux.starminer.client;

import com.fusionflux.starminer.SimplyStarminer;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class SimplyStarminerModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> MidnightConfig.getScreen(screen, SimplyStarminer.MOD_ID);
    }
}
