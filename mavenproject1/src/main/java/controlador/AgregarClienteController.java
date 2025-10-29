/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author diana
 */

import vista.AgregarClienteView;


public final class AgregarClienteController {
    private final AgregarClienteView vistaAgregarCliente;
    private final NavegacionCliente navegacion;
    
    public AgregarClienteController(AgregarClienteView vistaAgregarCliente, NavegacionCliente navegacion) {
        this.vistaAgregarCliente = vistaAgregarCliente;
        this.navegacion = navegacion;
        configurarEventos();
    }
    
    public void configurarEventos(){
        vistaAgregarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaAgregarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
    }
    
    public AgregarClienteView getVistaAgregarCliente() {
        return vistaAgregarCliente;
    }
    
}
