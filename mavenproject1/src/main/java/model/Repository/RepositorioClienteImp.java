/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Repository;

import java.sql.*;
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
       return null;
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
