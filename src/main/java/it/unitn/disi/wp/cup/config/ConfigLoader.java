package it.unitn.disi.wp.cup.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Load in memory a configuration file from 'resources' folder
 *
 * @author Carlo Corradini
 */
public final class ConfigLoader {

    private static final String ENCODING = "UTF-8";
    private static final char LIST_DELIMITER = ',';
    private static final boolean THROW_EXCEPTION_ON_MISSING = true;
    private static final boolean INCLUDES_ALLOWED = true;

    /**
     * Given a configuration File Name, load it and return properties accessible
     * with getters
     *
     * @param fileName Name of the File
     * @return PropertiesConfiguration Object
     */
    public static PropertiesConfiguration get(String fileName) {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<>(
                PropertiesConfiguration.class).configure(new Parameters().properties()
                .setFileName(fileName)
                .setEncoding(ENCODING)
                .setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER))
                .setThrowExceptionOnMissing(THROW_EXCEPTION_ON_MISSING)
                .setIncludesAllowed(INCLUDES_ALLOWED));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }
}
