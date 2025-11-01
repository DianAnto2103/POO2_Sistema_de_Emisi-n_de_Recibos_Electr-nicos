/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import vista.ReciboView;

/**
 *
 * @author diana
 */
public final class RegistroController {
    private final JFrame frameReportes;
    private final ReciboView vistaRecibo;
    
    public RegistroController(JFrame frameReportes,ReciboView vistaRecibo){
        this.frameReportes = frameReportes;
        this.vistaRecibo = vistaRecibo;
        configurarEventos();
        
    }
    
    
    public void configurarEventos(){
        vistaRecibo.getBotonSalir().addActionListener(e -> {
            cerrarVentana();
        });
    }
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
}
