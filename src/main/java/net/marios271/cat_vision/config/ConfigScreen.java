package net.marios271.cat_vision.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
//? < 1.19 {
/*import net.minecraft.network.chat.TranslatableComponent;
*///?}

public class ConfigScreen {
	private static final String PREFIX = "text.cat_vision.config.";

	public static Screen create(Screen parent, ConfigData config) {
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(text(PREFIX + "title"));

		ConfigCategory category = builder.getOrCreateCategory(text(PREFIX + "category"));
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		category.addEntry(entryBuilder.startBooleanToggle(text(PREFIX + "option.remember_nv"), config.remember_nv)
			.setDefaultValue(true)
			.setSaveConsumer(v -> config.remember_nv = v)
			.build());

		category.addEntry(entryBuilder.startBooleanToggle(text(PREFIX + "option.auto_nv"), config.auto_nv)
			.setDefaultValue(true)
			.setSaveConsumer(v -> config.auto_nv = v)
			.build());

		category.addEntry(entryBuilder.startBooleanToggle(text(PREFIX + "option.blindness_immunity"), config.blindness_immunity)
			.setDefaultValue(true)
			.setSaveConsumer(v -> config.blindness_immunity = v)
			.setTooltip(
				text(PREFIX + "tooltip.blindness_disclaimer.1"),
				text(PREFIX + "tooltip.blindness_disclaimer.2"),
				text(PREFIX + "tooltip.blindness_disclaimer.3"),
				text(PREFIX + "tooltip.blindness_disclaimer.4")
			)
			.build());

		category.addEntry(entryBuilder.startBooleanToggle(text(PREFIX + "option.nausea_immunity"), config.nausea_immunity)
			.setDefaultValue(true)
			.setSaveConsumer(v -> config.nausea_immunity = v)
			.build());

		//? >= 1.19 {
		category.addEntry(entryBuilder.startBooleanToggle(text(PREFIX + "option.darkness_immunity"), config.darkness_immunity)
			.setDefaultValue(true)
			.setSaveConsumer(v -> config.darkness_immunity = v)
			.build());
		//?}

		builder.setSavingRunnable(config::save);

		return builder.build();
	}

	private static Component text(String key) {
		//? < 1.19 {
		/*return new TranslatableComponent(key);
		*///?} >= 1.19 {
		return Component.translatable(key);
		//?}
	}
}
