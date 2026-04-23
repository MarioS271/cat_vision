package net.marios271.cat_vision.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ConfigScreen {
    public static Screen create(Screen parent, ConfigData config) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(new TranslatableComponent("text.cat_vision.config.title"));

        ConfigCategory category = builder.getOrCreateCategory(new TranslatableComponent("text.cat_vision.config.category"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("text.cat_vision.config.option.remember_nv"), config.remember_nv)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.remember_nv = v)
            .build());

        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("text.cat_vision.config.option.auto_nv"), config.auto_nv)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.auto_nv = v)
            .build());

        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("text.cat_vision.config.option.blindness_immunity"), config.blindness_immunity)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.blindness_immunity = v)
            .build());

        category.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("text.cat_vision.config.option.nausea_immunity"), config.nausea_immunity)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.nausea_immunity = v)
            .build());

        builder.setSavingRunnable(config::save);

        return builder.build();
    }
}
