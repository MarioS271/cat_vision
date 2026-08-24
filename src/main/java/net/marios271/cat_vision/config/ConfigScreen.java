package net.marios271.cat_vision.config;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.marios271.cat_vision.handler.VisionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
//? < 1.19 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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

		category.addEntry(autoNvEntry(entryBuilder, config));

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

		for (AbstractConfigListEntry entry : curveEntries(entryBuilder, config))
			category.addEntry(entry);

		List<Runnable> afterSave = new ArrayList<>();
		addDimensionEntries(category, entryBuilder, config, afterSave);

		builder.setSavingRunnable(() -> {
			for (Runnable task : afterSave)
				task.run();
			config.save();
		});
		return builder.build();
	}

	private static void addDimensionEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder,
			ConfigData config, List<Runnable> afterSave) {
		Set<String> dimensions = new LinkedHashSet<>(Arrays.asList(
			"minecraft:overworld",
			"minecraft:the_nether",
			"minecraft:the_end"
		));
		dimensions.addAll(config.dimension_overrides.keySet());

		String current = VisionHandler.dimensionOf(Minecraft.getInstance());
		if (current != null)
			dimensions.add(current);

		for (String dimension : dimensions) {
			VisionSettings existing = config.dimension_overrides.get(dimension);
			VisionSettings settings = existing != null ? existing : config.copy();
			VisionSettings asShown = settings.copy();

			if (existing == null)
				afterSave.add(() -> {
					if (config.dimension_overrides.get(dimension) == settings && settings.sameAs(asShown))
						settings.copyFrom(config);
				});

			List<AbstractConfigListEntry> entries = new ArrayList<>();
			entries.add(entryBuilder.startBooleanToggle(text(PREFIX + "option.dimension_override"), existing != null)
				.setDefaultValue(false)
				.setTooltip(
					text(PREFIX + "tooltip.dimension_override.1"),
					text(PREFIX + "tooltip.dimension_override.2"),
					text(PREFIX + "tooltip.dimension_override.3")
				)
				.setSaveConsumer(v -> {
					if (v) config.dimension_overrides.put(dimension, settings);
					else config.dimension_overrides.remove(dimension);
				})
				.build());
			entries.add(autoNvEntry(entryBuilder, settings));
			entries.addAll(curveEntries(entryBuilder, settings));

			category.addEntry(entryBuilder.startSubCategory(dimensionName(dimension), entries).build());
		}
	}

	private static Component dimensionName(String dimension) {
		switch (dimension) {
			case "minecraft:overworld":
				return text(PREFIX + "dimension.overworld");
			case "minecraft:the_nether":
				return text(PREFIX + "dimension.the_nether");
			case "minecraft:the_end":
				return text(PREFIX + "dimension.the_end");
			default:
				return literal(dimension);
		}
	}

	private static AbstractConfigListEntry autoNvEntry(ConfigEntryBuilder entryBuilder, VisionSettings settings) {
		return entryBuilder.startBooleanToggle(text(PREFIX + "option.auto_nv"), settings.auto_nv)
			.setDefaultValue(true)
			.setSaveConsumer(v -> settings.auto_nv = v)
			.build();
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
