/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

/**
 *
 * @author diana
 */
import vista.ModificarClienteView;

//esta es el controlador para la vista modificar clientes
public final class ModificarClienteController {
    //se trae la vista
    private final ModificarClienteView vistaModificarCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
    
    //este es el constructor, donde se trae la vista y la navegación. 
    public ModificarClienteController(ModificarClienteView vistaModificarCliente, NavegacionCliente navegacion) {
        this.vistaModificarCliente = vistaModificarCliente; 
        this.navegacion = navegacion;
        configurarEventos(); //esto es para la configuracion de eventos como => volver a pantalla mostrarCliente, cerrarVentana, etc. 
    }
    
    public void configurarEventos(){
        vistaModificarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaModificarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
        
    }
    
    public ModificarClienteView getVistaBuscarCliente() { //un getter para que lo pueda recibir la coordinacion. Ir a Clientes Coordinador Controller
        return vistaModificarCliente;
    }   
}
