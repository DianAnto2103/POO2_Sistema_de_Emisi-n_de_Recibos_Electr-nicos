/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import model.Recibo;
import model.Cliente;
import model.Singleton.Singleton;

/**
 *
 * @author diana
 */
public class RepositorioReciboImp implements RepositorioRecibo{
    private final Connection conexion;
    
    public RepositorioReciboImp()
    {
        // Obtener la conexión unica usando el Singleton
        this.conexion = Singleton.getInstance().getConexion();
    }
    
    @Override
    public Recibo buscarPorID(int id)
    {
        //Consulta SQL con JOIN para obtener datos del cliente también
        String sql = "SELECT r.*, c.ruc, c.razon_social, c.telefono " +
                "FROM recibos r INNER JOIN clientes c ON r.cliente_id = c.id " +
                "WHERE r.id = ? AND r.activo = 1";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setInt(1, id); // Asignar el ID al parámetro
            ResultSet resultado = stmt.executeQuery();
            
            if(resultado.next()){
                return mapearRecibo(resultado); // Convertir ResultSet a objeto Recibo
            }
            
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return null; // Si no encuentra el recibo
        
    }
    
    @Override
    public List<Recibo> buscarTodos()
    {
        List<Recibo> recibos = new ArrayList<>();
        
        // Consulta para obtener todos los recibos activos con datos del cliente
        String sql = "SELECT r.*, c.ruc, c.razon_social, c.telefono " +
                "FROM recibos r INNER JOIN clientes c ON r.cliente_id = c.id " +
                "WHERE r.activo = 1 ORDER BY r.fecha_emision DESC";
        
        try(Statement stmt = conexion.createStatement())
        {
            ResultSet resultado = stmt.executeQuery(sql);
            
            while(resultado.next()){
                recibos.add(mapearRecibo(resultado));
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return recibos;
        
    }
    
    @Override
    public List<Recibo> buscarPorCliente(int clienteId){
        List<Recibo> recibos = new ArrayList<>();
        // Consulta para obtener recibos de un cliente específico
        String sql = "SELECT r.*, c.ruc, c.razon_social, c.telefono " +
                "FROM recibos r INNER JOIN clientes c ON r.cliente_id = c.id " +
                "WHERE r.cliente_id = ? AND r.activo = 1 ORDER BY r.fecha_emision DESC";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, clienteId); // Asignar el ID del cliente
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                recibos.add(mapearRecibo(resultado));
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return recibos;
        
    }
    
    @Override
    public List<Recibo> buscarPorFecha(Date fechaInicio, Date fechaFin)
    {
        List<Recibo> recibos = new ArrayList<>();
        // Consulta para obtener recibos en un rango de fechas (para reportes)
        String sql = "SELECT r.*, c.ruc, c.razon_social, c.telefono " +
                "FROM recibos r INNER JOIN clientes c ON r.cliente_id = c.id " +
                "WHERE r.fecha_emision BETWEEN ? AND ? AND r.activo = 1 " +
                "ORDER BY r.fecha_emision DESC";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            // Convertir java.util.Date a java.sql.Date para la BD
            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                recibos.add(mapearRecibo(resultado));
            }
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return recibos;
        
    }
    
    @Override
    public void guardar(Recibo recibo){
        // Consulta SQL para insertar nuevo recibo
        String sql = "INSERT INTO recibos (numero_recibo, fecha_emision, total, cliente_id, usuario_id, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            // Asignar todos los valores del recibo a los parámetros
            stmt.setString(1, recibo.getNumeroRecibo());
            stmt.setDate(2, new java.sql.Date(recibo.getFechaEmision().getTime()));
            stmt.setDouble(3, recibo.getTotal());
            stmt.setInt(4, recibo.getCliente().getID());
            stmt.setInt(5, 1);
            stmt.setBoolean(6, true); // Por defecto activo
            
            stmt.executeUpdate();
            
            // Obtener el ID generado automáticamente por la BD
            ResultSet resultado = stmt.getGeneratedKeys();
            
            if(resultado.next()){
                recibo.setID(resultado.getInt(1)); // Asignar el ID al objeto
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void actualizar(Recibo recibo){
        // Consulta SQL para actualizar recibo existente
        String sql = "UPDATE recibos SET numero_recibo = ?, fecha_emision = ?, total = ?, cliente_id = ? WHERE id = ?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql))
        {
            
            stmt.setString(1, recibo.getNumeroRecibo());
            stmt.setDate(2, new java.sql.Date(recibo.getFechaEmision().getTime()));
            stmt.setDouble(3, recibo.getTotal());
            stmt.setInt(4, recibo.getCliente().getID());
            stmt.setInt(5, recibo.getID());
            
            stmt.executeUpdate();
            
        } catch(SQLException ex){
            
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void eliminar(Recibo recibo){
        
        // Eliminación lógica
        String sql = "UPDATE recibos SET activo = 0 WHERE id = ?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setInt(1, recibo.getID());
            stmt.executeUpdate();
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    
    @Override
    public String obtenerUltimoNumeroRecibo(){
        // Consulta para obtener el último número de recibo
        String sql = "SELECT TOP 1 numero_recibo FROM recibos ORDER BY id DESC";
        
        try (Statement stmt = conexion.createStatement()){
            ResultSet resultado = stmt.executeQuery(sql);
            
            if(resultado.next()){
                return resultado.getString("numero_recibo");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return "R-000"; //Por defecto si no hay recibos.
        
    }
    
    // Método privado para convertir ResultSet a objeto Recibo
    private Recibo mapearRecibo(ResultSet resultado) throws SQLException {
        
        Recibo recibo = new Recibo();
        
        
        // Mapear datos básicos del recibo
        recibo.setID(resultado.getInt("id"));
        recibo.setNumeroRecibo(resultado.getString("numero_recibo"));
        recibo.setFechaEmision(resultado.getDate("fecha_emision"));
        recibo.setTotal(resultado.getDouble("total"));
        
         // Mapear datos del cliente (usando JOIN)
        Cliente cliente = new Cliente();
        cliente.setID(resultado.getInt("cliente_id"));
        cliente.setRUC(resultado.getString("ruc"));
        cliente.setRazonSocial(resultado.getString("razon_social"));
        cliente.setTelefono(resultado.getString("telefono"));
        recibo.setCliente(cliente);

        return recibo;
        
    }
    
}
