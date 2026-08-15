package com.database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.text.Collator.PRIMARY;

public class DatabaseConfig {

    private static Connection connnection;

    private static Connection getConnection() throws SQLException {
        try {
            if (connnection == null) {


                connnection = DriverManager.getConnection(
                        "//127.0.0.1:3306",
                        "root",
                        "Ume$h2896"
                );
                createDatabaseIfNotExists(connnection, "QuizMaster");
                createTableIfNotExists(connnection, "QuizMaster");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connnection;

    }

    private static void useDatabase(Connection connnection, String quizMaster) {
        try {
            connnection.createStatement().executeUpdate("USE " + quizMaster);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createDatabaseIfNotExists(Connection connnection, String QuizMaster) {
        
        try {
            String sql = "CREATE DATABASE IF NOT EXISTS " + QuizMaster;
            connnection.createStatement().executeUpdate(sql);
            useDatabase(connnection, QuizMaster);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists(Connection connnection, String quizMaster) {
        useDatabase(connnection, quizMaster);


        try {
            String sql = "CREATE TABLE IF NOT EXISTS question (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "question_text TEXT," +
                    "option1 VARCHAR(100)," +
                    "option2 VARCHAR(100)," +
                    "option3 VARCHAR(100)," +
                    "option4 VARCHAR(100)," +
                    "correct_option INT" +
                    ")";
            connnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "CREATE TABLE IF NOT EXISTS student (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "First_name VARCHAR(100)," +
                    "Last_Name VARCHAR(100)," +
                    "Email VARCHAR(100) PRIMARY KEY," +
                    "Username VARCHAR(100) UNIQUE," +
                    "Password VARCHAR(100) Encrypted," +
                    "City VARCHAR(100)," +
                    "Phone_Number VARCHAR(15)" +
                    ")";
            connnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            String sql = "CREATE TABLE IF NOT EXISTS score (" +

                    "student_id INT," +
                    "Totalscore INT," +
                    "FOREIGN KEY (student_id) REFERENCES student(id)" +
                    "grade VARCHAR(10)"+
                    ")";
            connnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}


