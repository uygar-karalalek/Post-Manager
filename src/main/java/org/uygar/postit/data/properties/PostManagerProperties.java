package org.uygar.postit.data.properties;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class PostManagerProperties {

    Properties properties;
    String propPath;

    public PostManagerProperties(String propPath) {
        this.propPath = propPath;
        properties = new Properties();
        loadProperties();
    }

    public PostManagerProperties() {
        this.propPath = "/org/uygar/application.properties";
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
        try {
            InputStream resourceAsStream = PostManagerProperties.class.getResourceAsStream(propPath);

            OutputStream outputStream = new ByteArrayOutputStream();
            resourceAsStream.transferTo(outputStream);

          //  OutputStream output = new FileOutputStream(getClass().getResource(propPath).getPath());
            properties.store(outputStream, "changed properties: " + LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProperties() {
        try {
            InputStream resourceAsStream = PostManagerProperties.class.getResourceAsStream(propPath);
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("The file not already exists!");
        }
    }

}