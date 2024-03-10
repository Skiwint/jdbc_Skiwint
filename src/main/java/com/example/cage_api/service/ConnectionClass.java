package com.example.cage_api.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionClass {

    private static final String URL = "jdbc:mysql://localhost:3306/user_api";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Skiwint280504!";

    public static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
