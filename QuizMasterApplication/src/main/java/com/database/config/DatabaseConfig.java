package com.database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConfig {

    private static final String DATABASE_NAME = "QuizMaster";
    private static final String DATABASE_URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://127.0.0.1:3306/");
    private static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "root");

    private static Connection connection;

    private DatabaseConfig() {
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String password = System.getenv("DB_PASSWORD");
            if (password == null) {
                throw new SQLException("DB_PASSWORD environment variable is not set.");
            }
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, password);
            createDatabaseIfNotExists(connection);
            createTablesIfNotExist(connection);
        }
        return connection;
    }

    private static void useDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE " + DATABASE_NAME);
        }
    }

    private static void createDatabaseIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
        }
        useDatabase(connection);
    }

    private static void createTablesIfNotExist(Connection connection) throws SQLException {
        useDatabase(connection);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS question ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "question_text TEXT NOT NULL,"
                    + "option1 VARCHAR(100) NOT NULL,"
                    + "option2 VARCHAR(100) NOT NULL,"
                    + "option3 VARCHAR(100) NOT NULL,"
                    + "option4 VARCHAR(100) NOT NULL,"
                    + "correct_option INT NOT NULL,"
                    + "CHECK (correct_option BETWEEN 1 AND 4)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS student ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "first_name VARCHAR(100) NOT NULL,"
                    + "last_name VARCHAR(100) NOT NULL,"
                    + "username VARCHAR(100) NOT NULL UNIQUE,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "city VARCHAR(100),"
                    + "email VARCHAR(100) NOT NULL UNIQUE,"
                    + "mobile VARCHAR(15)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS score ("
                    + "student_id INT NOT NULL,"
                    + "total_score INT NOT NULL,"
                    + "grade VARCHAR(10),"
                    + "FOREIGN KEY (student_id) REFERENCES student(id)"
                    + ")");
        }
    }
}
