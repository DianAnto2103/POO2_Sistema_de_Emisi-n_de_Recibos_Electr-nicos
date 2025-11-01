/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import vista.ModuloReportesView;

/**
 *
 * @author diana
 */
public final class ModuloReportesController {
    private final ModuloReportesView vistaModuloReporte;
    private final NavegacionReporte navegacion;

    public ModuloReportesController(ModuloReportesView vistaModuloReporte, NavegacionReporte navegacion) {
        this.vistaModuloReporte = vistaModuloReporte;
        this.navegacion = navegacion;
        configurarEventos();
    }
    
    public void configurarEventos(){
        vistaModuloReporte.getBotonDeudores().addActionListener(e -> navegacion.mostrarListaDeudores());
        vistaModuloReporte.getBotonEstado().addActionListener(e -> navegacion.mostrarEstadoDeCuenta());
        
    }
    
    public ModuloReportesView getVistaModuloReporte() {
        return vistaModuloReporte;
    }  
}
