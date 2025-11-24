/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

import javax.swing.table.DefaultTableModel;
import model.FacadeCliente.FacadeListadoClientes;
import vista.ListarClientesView;

/**
 *
 * @author diana,flavia,darien
 */
public final class ListaClientesController {
    //se trae la vista
    private final ListarClientesView vistaListaCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
    //Se trae a facadeCliente
    private final FacadeListadoClientes facadeCliente;
    
     //este es el constructor, donde se trae la vista y la navegación. 
    public ListaClientesController(ListarClientesView vistaCliente, NavegacionCliente navegacion){
        this.vistaListaCliente = vistaCliente;
        this.navegacion = navegacion;
        //Se iniacializa facade cliente
        this.facadeCliente = new FacadeListadoClientes();
        configurarEventos(); //esto es para la configuracion de eventos como => ir a la pantalla agregar cleinte, ir a la pantalla buscar cliente. 
        cargarClientes();
    }
    
    public void configurarEventos(){
        vistaListaCliente.getBotonAgregarCliente().addActionListener(e -> navegacion.mostrarAgregarCliente());
        vistaListaCliente.getBotonBuscarCliente().addActionListener(e -> navegacion.mostrarModificarCliente());
    }
    
    public void cargarClientes(){
        DefaultTableModel modelo = facadeCliente.obtenerClientesParaTabla();
        vistaListaCliente.getTablaCliente().setModel(modelo);
    }
    
    
    public ListarClientesView getVistaListaCliente() {
        return vistaListaCliente;
    }
    
    
}
