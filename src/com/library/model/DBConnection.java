package com.library.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton responsible for providing JDBC connections to the MySQL database.
 */
public class DBConnection {
    private static DBConnection instance;
    private final String url;
    private final String user;
    private final String password;

    private DBConnection() {
        Properties props = new Properties();
        String tmpUrl = "jdbc:mysql://localhost:3306/library_db";
        String tmpUser = "root";
        String tmpPass = "vanshgajiwala@777";

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            tmpUrl = props.getProperty("db.url", tmpUrl);
            tmpUser = props.getProperty("db.user", tmpUser);
            tmpPass = props.getProperty("db.password", tmpPass);
        } catch (IOException e) {
            System.err.println("[WARN] config.properties not found or unreadable. Using defaults. " + e.getMessage());
        }

        this.url = tmpUrl;
        this.user = tmpUser;
        this.password = tmpPass;

        try {
            // Modern MySQL Connector/J auto-registers the driver.
            // Class.forName is not strictly required, but kept for clarity.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] MySQL JDBC Driver not found in classpath.");
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Returns a new JDBC Connection. Caller should use try-with-resources to close it.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
