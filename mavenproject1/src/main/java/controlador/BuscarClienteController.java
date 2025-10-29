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

public final class BuscarClienteController {
    private final BuscarClienteView vistaBuscarCliente;
    private final NavegacionCliente navegacion;
    
    public BuscarClienteController(BuscarClienteView vistaBuscarCliente, NavegacionCliente navegacion) {
        this.vistaBuscarCliente = vistaBuscarCliente;
        this.navegacion = navegacion;
        configurarEventos();
    }
    
    public void configurarEventos(){
        vistaBuscarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaBuscarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
        
    }
    
    public BuscarClienteView getVistaBuscarCliente() {
        return vistaBuscarCliente;
    }
    
    
    
}
