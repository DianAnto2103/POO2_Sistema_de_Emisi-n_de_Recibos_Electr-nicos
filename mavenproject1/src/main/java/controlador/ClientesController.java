/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import vista.AgregarClienteView;
import vista.ListarClientesView;

/**
 *
 * @author diana
 */
public class ClientesController {
    ListarClientesView vistaCliente;
    private JFrame frameClientes;
    
    public ClientesController(ListarClientesView vistaCliente, JFrame frameClientes){
        this.vistaCliente = vistaCliente;
        this.frameClientes = frameClientes;
        this.vistaCliente.getBotonAgregarCliente().addActionListener(e -> abrirVentanaAgregar());
    }
    
    public void abrirVentanaAgregar()
    {
        AgregarClienteView vistaAgregarCliente = new AgregarClienteView();
        
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vistaAgregarCliente);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
        
        
    }
    
}
