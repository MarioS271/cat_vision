package net.marios271.cat_vision.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
//? < 1.19 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.DoubleConsumer;
import java.util.function.Function;

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

		List<Runnable> afterSave = new ArrayList<>();
		for (AbstractConfigListEntry entry : nvEntries(entryBuilder, config, afterSave))
			category.addEntry(entry);

		builder.setSavingRunnable(() -> {
			for (Runnable task : afterSave)
				task.run();
			config.save();
		});

		return builder.build();
	}

	private static List<AbstractConfigListEntry> nvEntries(ConfigEntryBuilder entryBuilder, VisionSettings settings,
			List<Runnable> afterSave) {
		List<AbstractConfigListEntry> entries = new ArrayList<>();

		VisionSettings.Preset shown = VisionSettings.Preset.of(settings);
		VisionSettings.Preset[] chosen = { shown };

		entries.add(entryBuilder.startEnumSelector(text(PREFIX + "option.nv_preset"), VisionSettings.Preset.class, shown)
			.setDefaultValue(VisionSettings.Preset.of(new VisionSettings()))
			.setEnumNameProvider(preset -> text(PREFIX + "preset." + presetKey(preset.name())))
			.setTooltipSupplier(preset -> Optional.of(new Component[] {
				text(PREFIX + "tooltip.preset." + presetKey(preset.name()))
			}))
			.setSaveConsumer(preset -> chosen[0] = preset)
			.build());

		// Cloth saves the Advanced sliders after this entry, so applying the preset in its own
		// save consumer would be overwritten by whatever the sliders were still showing.
		afterSave.add(() -> {
			if (chosen[0] != shown)
				chosen[0].applyTo(settings);
		});

		entries.add(entryBuilder.startSubCategory(text(PREFIX + "option.advanced"), curveEntries(entryBuilder, settings))
			.setTooltip(
				text(PREFIX + "tooltip.advanced.1"),
				text(PREFIX + "tooltip.advanced.2")
			)
			.build());

		return entries;
	}

	private static String presetKey(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	private static List<AbstractConfigListEntry> curveEntries(ConfigEntryBuilder entryBuilder, VisionSettings settings) {
		List<AbstractConfigListEntry> entries = new ArrayList<>();

		entries.add(entryBuilder.startBooleanToggle(text(PREFIX + "option.nv_curve"), settings.nv_curve)
			.setDefaultValue(false)
			.setTooltip(
				text(PREFIX + "tooltip.nv_curve.1"),
				text(PREFIX + "tooltip.nv_curve.2"),
				text(PREFIX + "tooltip.nv_curve.3"),
				text(PREFIX + "tooltip.nv_curve.4")
			)
			.setSaveConsumer(v -> settings.nv_curve = v)
			.build());

		entries.add(slider(entryBuilder, "nv_lit", percent(settings.nv_lit), 0, 100, 0,
			v -> literal(v + "%"),
			v -> settings.nv_lit = v / 100.0,
			text(PREFIX + "tooltip.nv_lit.1"),
			text(PREFIX + "tooltip.nv_lit.2")));

		entries.add(slider(entryBuilder, "nv_dark", percent(settings.nv_dark), 0, 100, 100,
			v -> literal(v + "%"),
			v -> settings.nv_dark = v / 100.0,
			text(PREFIX + "tooltip.nv_dark.1"),
			text(PREFIX + "tooltip.nv_dark.2"),
			text(PREFIX + "tooltip.nv_dark.3")));

		entries.add(slider(entryBuilder, "nv_lit_light", settings.nv_lit_light, 0, 15, 12,
			v -> literal(String.valueOf(v)),
			v -> settings.nv_lit_light = (int) v,
			text(PREFIX + "tooltip.nv_lit_light")));

		entries.add(slider(entryBuilder, "nv_shape", (int) Math.round(settings.nv_shape * 10), 1, 50, 10,
			v -> literal(String.format(Locale.ROOT, "%.1f", v / 10.0)),
			v -> settings.nv_shape = v / 10.0,
			text(PREFIX + "tooltip.nv_shape.1"),
			text(PREFIX + "tooltip.nv_shape.2")));

		entries.add(slider(entryBuilder, "nv_speed", (int) Math.round(settings.nv_speed * 100), 1, 100, 1,
			v -> literal(String.format(Locale.ROOT, "%.2f", v / 100.0)),
			v -> settings.nv_speed = v / 100.0,
			text(PREFIX + "tooltip.nv_speed")));

		return entries;
	}

	private static AbstractConfigListEntry slider(ConfigEntryBuilder entryBuilder, String key, int value,
			int min, int max, int defaultValue, Function<Integer, Component> textGetter,
			DoubleConsumer save, Component... tooltip) {
		return entryBuilder.startIntSlider(text(PREFIX + "option." + key), value, min, max)
			.setDefaultValue(defaultValue)
			.setTextGetter(textGetter)
			.setTooltip(tooltip)
			.setSaveConsumer(v -> save.accept(v))
			.build();
	}

	private static int percent(double value) {
		return (int) Math.round(value * 100);
	}

	private static Component text(String key) {
		//? < 1.19 {
		/*return new TranslatableComponent(key);
		*///?} >= 1.19 {
		return Component.translatable(key);
		//?}
	}

	private static Component literal(String value) {
		//? < 1.19 {
		/*return new TextComponent(value);
		*///?} >= 1.19 {
		return Component.literal(value);
		//?}
	}
}
