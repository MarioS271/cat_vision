package net.marios271.cat_vision;

import net.marios271.cat_vision.config.ConfigData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public final class CatVision {
    public static final String MOD_ID = "cat_vision";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final String CONFIG_FILE = "cat_vision.json";
    public static ConfigData CONFIG;

    public static void init(File configDir) {
        CONFIG = ConfigData.load(configDir);
        LOGGER.info("Initialized CatVision Mod");
    }
}
