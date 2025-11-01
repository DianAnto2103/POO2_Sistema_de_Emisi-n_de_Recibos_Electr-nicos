/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import vista.EstadodeCuenta;

/**
 *
 * @author diana
 */
public final class EstadoDeCuentaController {
    private final EstadodeCuenta vistaEstadodeCuenta;
    private final NavegacionReporte navegacion;
    
    public EstadoDeCuentaController(EstadodeCuenta vistaEstadodeCuenta, NavegacionReporte navegacion) {
        this.vistaEstadodeCuenta = vistaEstadodeCuenta;
        this.navegacion = navegacion;
        configurarEventos(); 
    }
    
    public void configurarEventos(){
        this.vistaEstadodeCuenta.getBotonVolver().addActionListener(e -> navegacion.mostrarModuloReportes());
    }
    
    public EstadodeCuenta getVistaEstadodeCuenta() {
        return vistaEstadodeCuenta;
    }
    
}
