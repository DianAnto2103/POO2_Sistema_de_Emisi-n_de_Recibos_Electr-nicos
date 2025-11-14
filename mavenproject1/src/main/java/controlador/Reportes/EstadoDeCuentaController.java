/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import vista.EstadodeCuentaView;

/**
 *
 * @author diana
 */
public final class EstadoDeCuentaController {
    private final EstadodeCuentaView vistaEstadodeCuenta;
    private final NavegacionReporte navegacion;
    
    public EstadoDeCuentaController(EstadodeCuentaView vistaEstadodeCuenta, NavegacionReporte navegacion) {
        this.vistaEstadodeCuenta = vistaEstadodeCuenta;
        this.navegacion = navegacion;
        configurarEventos(); 
    }
    
    public void configurarEventos(){
        this.vistaEstadodeCuenta.getBotonVolver().addActionListener(e -> navegacion.mostrarModuloReportes());
    }
    
    public EstadodeCuentaView getVistaEstadodeCuenta() {
        return vistaEstadodeCuenta;
    }
    
}
