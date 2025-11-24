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
import java.util.List;
import model.Cliente;

public class FacadeRecibos {
    private final GeneradorNumeracionService generadorNumeros;
    private final BusquedaClienteService busquedaCliente;
    private final RepositorioRecibo repoRecibo;
    private final RepositorioCliente repoCliente;
    
    public FacadeRecibos() {
        this.generadorNumeros = new GeneradorNumeracionService();
        this.busquedaCliente = new BusquedaClienteService();
        this.repoRecibo = new RepositorioReciboImp();
        this.repoCliente = new RepositorioClienteImp();
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
        return busquedaCliente.buscarPorNombreExacto(clientes, razonSocial);
    }
}
