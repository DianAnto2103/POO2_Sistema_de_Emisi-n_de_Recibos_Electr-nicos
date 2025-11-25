/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeReportes;

/**
 *
 * @author diana
 */
import Repository.*;
import model.Cliente;
import model.Recibo;
import java.util.*;

public class EstadoCuentaService {
    private final RepositorioCliente repoCliente;
    private final RepositorioRecibo repoRecibo;
    
    public EstadoCuentaService() {
        this.repoCliente = new RepositorioClienteImp();
        this.repoRecibo = new RepositorioReciboImp();
    }
    
    public Map<String, Object> prepararDatosEstadoCuenta(String rucCliente, Date fechaInicio, Date fechaFin) {
        Map<String, Object> datos = new HashMap<>();
        
        // 1. Buscar cliente por RUC
        List<Cliente> clientes = repoCliente.buscarPorRUC(rucCliente);
        if (clientes.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado: " + rucCliente);
        }
        Cliente cliente = clientes.get(0);
        datos.put("cliente", cliente);
        
        // 2. Buscar recibos por cliente y rango de fechas
        List<Recibo> recibos = repoRecibo.buscarPorFecha(fechaInicio, fechaFin);
        List<Recibo> recibosCliente = new ArrayList<>();
        
        // Filtrar solo los recibos del cliente específico
        for (Recibo recibo : recibos) {
            if (recibo.getCliente().getID() == cliente.getID()) {
                recibosCliente.add(recibo);
            }
        }
        datos.put("recibos", recibosCliente);
        
        // 3. Cálculos
        double totalPagado = calcularTotalPagado(recibosCliente);
        Date ultimoPago = obtenerUltimoPago(recibosCliente);
        
        datos.put("totalPagado", totalPagado);
        datos.put("saldoPendiente", Math.max(0, cliente.getMensualidad() - totalPagado));
        datos.put("ultimoPago", ultimoPago);
        datos.put("fechaInicio", fechaInicio);
        datos.put("fechaFin", fechaFin);
        datos.put("fechaGeneracion", new Date());
        
        return datos;
    }
    
    private double calcularTotalPagado(List<Recibo> recibos) {
        double total = 0;
        for (Recibo recibo : recibos) {
            total += recibo.getTotal();
        }
        return total;
    }
    
    private Date obtenerUltimoPago(List<Recibo> recibos) {
        if (recibos.isEmpty()) return null;
        Date ultimaFecha = recibos.get(0).getFechaEmision();
        for (Recibo recibo : recibos) {
            if (recibo.getFechaEmision().after(ultimaFecha)) {
                ultimaFecha = recibo.getFechaEmision();
            }
        }
        return ultimaFecha;
    }
    
    public List<Cliente> buscarClientesPorRUC(String ruc) {
        return repoCliente.buscarPorRUC(ruc);
    }
    
    public List<Cliente> buscarClientesPorRazonSocial(String razonSocial) {
        return repoCliente.buscarPorRazonSocial(razonSocial);
    }
}
