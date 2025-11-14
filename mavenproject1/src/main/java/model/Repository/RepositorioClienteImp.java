/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Singleton.Singleton;

/**
 *
 * @author diana
 */
public class RepositorioClienteImp implements RepositorioCliente {
    private Connection connection;
    
    public RepositorioClienteImp()
    {
        this.connection = Singleton.getInstance().getConexion(); //Conexión
    }
    
    @Override
    public Cliente buscarPorID(int id)
    {
         // Consulta SQL para buscar cliente por ID, solo si está activo.
        String sql = "SELECT * FROM clientes WHERE id = ? AND activo = 1";
        
        // try-with-resources: cierra automáticamente el PreparedStatement
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
            // Reemplaza el primer parámetro (?) con el ID recibido
            stmt.setInt(1, id);
            
            // Ejecuta la consulta y obtiene los resultados
            ResultSet resultado = stmt.executeQuery();
            
            // rs.next() avanza al primer registro y devuelve true si existe
            if(resultado.next())
            {
                // Crear un nuevo objeto Cliente para llenar con datos de la BD
                Cliente cliente = new Cliente();
                
                // Extraer cada campo de la base de datos y asignarlo al objeto
                cliente.setID(resultado.getInt("id")); //ID numérico
                cliente.setRUC(resultado.getString("ruc")); //RUC como String
                cliente.setRazonSocial(resultado.getString("razon_social")); //Razón social
                cliente.setTelefono(resultado.getString("telefono")); //teléfono
                cliente.setMensualidad(resultado.getDouble("mensualidad")); //Monto mensual
                cliente.setActivo(resultado.getBoolean("activo")); //Estado activo/inactivo
                
                return cliente; //retorna el cliente encontrado
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace(); // Si hay error de base de datos, mostrar detalles
        }
        
        return null;  // Si no encuentra cliente o hay error, retorna null

    }
    
    @Override
    public List<Cliente> buscarTodos()
    {
        
        // Lista para almacenar todos los clientes que se van a retornar
        List<Cliente> clientes = new ArrayList<>();
        
        
        // Consulta SQL. Selecciona todos los clientes activos ordenados por ID ascendente (1,2,3,4, 5....)
        String sql = "SELECT * FROM clientes WHERE activo = 1 ORDER BY id ASC";
        
        // try-with-resources: cierra automáticamente el Statement y ResultSet
        try(Statement stmt = connection.createStatement())
        {
            
            //Ejecutar la consulta y obtener el ResultSet
            ResultSet resultado = stmt.executeQuery(sql);
            
            while(resultado.next())
            {
                
                //Crear un nuevo objeto Clietne
                Cliente cliente = new Cliente();
                
                
                //Llenar el objeto Cliente con los datos de la base de datos. 
                
                cliente.setID(resultado.getInt("id"));
                cliente.setRUC(resultado.getString("ruc"));
                cliente.setRazonSocial(resultado.getString("razon_social"));
                cliente.setTelefono(resultado.getString("telefono"));
                cliente.setMensualidad(resultado.getDouble("mensualidad"));
                cliente.setActivo(resultado.getBoolean("activo"));
                
                //Agregar el cliente a la lista.
                
                clientes.add(cliente);
                
            }
        } catch(SQLException ex){
            // Si hay error de base de datos, mostrar detalles para debuggear
            ex.printStackTrace();
            
        }
        
        //retornar lista de clientes
        return clientes;
    }
    
    @Override
    public List<Cliente> buscarPorEjemplo(Cliente cliente)
    {
        
        return null;
        
    }
    
    @Override
    public void guardar(Cliente cliente)
    {
        
    }
    
    @Override
    public void actualizar(Cliente cliente)
    {
 
    }
    
    @Override
    public void eliminar(Cliente cliente)
    {
        
    }
  
}
