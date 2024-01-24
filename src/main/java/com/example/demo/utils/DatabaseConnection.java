package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://translation.cexc1sf5tjqx.us-east-1.rds.amazonaws.com:3306/translation";
    private static final String username = "root";
    private static final String password = "translator";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
