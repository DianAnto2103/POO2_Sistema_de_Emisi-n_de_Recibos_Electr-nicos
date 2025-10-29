/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author diana
 */
import vista.BuscarClienteView;

//esta es el controlador para la vista buscar clientes
public final class BuscarClienteController {
    //se trae la vista
    private final BuscarClienteView vistaBuscarCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
    
    //este es el constructor, donde se trae la vista y la navegación. 
    public BuscarClienteController(BuscarClienteView vistaBuscarCliente, NavegacionCliente navegacion) {
        this.vistaBuscarCliente = vistaBuscarCliente; 
        this.navegacion = navegacion;
        configurarEventos(); //esto es para la configuracion de eventos como => volver a pantalla mostrarCliente, cerrarVentana, etc. 
    }
    
    public void configurarEventos(){
        vistaBuscarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaBuscarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
        
    }
    
    public BuscarClienteView getVistaBuscarCliente() { //un getter para que lo pueda recibir la coordinacion. Ir a Clientes Coordinador Controller
        return vistaBuscarCliente;
    }
    
    
    
}
