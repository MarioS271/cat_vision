package net.marios271.cat_vision.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigScreen;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ConfigScreen.create(parent, CatVision.CONFIG);
    }
}
