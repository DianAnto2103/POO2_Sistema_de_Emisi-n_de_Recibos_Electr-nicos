/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Facade;
import model.Cliente;
import model.Recibo;
import model.ConceptoPago;
import Repository.RepositorioRecibo;
import Repository.RepositorioReciboImp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavia
 */
public class ReciboService {
    
    private RepositorioRecibo repositorioRecibo;
    
    public ReciboService() {
        this.repositorioRecibo = new RepositorioReciboImp();
    }
    
    
    public Recibo generarNuevoRecibo(Cliente cliente) {
        // Obtener el último número de recibo
        String ultimoNumero = repositorioRecibo.obtenerUltimoNumeroRecibo();
        String nuevoNumero = generarSiguienteNumero(ultimoNumero);
        
        Recibo recibo = new Recibo();
        recibo.setNumeroRecibo(nuevoNumero);
        recibo.setFechaEmision(new Date(System.currentTimeMillis()));
        recibo.setCliente(cliente);
        recibo.setTotal(0.0);
        recibo.setConceptos(new ArrayList<>());
        
        return recibo;
    }
    
    /**
     * Guardar recibo en base de datos
     */
    public boolean guardarRecibo(Recibo recibo) {
        try {
            repositorioRecibo.guardar(recibo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Actualizar recibo existente
     */
    public boolean actualizarRecibo(Recibo recibo) {
        try {
            repositorioRecibo.actualizar(recibo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Eliminar recibo (eliminación lógica)
     */
    public boolean eliminarRecibo(Recibo recibo) {
        try {
            repositorioRecibo.eliminar(recibo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtener recibo por ID
     */
    public Recibo obtenerReciboPorID(int id) {
        return repositorioRecibo.buscarPorID(id);
    }
    
    /**
     * Buscar recibo por número (busca en todos y compara)
     */
    public Recibo obtenerReciboPorNumero(String numeroRecibo) {
        List<Recibo> todos = repositorioRecibo.buscarTodos();
        for (Recibo recibo : todos) {
            if (recibo.getNumeroRecibo().equals(numeroRecibo)) {
                return recibo;
            }
        }
        return null;
    }
    
    /**
     * Obtener todos los recibos de un cliente
     */
    public List<Recibo> obtenerRecibosPorCliente(int clienteID) {
        return repositorioRecibo.buscarPorCliente(clienteID);
    }
    
    /**
     * Buscar recibos por rango de fechas
     */
    public List<Recibo> obtenerRecibosPorFecha(java.util.Date fechaInicio, java.util.Date fechaFin) {
        return repositorioRecibo.buscarPorFecha(fechaInicio, fechaFin);
    }
    
    /**
     * Listar todos los recibos
     */
    public List<Recibo> listarTodos() {
        return repositorioRecibo.buscarTodos();
    }
    
    /**
     * Agregar concepto a un recibo
     */
    public void agregarConcepto(Recibo recibo, ConceptoPago concepto) {
        if (recibo.getConceptos() == null) {
            recibo.setConceptos(new ArrayList<>());
        }
        recibo.getConceptos().add(concepto);
        recibo.setTotal(recibo.getTotal() + concepto.getMonto());
    }
    
    /**
     * Calcular total de un recibo
     */
    public double calcularTotal(Recibo recibo) {
        if (recibo.getConceptos() == null || recibo.getConceptos().isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (ConceptoPago concepto : recibo.getConceptos()) {
            total += concepto.getMonto();
        }
        return total;
    }
    
    /**
     * Generar siguiente número de recibo
     */
    private String generarSiguienteNumero(String ultimoNumero) {
        try {
            // Extraer el número del formato "R-001"
            String[] partes = ultimoNumero.split("-");
            if (partes.length == 2) {
                int numero = Integer.parseInt(partes[1]);
                numero++;
                return String.format("R-%03d", numero);
            }
        } catch (Exception e) {
            // Si hay error, retornar número por defecto
        }
        return "R-001";
    }
}
