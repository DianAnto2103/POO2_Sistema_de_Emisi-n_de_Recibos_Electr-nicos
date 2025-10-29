/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import vista.AgregarClienteView;
import vista.BuscarClienteView;
import vista.ListarClientesView;

/**
 *
 * @author diana
 */
public class ClientesController {
    ListarClientesView vistaListaCliente;
    private JFrame frameClientes;
    private AgregarClienteView vistaAgregarCliente;
    private BuscarClienteView vistaBuscarCliente;
    
    public ClientesController(ListarClientesView vistaCliente, JFrame frameClientes){
        this.vistaListaCliente = vistaCliente;
        this.frameClientes = frameClientes;
        this.vistaListaCliente.getBotonAgregarCliente().addActionListener(e -> abrirVentanaAgregar());
        this.vistaListaCliente.getBotonBuscarCliente().addActionListener(e -> abrirVentanaBuscar());
    }
    
    public void abrirVentanaAgregar()
    {
        vistaAgregarCliente = new AgregarClienteView();
        
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vistaAgregarCliente);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
        
        vistaAgregarCliente.getBotonVolver().addActionListener(e -> volverALista());
        vistaAgregarCliente.getBotonSalir().addActionListener(e -> frameClientes.dispose());
    }
    
    public void abrirVentanaBuscar(){
        vistaBuscarCliente = new BuscarClienteView();
        
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vistaBuscarCliente);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
        
        vistaBuscarCliente.getBotonVolver().addActionListener(e -> volverALista());
        vistaBuscarCliente.getBotonSalir().addActionListener(e -> frameClientes.dispose());
        
    }
    
    public void volverALista(){
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vistaListaCliente);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
    }
    
}
