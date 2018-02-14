package us.infinz.pawelcwieka.organiser.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    public static String getProperty(String property) {

        try {
            properties.load(new FileInputStream("config.properties"));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return properties.getProperty(property, "");

    }


    public static void saveProperty(String property, String value) {

        try {

            properties.setProperty(property, value);
            properties.store(new FileOutputStream("config.properties"), null);

        } catch (IOException io) {
            io.printStackTrace();

        }
    }



}
