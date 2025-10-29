/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.ListarClientesView;

/**
 *
 * @author diana
 */
public final class ListaClientesController {
    ListarClientesView vistaListaCliente;
    private final NavegacionCliente navegacion;
    
    public ListaClientesController(ListarClientesView vistaCliente, NavegacionCliente navegacion){
        this.vistaListaCliente = vistaCliente;
        this.navegacion = navegacion;
        configurarEventos();
    }
    
    public void configurarEventos(){
        vistaListaCliente.getBotonAgregarCliente().addActionListener(e -> navegacion.mostrarAgregarCliente());
        vistaListaCliente.getBotonBuscarCliente().addActionListener(e -> navegacion.mostrarBuscarCliente());
    }
    
    public ListarClientesView getVistaListaCliente() {
        return vistaListaCliente;
    }
    
}
