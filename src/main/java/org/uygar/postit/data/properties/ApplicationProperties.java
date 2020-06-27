package org.uygar.postit.data.properties;

import java.io.*;
import java.nio.channels.Channel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Properties;

public class ApplicationProperties {

    Path propPath;
    Properties properties;

    public ApplicationProperties(Path propPath) {
        this.propPath = propPath;
        properties = new Properties();
        loadPropertiesFile();
    }

    public ApplicationProperties() {
        this.propPath = Paths.get("application.properties");
        loadPropertiesFile();
    }

    public void storeProperty(String key, String value) {
        properties.put(key, value);
    }

    public void storePropertiesFile() {
        File property = new File(propPath.toAbsolutePath().toString());
        try {
            OutputStream stream = new
                    BufferedOutputStream(new FileOutputStream(property));
            properties.store(stream, "changed properties: "+ LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPropertiesFile() {
        File property = new File(propPath.toAbsolutePath().toString());
        try {
            InputStream stream = new
                    BufferedInputStream(new FileInputStream(property));
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}