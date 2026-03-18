/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

import model.FacadeCliente.FacadeBorrarCliente;
import vista.ListarClientesView;

/**
 *
 * @author diana
 */
public class BorrarClientesController {
    private ListarClientesView listaClientesView;
    private FacadeBorrarCliente facadeClientes; 
    private int idCliente;
    
    public BorrarClientesController(ListarClientesView listaClientesView, FacadeBorrarCliente facadeClientes, int idCliente){
        this.listaClientesView = new ListarClientesView();
        this.facadeClientes = new FacadeBorrarCliente();
        this.idCliente = idCliente;
        
        configurarEventos();
    }
    
    public final void configurarEventos(){
        listaClientesView.getBotonEliminarCliente().addActionListener(e -> borrarCliente());
    }
    
    public void borrarCliente(){
        facadeClientes.borrarCliente(idCliente);
    }
    
    
}
