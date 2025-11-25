/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeReportes;

/**
 *
 * @author diana
 */
import java.util.*;
import model.Cliente;

public class FacadeReportes {
    private final EstadoCuentaService estadoCuentaService;
    
    public FacadeReportes() {
        this.estadoCuentaService = new EstadoCuentaService();
    }
    
    public Map<String, Object> generarEstadoCuenta(String rucCliente, Date fechaInicio, Date fechaFin) {
        return estadoCuentaService.prepararDatosEstadoCuenta(rucCliente, fechaInicio, fechaFin);
    }
    
    public List<Cliente> buscarClientesPorRUC(String ruc) {
        return estadoCuentaService.buscarClientesPorRUC(ruc);
    }
    
    public List<Cliente> buscarClientesPorRazonSocial(String razonSocial) {
        return estadoCuentaService.buscarClientesPorRazonSocial(razonSocial);
    }
}
