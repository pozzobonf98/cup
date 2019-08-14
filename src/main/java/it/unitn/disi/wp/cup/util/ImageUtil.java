package it.unitn.disi.wp.cup.util;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Util class for retrieving useful Application images
 *
 * @author Carlo Corradini
 */
public final class ImageUtil {

    private static final Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());
    private static BufferedImage LOGO = null;

    /**
     * Configure the class
     *
     * @param servletContext The {@link ServletContext} get information from
     * @throws NullPointerException If servletContext is null
     */
    public static void configure(final ServletContext servletContext) throws NullPointerException {
        if (servletContext == null)
            throw new NullPointerException("ServletContext cannot be null");

        try {
            LOGO = ImageIO.read(new File(FilenameUtils.separatorsToUnix(servletContext.getRealPath("/") + "assets/_default/images/favicon/android-chrome-512x512.png")));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to retrieve images for ImageUtil", ex);
        }
    }

    private static void isConfigured() throws NullPointerException {
        if (LOGO == null)
            throw new NullPointerException("ImageUtil has not been configured");
    }

    /**
     * Return the {@link BufferedImage image} representing the Logo of the Application
     *
     * @return The Logo of the Application
     * @throws NullPointerException If ImageUtil has not been configured
     */
    public static BufferedImage getLOGO() throws NullPointerException {
        isConfigured();
        return LOGO;
    }
}
