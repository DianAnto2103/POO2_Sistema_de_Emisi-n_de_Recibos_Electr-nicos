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
    private Connection conexion;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=OasisDB";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USUARIO = "developer";
    private static final String PASSWORD = "oasis123";
    
    private Singleton(){}
    
    public Singleton getInstance() {
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    
    public static Connection conexion(){
        
        return null;
        
    }
}
