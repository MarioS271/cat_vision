package net.marios271.cat_vision.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.marios271.cat_vision.CatVision;

import java.io.*;

public class ConfigData {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private transient File file;

    public boolean remember_nv = true;
    public boolean auto_nv = true;
    public boolean blindness_immunity = true;
    public boolean nausea_immunity = true;

    public boolean has_nv = false;

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
            CatVision.LOGGER.info("Saved CatVision config");
        } catch (IOException exception) {
            CatVision.LOGGER.error("Failed to save config", exception);
        }
    }

    public static ConfigData load(File configDir) {
        File file = new File(configDir, CatVision.CONFIG_FILE);
        ConfigData result = null;
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                CatVision.LOGGER.info("Loaded CatVision config");
                result = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException exception) {
                CatVision.LOGGER.warn("Failed to load config, returning default values");
            }
        }
        if (result == null) result = new ConfigData();
        result.file = file;
        return result;
    }
}
