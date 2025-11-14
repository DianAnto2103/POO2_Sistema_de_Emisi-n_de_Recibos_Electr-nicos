/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author diana
 */
public class Singleton {
    private static Singleton instance; //Instancia unica estatica(solo habrá UNA en todo el programa).
    private Connection conexion; //Conexión a la base de datos
    
    //Datos de conexión a SQL Server. final porque no cambian.
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=OasisDB";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USUARIO = "developer";
    private static final String PASSWORD = "oasis123";
    
    //Constructor PRIVADO; nadie más puede crear instancias (magia de Singleton)
    private Singleton()
    {
        conectar();
    }
    
    //Método PÚBLICO y ESTATICO para obtener la instancia única
    public static Singleton getInstance() {
        if(instance == null) //Si no existe la instancia, la crea (solo la primera vez)
        { 
            instance = new Singleton(); // Llama al constructor privado
        }
        return instance; // Retorna la misma instancia siempree
    }
    
    //Método para obtener la conexión a la BD
    public Connection getConexion(){
        try
        {
            if(conexion == null || conexion.isClosed()) //si la conexión es nula o esta cerrada
            {
                conectar(); // Si esta cerrada, reconecta
            }        
        } catch(SQLException e){ 
            e.printStackTrace(); // Si hay error, lo muestra en consola.
        }
        
        return conexion; // Retorna la conexión lista para usar
        
    }
    
    //Método PRIVADO que hace la conexión real a SQL Server
    private void conectar(){ 
        try
        {
            Class.forName(DRIVER); // Carga el driver de SQL Server en memoria
            
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD); // Establece la conexión con la base de datos

        } catch (SQLException ex) {
            
            // Error de conexión (usuario, password)
            
            System.err.println("Error SQL: " + ex.getMessage());
            
        } catch (ClassNotFoundException ex) {
            
            // Error si no encuentra el driver JDBC
            
            System.err.println("Driver no encontrado: " + ex.getMessage());
            
        }
    }
    
    //Método para cerrar la conexión [esto es opcional]
    
    public void cerrarConexion(){
        if(conexion != null){
            try
            {
                conexion.close();
            } catch(SQLException e){
                
                System.err.println("Error cerrando conexión: " + e.getMessage());
                
            }
        }
    }
    
}
