package org.uygar.postit.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection(String databasePath) {
        loadSQLiteClass();
        this.initConnection(databasePath);
    }

    private void initConnection(String databasePath) {
        try {
            connection =  DriverManager.getConnection(databasePath);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadSQLiteClass() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}