package it.unitn.disi.wp.cup.config;

import org.apache.commons.configuration2.PropertiesConfiguration;

/**
 * Configuration Manager for properties File
 *
 * @author Carlo Corradini
 */
public abstract class ConfigManager {

    private final PropertiesConfiguration config;
    private final String category;

    /**
     * Create a Manager given a Name of the Configuration File and the category
     * associated with it
     *
     * @param fileName Name of the File
     * @param category Properties category
     */
    public ConfigManager(String fileName, String category) {
        this.config = ConfigLoader.get(String.format("%s/%s", "config", fileName));
        this.category = category;
    }

    private String formatter(String key) {
        return String.format("%s.%s", category, key);
    }

    /**
     * Return the String associated with the key
     *
     * @param key Configuration key
     * @return The String value
     */
    protected String getString(String key) {
        return config.getString(formatter(key));
    }

    /**
     * Return the int associated with the key
     *
     * @param key Configuration key
     * @return The int value
     */
    protected int getInt(String key) {
        return config.getInt(formatter(key));
    }

    /**
     * Return the boolean associated with the key
     *
     * @param key Configuration key
     * @return The boolean value
     */
    protected boolean getBoolean(String key) {
        return config.getBoolean(formatter(key));
    }

    /**
     * Return the double associated with the key
     *
     * @param key Configuration key
     * @return The double value
     */
    protected double getDouble(String key) {
        return config.getDouble(formatter(key));
    }

}
