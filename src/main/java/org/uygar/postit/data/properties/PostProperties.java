package org.uygar.postit.data.properties;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

public class PostProperties {

    Path propPath;
    Properties properties;

    public PostProperties(Path propPath) {
        this.propPath = propPath;
        properties = new Properties();
        loadProperties();
    }

    public PostProperties() {
        this.propPath = Paths.get("src/main/resources/application.properties");
        properties = new Properties();
        loadProperties();
    }

    public Object putProperty(String key, Object value) {
        return properties.put(key, value);
    }

    public String getStringProperty(String propertyName) {
        return (String) this.properties.get(propertyName);
    }

    public Object getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }

    public void storeProperties() {
        File property = new File(propPath.toAbsolutePath().toString());
        try {
            OutputStream stream = new
                    BufferedOutputStream(new FileOutputStream(property));
            properties.store(stream, "changed properties: " + LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProperties() {
        File property = new File(propPath.toAbsolutePath().toString());
        try {
            InputStream stream = new
                    BufferedInputStream(new FileInputStream(property));
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("The file not already exists!");
        }
    }

}