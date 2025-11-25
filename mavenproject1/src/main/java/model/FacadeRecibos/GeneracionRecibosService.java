/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

/**
 *
 * @author diana
 */
import Repository.*;
import model.Adapter.InterfacePDF;
import model.Cliente;
import model.ConceptoPago;
import model.Recibo;
import java.util.List;
import model.Adapter.PDFAdapter;

public class GeneracionRecibosService {
    private final RepositorioRecibo repoRecibo;
    private final RepositorioCliente repoCliente;
    private final InterfacePDF pdfAdapter;
    
    public GeneracionRecibosService() {
        this.repoRecibo = new RepositorioReciboImp();
        this.repoCliente = new RepositorioClienteImp();
        this.pdfAdapter = new PDFAdapter();
    }
    
    /**
     * Servicio que genera recibo completo + PDF
     */
    public boolean generarReciboCompleto(String rucCliente, List<ConceptoPago> conceptos) {
        try {
            // 1. Buscar cliente
            Cliente cliente = repoCliente.buscarPorRUC(rucCliente).get(0);
            
            // 2. Generar y guardar recibo
            Recibo recibo = crearYGuardarRecibo(cliente, conceptos);
            
            // 3. Generar PDF
            String rutaPDF = "recibos/Recibo-" + recibo.getNumeroRecibo() + ".pdf";
            return pdfAdapter.generar(recibo, conceptos, rutaPDF);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Métodos privados de lógica...
    private Recibo crearYGuardarRecibo(Cliente cliente, List<ConceptoPago> conceptos) {
        String numeroRecibo = generarNumeroRecibo();
        double total = calcularTotal(conceptos);
        
        Recibo recibo = new Recibo();
        recibo.setNumeroRecibo(numeroRecibo);
        recibo.setCliente(cliente);
        recibo.setTotal(total);
        
        recibo.setFechaEmision(new java.util.Date());
        
        repoRecibo.guardar(recibo);
        return recibo;
    }
    
    private String generarNumeroRecibo() {
        String ultimoNumero = repoRecibo.obtenerUltimoNumeroRecibo();
        if (ultimoNumero == null) return "R-001";
        int numero = Integer.parseInt(ultimoNumero.replace("R-", "")) + 1;
        return "R-" + String.format("%03d", numero);
    }
    
    private double calcularTotal(List<ConceptoPago> conceptos) {
        return conceptos.stream().mapToDouble(ConceptoPago::getMonto).sum();
    }
    

}
