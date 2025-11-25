/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

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
    private final Connection connection;
    
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
                return mapearCliente(resultado); //retorna el cliente encontrado
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
                clientes.add(mapearCliente(resultado));
                
            }
        } catch(SQLException ex){
            // Si hay error de base de datos, mostrar detalles para debuggear
            ex.printStackTrace();
            
        }
        
        //retornar lista de clientes
        return clientes;
    }
    
    @Override
    public List<Cliente> buscarPorRUC(String ruc)
    {
        List<Cliente> clientes = new ArrayList<>();
        
        //Consulta SQL: Buscar cliente cuyo RUC empiece con texto ingresado..
        String sql = "SELECT * FROM clientes WHERE ruc LIKE ? AND activo = 1 ORDER BY id ASC";
        
        // try-with-resources: cierra automáticamente el PreparedStatement
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
           /* Configurar el parámetro de búsqueda: ruc + "%" para búsqueda que empiece con... 
            Ejemplo: si ruc = "201001", busca "201001%" */
            
            stmt.setString(1, ruc + "%");
            
            //Ejecutar la consulta y obtener resultados
            
            ResultSet resultado = stmt.executeQuery();
            
            
            // Procesar cada registro encontrado
            while(resultado.next())
            {
                clientes.add(mapearCliente(resultado));
                
            }
            
        } catch (SQLException ex){
            ex.printStackTrace(); // Si hay error en la base de datos, mostrar el detalle
        }
        
        return clientes; // Retornar la lista de clientes encontrados (puede estar vacía)
        
    }
    
    @Override
    public List<Cliente> buscarPorRazonSocial(String razonSocial)
    {
        List<Cliente> clientes = new ArrayList<>();
        
        //Consulta SQL: Buscar cliente cuyo Razon Social empiece con texto ingresado..
        String sql = "SELECT * FROM clientes WHERE razon_social LIKE ? AND activo = 1 ORDER BY razon_social ASC";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, "%" + razonSocial + "%");
            
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                clientes.add(mapearCliente(resultado));
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return clientes;       
    }

    @Override
    public void guardar(Cliente cliente)
    {
        //Consulta SQl para insertar nuevos clients
        
        String sql = "INSERT INTO clientes (ruc, razon_social, telefono, mensualidad, activo) VALUES (?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            //Establecer los valores por cada parámetro (?)
            stmt.setString(1, cliente.getRUC());
            stmt.setString(2, cliente.getRazonSocial());
            stmt.setString(3, cliente.getTelefono());
            stmt.setDouble(4, cliente.getMensualidad());
            stmt.setBoolean(5, cliente.isActivo());
            
            //Ejecutar la inserción
            stmt.executeUpdate();
            
            // Obtener el ID generado automáticamente por la base de datos
            ResultSet resultado = stmt.getGeneratedKeys();    
            if(resultado.next()){
                // Asignar el ID generado al objeto cliente
                cliente.setID(resultado.getInt(1));
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void actualizar(Cliente cliente)
    {
        // Consulta SQL para actualizar cliente existentw
        String sql = "UPDATE clientes SET ruc = ?, razon_social = ?, telefono = ?, mensualidad = ?, activo = ? WHERE id = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            
            // Establecer los nuevos valores para cada parámetro (?)
            stmt.setString(1, cliente.getRUC());
            stmt.setString(2, cliente.getRazonSocial());
            stmt.setString(3, cliente.getTelefono());
            stmt.setDouble(4, cliente.getMensualidad());
            stmt.setBoolean(5, cliente.isActivo());
            stmt.setInt(6, cliente.getID()); // WHERE id = ?
        
            // Ejecutar la actualización
            stmt.executeUpdate();
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
    }
    
    @Override
    public void eliminar(Cliente cliente)
    {
        // Consulta SQL para eliminación lógica..
        String sql = "UPDATE clientes SET activo = 0 WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
            // Establecer el ID del cliente a eliminar
            stmt.setInt(1, cliente.getID());
             // Ejecutar la eliminación lógica
             stmt.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    private Cliente mapearCliente(ResultSet resultado) throws SQLException 
    {
        // Método privado auxiliar para mapear ResultSet a Cliente 
        Cliente cliente = new Cliente();
        cliente.setID(resultado.getInt("id"));
        cliente.setRUC(resultado.getString("ruc"));
        cliente.setRazonSocial(resultado.getString("razon_social"));
        cliente.setTelefono(resultado.getString("telefono"));
        cliente.setMensualidad(resultado.getDouble("mensualidad"));
        cliente.setActivo(resultado.getBoolean("activo"));
        return cliente;
    }
  
}
