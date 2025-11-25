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
import model.Adapter.PDFAdapter;
import java.util.List;
import model.Cliente;
import model.ConceptoPago;
import model.Recibo;

public class FacadeRecibos {
    private final GeneradorNumeracionService generadorNumeros;
    private final BusquedaClienteService busquedaCliente;
    private final RepositorioRecibo repoRecibo;
    private final RepositorioCliente repoCliente;
    private final ValidadorConceptoService validadorConcepto;
    private final CalculadoraTotalService calculadoraTotal;
    private final RepositorioConceptoPago repoConcepto;
    private final GeneracionRecibosService generadorPDF;
    
    public FacadeRecibos() {
        this.generadorNumeros = new GeneradorNumeracionService();
        this.busquedaCliente = new BusquedaClienteService();
        this.validadorConcepto = new ValidadorConceptoService();
        this.calculadoraTotal = new CalculadoraTotalService();
        this.repoRecibo = new RepositorioReciboImp();
        this.repoCliente = new RepositorioClienteImp();
        this.repoConcepto = new RepositorioConceptoPagoImp();
        this.generadorPDF = new GeneracionRecibosService();
        
    }
    
    //generar numero recibo nuevo
    public String generarNuevoNumeroRecibo() {
        // 1. Obtener último número de la BD
        String ultimoNumero = repoRecibo.obtenerUltimoNumeroRecibo();
        
        // 2. Usar el servicio para generar el nuevo número
        return generadorNumeros.generarNumeroRecibo(ultimoNumero);
    }
    
    //obtener nombre de clientes en lista
    public List<String> obtenerNombresClientes() {
        List<Cliente> clientes = repoCliente.buscarTodos();
        return busquedaCliente.extraerNombresClientes(clientes);
    }
    
    public Cliente buscarClientePorNombre(String razonSocial) {
        List<Cliente> clientes = repoCliente.buscarTodos();
        return busquedaCliente.buscarPorRazonSocialExacto(clientes, razonSocial);
    }
    
    public boolean agregarConcepto(ConceptoPago concepto) {
        return validadorConcepto.validarConcepto(concepto);
    }
    
    public double calcularTotalConceptos(List<ConceptoPago> conceptos) {
        return calculadoraTotal.calcularTotal(conceptos);
    }
    
    public String formatearTotal(double total) {
        return calculadoraTotal.formatearTotal(total);
    }
    
     public boolean superaLimiteTotal(double total) {
        return calculadoraTotal.superaLimite(total, 10000.00); // HU5: S/ 10,000
    }
     
    public boolean generarReciboCompleto(String rucCliente, List<ConceptoPago> conceptos) {
        return generadorPDF.generarReciboCompleto(rucCliente, conceptos);
    }
}
