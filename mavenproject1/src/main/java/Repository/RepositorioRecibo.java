/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repository;

import java.util.Date;
import java.util.List;
import model.Recibo;

/**
 *
 * @author diana
 */
public interface RepositorioRecibo {
    Recibo buscarPorID(int id);
    List<Recibo> buscarTodos();
    List<Recibo> buscarPorCliente(int clienteId);
    List<Recibo> buscarPorFecha(Date fechaInicio, Date fechaFin);
    
    void guardar(Recibo recibo);
    void actualizar(Recibo recibo);
    void eliminar(Recibo recibo);
    
    String obtenerUltimoNumeroRecibo();
}
