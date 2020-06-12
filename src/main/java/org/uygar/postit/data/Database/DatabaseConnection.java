package org.uygar.postit.data.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private String databasePath;
    private Connection connection;

    {
        loadSQLiteClass();
    }

    public DatabaseConnection(String databasePath) {
        this.databasePath = databasePath;
        connection = initConnection(this.databasePath);
    }

    private Connection initConnection(String databasePath) {
        try {
            return DriverManager.getConnection(databasePath);
        } catch (SQLException e) {
            return null;
        }
    }

    private void loadSQLiteClass() {
        try {
            Class.forName("org.sqlite.SQLiteConnection");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}