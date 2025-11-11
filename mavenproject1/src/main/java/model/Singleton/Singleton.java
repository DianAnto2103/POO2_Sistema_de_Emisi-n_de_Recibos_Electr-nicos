/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Singleton;

import java.sql.Connection;

/**
 *
 * @author diana
 */
public class Singleton {
    private static Singleton instance;
    private static Connection conexion;
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=OasisDB";
    private static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String USUARIO = "developer";
    private static String PASSWORD = "oasis123";
    
    public Singleton getInstance() {
        return instance;
    }
}
