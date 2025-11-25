/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ConceptoPago;
import model.Singleton.Singleton;

/**
 *
 * @author diana
 */
public class RepositorioConceptoPagoImp implements RepositorioConceptoPago{
    private Connection connection;
    
    public RepositorioConceptoPagoImp(){
        this.connection = Singleton.getInstance().getConexion(); //Conexión
    }
    
    @Override
    public ConceptoPago buscarPorID(int id)
    {
        String sql = "SELECT * FROM conceptos_pago WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            
            if(resultado.next()){
                return mapearConceptoPago(resultado);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;       
    }
    
    @Override
    public List<ConceptoPago> buscarPorRecibo(int reciboID){
        
        List<ConceptoPago> conceptos = new ArrayList<>();
        
        String sql = "SELECT * FROM conceptos_pago WHERE recibo_id = ? ORDER BY id";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, reciboID);
            
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                conceptos.add(mapearConceptoPago(resultado));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return conceptos;
    }
    
    @Override
    public void guardarTodos(List<ConceptoPago> conceptos) {
        String sql = "INSERT INTO conceptos_pago (descripcion, monto, metodo_pago, recibo_id) VALUES (?, ?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            
            for(ConceptoPago concepto : conceptos){
                stmt.setString(1, concepto.getDescripcion());
                stmt.setDouble(2, concepto.getMonto());
                stmt.setString(3, concepto.getMetodoPago().getNombre());
                stmt.setInt(4, concepto.getReciboID());
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }   
    }
    
    @Override
    public void guardar(ConceptoPago concepto){
        
        String sql = "INSERT INTO conceptos_pago (descripcion, monto, metodo_pago, recibo_id) VALUES (?, ?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            stmt.setString(1, concepto.getDescripcion());
            stmt.setDouble(2, concepto.getMonto());
            stmt.setString(3, concepto.getMetodoPago().getNombre());
            stmt.setInt(4, concepto.getReciboID());
            
            stmt.executeUpdate();
            
            ResultSet resultado = stmt.getGeneratedKeys();
            
            if(resultado.next()){
                concepto.setCodigo(resultado.getInt(1));
            }
            
        } catch (SQLException ex) {
            
        }
        
    }
    
    @Override
    public void actualizar(ConceptoPago concepto) {
        String sql = "UPDATE conceptos_pago SET descripcion = ?, monto = ?, metodo_pago = ? WHERE id = ?";
        
         try (PreparedStatement stmt = connection.prepareStatement(sql)){
             stmt.setString(1, concepto.getDescripcion());
             stmt.setDouble(2, concepto.getMonto());
             stmt.setString(3, concepto.getMetodoPago().getNombre());
             stmt.setInt(4, concepto.getCodigo());
             
             stmt.executeUpdate();
             
         } catch (SQLException ex){
            ex.printStackTrace();
         }
    
    
    }
    
    @Override
    public void eliminar(ConceptoPago concepto){
        String sql = "DELETE FROM conceptos_pago WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, concepto.getCodigo());
            stmt.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
  
    private ConceptoPago mapearConceptoPago(ResultSet resultado) throws SQLException {
        ConceptoPago concepto = new ConceptoPago();
        concepto.setCodigo(resultado.getInt("id"));
        concepto.setDescripcion(resultado.getString("descripcion"));
        concepto.setMonto(resultado.getDouble("monto"));
        concepto.setMetodoPago(resultado.getString("metodo_pago"));
        concepto.setReciboID(resultado.getInt("recibo_id"));
        
        return concepto;
    }
    
}
