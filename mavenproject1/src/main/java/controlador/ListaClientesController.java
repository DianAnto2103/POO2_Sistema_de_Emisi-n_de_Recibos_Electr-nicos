/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.ListarClientesView;

/**
 *
 * @author diana,flavia,darien
 */
public final class ListaClientesController {
    //se trae la vista
    ListarClientesView vistaListaCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
     //este es el constructor, donde se trae la vista y la navegación. 
    public ListaClientesController(ListarClientesView vistaCliente, NavegacionCliente navegacion){
        this.vistaListaCliente = vistaCliente;
        this.navegacion = navegacion;
        configurarEventos(); //esto es para la configuracion de eventos como => ir a la pantalla agregar cleinte, ir a la pantalla buscar cliente. 
    }
    
    public void configurarEventos(){
        vistaListaCliente.getBotonAgregarCliente().addActionListener(e -> navegacion.mostrarAgregarCliente());
        vistaListaCliente.getBotonBuscarCliente().addActionListener(e -> navegacion.mostrarBuscarCliente());
    }
    
    public ListarClientesView getVistaListaCliente() {
        return vistaListaCliente;
    }
    
}
