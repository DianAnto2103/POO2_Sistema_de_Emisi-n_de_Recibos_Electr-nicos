/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import model.FacadeRecibos.FacadeRecibos;
import vista.ReciboView;

/**
 *
 * @author diana
 */
public final class RegistroController {
    private final JFrame frameReportes;
    private final ReciboView vistaRecibo;
    private final FacadeRecibos facadeRecibos;
    
    public RegistroController(JFrame frameReportes){
        this.frameReportes = frameReportes;
        this.vistaRecibo = new ReciboView();
        this.facadeRecibos = new FacadeRecibos();
        configurarEventos();
        generarNumeroReciboAutomatico();
        
    }
    
    private void generarNumeroReciboAutomatico() {
        String numeroRecibo = facadeRecibos.generarNuevoNumeroRecibo();
        vistaRecibo.getTxtNumeroRecibo().setText(numeroRecibo); // "R-001"
    }
    
    public void configurarEventos(){
        vistaRecibo.getBotonSalir().addActionListener(e -> {
            cerrarVentana();
        });
    }
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    
    public ReciboView getVistaRegistro(){
        return vistaRecibo;
    }
}
