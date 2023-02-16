package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    public static File getProperty(String key) throws IOException {
        Properties property = new Properties();
        FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES);
        property.load(inputStream);

        return new File(property.getProperty(key));
    }
}
