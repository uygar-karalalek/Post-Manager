package org.uygar.postit.data.properties;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class PostManagerProperties {

    Path propPath;
    Properties properties;

    public PostManagerProperties(Path propPath) {
        this.propPath = propPath;
        properties = new Properties();
        loadProperties();
    }

    public PostManagerProperties() {
        this.propPath = Paths.get("src/main/resources/application.properties");
        properties = new Properties();
        loadProperties();
    }

    public Object putProperty(String key, Object value) {
        return properties.put(key, value);
    }

    public Map<String, LocalDateTime> getAllSpecificFolders() {
        Map<String, LocalDateTime> specific = new HashMap<>();

        Iterator<Object> keysIterator = this.properties.keys().asIterator();
        while (keysIterator.hasNext()) {
            String folder = (String) keysIterator.next();
            if (folder.contains("folder_")) {
                specific.put(
                        folder.substring("folder_".length()),
                        LocalDateTime.parse(properties.getProperty(folder))
                );
            }
        }

        return specific;
    }

    public void putSpecificFolderProperty(String path) {
        String folderName = "folder_" + path;
        try {
            properties.put(folderName, LocalDateTime.now().toString());
        } catch (Exception exception) {
            this.putProperty(folderName, "1");
        }
    }

    private String getNumOfVisitedFolder(String path) {
        return (String) this.properties.get("folder_" + path);
    }

    public String getStringProperty(String propertyName) {
        return (String) this.properties.get(propertyName);
    }

    public Optional<Double> getDoubleProperty(String key) {
        try {
            return Optional.of(Double.parseDouble
                    (this.properties.getProperty(key)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Double getDoublePropertyOr(String key, double orValue) {
        return getDoubleProperty(key).orElse(orValue);
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