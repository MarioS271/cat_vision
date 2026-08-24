package net.marios271.cat_vision.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.marios271.cat_vision.CatVision;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigData extends VisionSettings {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private transient File file;

    public boolean remember_nv = true;
    public boolean blindness_immunity = true;
    public boolean nausea_immunity = true;
	public boolean darkness_immunity = true;

    public boolean has_nv = false;

    public Map<String, VisionSettings> dimension_overrides = new LinkedHashMap<>();

    public VisionSettings forDimension(String dimension) {
        VisionSettings override = dimension == null ? null : dimension_overrides.get(dimension);
        return override != null ? override : this;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
            CatVision.LOGGER.info("Saved " + CatVision.MOD_FRIENDLY_NAME + " config");
        } catch (IOException exception) {
            CatVision.LOGGER.error("Failed to save config", exception);
        }
    }

    public static ConfigData load(File configDir) {
        File file = new File(configDir, CatVision.CONFIG_FILE);
        ConfigData result = null;
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                CatVision.LOGGER.info("Loaded " + CatVision.MOD_FRIENDLY_NAME + " config");
                result = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException exception) {
                CatVision.LOGGER.warn("Failed to load config, returning default values");
            }
        }
        if (result == null) result = new ConfigData();
        if (result.dimension_overrides == null) result.dimension_overrides = new LinkedHashMap<>();
        result.file = file;
        return result;
    }
}
