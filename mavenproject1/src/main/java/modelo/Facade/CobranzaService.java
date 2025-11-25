/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Facade;

import model.Cliente;
import model.ConceptoPago;
import Repository.RepositorioConceptoPago;
import Repository.RepositorioConceptoPagoImp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavia
 */
public class CobranzaService {
private RepositorioConceptoPago repositorioConceptoPago;
    private ClienteService clienteService;
    
    public CobranzaService(ClienteService clienteService) {
        this.repositorioConceptoPago = new RepositorioConceptoPagoImp();
        this.clienteService = clienteService;
    }
    
    
    public boolean registrarPago(ConceptoPago concepto) {
        if (!validarConcepto(concepto)) {
            return false;
        }
        
        try {
            repositorioConceptoPago.guardar(concepto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean registrarPagosTodos(List<ConceptoPago> conceptos) {
        try {
            repositorioConceptoPago.guardarTodos(conceptos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public List<ConceptoPago> obtenerConceptosPorRecibo(int reciboID) {
        return repositorioConceptoPago.buscarPorRecibo(reciboID);
    }
    
    
    public ConceptoPago obtenerConceptoPorID(int id) {
        return repositorioConceptoPago.buscarPorID(id);
    }
    
    
    public boolean actualizarConcepto(ConceptoPago concepto) {
        try {
            repositorioConceptoPago.actualizar(concepto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
   
    public boolean eliminarConcepto(ConceptoPago concepto) {
        try {
            repositorioConceptoPago.eliminar(concepto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public double calcularTotalPagado(int clienteID) {
        // Aquí deberías hacer una consulta SQL personalizada
        // Por ahora retorna 0
        return 0.0;
    }
    
    
    public List<ClienteDeudor> obtenerClientesDeudores() {
        List<Cliente> activos = clienteService.listarActivos();
        List<ClienteDeudor> deudores = new ArrayList<>();
        
        for (Cliente cliente : activos) {
            double pagado = calcularTotalPagado(cliente.getID());
            double deuda = cliente.getMensualidad() - pagado;
            
            if (deuda > 0) {
                ClienteDeudor deudor = new ClienteDeudor();
                deudor.setCliente(cliente);
                deudor.setDeuda(deuda);
                deudor.setTotalPagado(pagado);
                deudores.add(deudor);
            }
        }
        
        return deudores;
    }
    
    
    private boolean validarConcepto(ConceptoPago concepto) {
        if (concepto == null) {
            return false;
        }
        
        if (concepto.getDescripcion() == null || concepto.getDescripcion().length() < 5) {
            return false;
        }
        
        if (concepto.getMonto() <= 0) {
            return false;
        }
        
        if (concepto.getMetodoPago() == null || concepto.getMetodoPago().isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    
    public static class ClienteDeudor {
        private Cliente cliente;
        private double deuda;
        private double totalPagado;
        
        public Cliente getCliente() { return cliente; }
        public void setCliente(Cliente cliente) { this.cliente = cliente; }
        
        public double getDeuda() { return deuda; }
        public void setDeuda(double deuda) { this.deuda = deuda; }
        
        public double getTotalPagado() { return totalPagado; }
        public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }
        
        @Override
        public String toString() {
            return String.format("Deudor[%s, Deuda=S/%.2f]",
                    cliente.getRazonSocial(), deuda);
        }
    }
}
