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
//este es el controlador para la vista de agregar cliente.
public final class AgregarClienteController {
    //se trae la vista
    private final AgregarClienteView vistaAgregarCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
    //este es el constructor, donde se trae la vista y la navegación. 
    public AgregarClienteController(AgregarClienteView vistaAgregarCliente, NavegacionCliente navegacion) {
        this.vistaAgregarCliente = vistaAgregarCliente;
        this.navegacion = navegacion;
        configurarEventos(); //esto es para la configuracion de eventos como => volver a pantalla mostrarCliente, cerrarVentana, etc. 
    }
    
    public void configurarEventos(){
        vistaAgregarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaAgregarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
    }
    
    public AgregarClienteView getVistaAgregarCliente() { //un getter para que lo pueda recibir la coordinacion. Ir a Clientes Coordinador Controller
        return vistaAgregarCliente;
    }
    
}
