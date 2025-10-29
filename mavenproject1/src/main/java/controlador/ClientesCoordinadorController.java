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
import vista.BuscarClienteView;
import vista.ListarClientesView;
import javax.swing.*;

public final class ClientesCoordinadorController implements NavegacionCliente {
    private final JFrame frameClientes;
    private ListaClientesController controllerListaClientes;
    private AgregarClienteController controllerAgregarClientes;
    private BuscarClienteController controllerBuscarClientes;
    
    public ClientesCoordinadorController(JFrame frameClientes){
        this.frameClientes = frameClientes;
        inicializarControllers();
        mostrarListaClientes(); 
    }
    
    
    public void inicializarControllers(){
        ListarClientesView vistaListaClientes = new ListarClientesView();
        AgregarClienteView vistaAgregarClientes = new AgregarClienteView();
        BuscarClienteView vistaBuscarClientes = new BuscarClienteView();
        
        this.controllerListaClientes = new ListaClientesController(vistaListaClientes, this);
        this.controllerAgregarClientes= new AgregarClienteController(vistaAgregarClientes, this);
        this.controllerBuscarClientes = new BuscarClienteController(vistaBuscarClientes, this);
    }
    
    @Override
    public void mostrarListaClientes(){
        mostrarVista(controllerListaClientes.getVistaListaCliente());
    }
    @Override
    public void mostrarAgregarCliente(){
        mostrarVista(controllerAgregarClientes.getVistaAgregarCliente());
    }
    @Override
    public void mostrarBuscarCliente(){
        mostrarVista(controllerBuscarClientes.getVistaBuscarCliente());
    }
    
    @Override
    public void cerrarVentana(){
        frameClientes.dispose();
    }
    
    private void mostrarVista(JPanel vista) {
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vista);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
    }
}
