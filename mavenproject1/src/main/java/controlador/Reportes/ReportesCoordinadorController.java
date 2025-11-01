/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import javax.swing.*;
import vista.EstadodeCuenta;
import vista.ListaClientesDeudores;
import vista.ModuloReportesView;

/**
 *
 * @author diana
 */
public final class ReportesCoordinadorController implements NavegacionReporte{
    private final JFrame frameReportes;
    private ListaDeudoresController controllerListaDeudores;
    private EstadoDeCuentaController controllerEstadoDeCuenta;
    private ModuloReportesController controllerModuloReportes;
    
    public ReportesCoordinadorController(JFrame frameReportes) {
        this.frameReportes = frameReportes;
        inicializarControllers();
        mostrarModuloReportes();
    }
    
    public void inicializarControllers(){
        ModuloReportesView vistaModuloReportes = new ModuloReportesView();
        ListaClientesDeudores vistaListaDeudores = new ListaClientesDeudores();
        EstadodeCuenta vistaEstadodeCuenta = new EstadodeCuenta();
        
        controllerModuloReportes = new ModuloReportesController(vistaModuloReportes, this);
        controllerListaDeudores = new ListaDeudoresController(vistaListaDeudores, this);
        controllerEstadoDeCuenta = new EstadoDeCuentaController(vistaEstadodeCuenta, this);
    }
    
    
    @Override
    public void mostrarModuloReportes() {
        mostrarVista(controllerModuloReportes.getVistaModuloReporte());
        
    }

    @Override
    public void mostrarListaDeudores() {
        mostrarVista(controllerListaDeudores.getVistaListaDeudores());
        
    }

    @Override
    public void mostrarEstadoDeCuenta() {
        mostrarVista(controllerEstadoDeCuenta.getVistaEstadodeCuenta());
        
    }

    @Override
    public void cerrarVentana() {
        frameReportes.dispose();
        
    }
    
    private void mostrarVista(JPanel vista) { 
        frameReportes.getContentPane().removeAll();
        frameReportes.getContentPane().add(vista);
        frameReportes.pack();
        frameReportes.setLocationRelativeTo(null);
        frameReportes.setResizable(false);
        frameReportes.revalidate();
        frameReportes.repaint();
    }
}
