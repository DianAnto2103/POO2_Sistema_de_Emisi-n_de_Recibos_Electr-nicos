/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.Clientes.ClientesCoordinadorController;
import javax.swing.*;
import vista.ListarClientesView;
import vista.MenuPrincipalView;
import vista.ModuloReportesView;
import vista.ReciboView;

/**
 *
 * @author diana
 */

//este es el controller para la vista principal. 
public class MenuController {
    private MenuPrincipalView vistaMenu;
    
    public MenuController(MenuPrincipalView vistaMenu){
        this.vistaMenu = vistaMenu;
        this.vistaMenu.getBotonRecibo().addActionListener(e -> abrirVentanaRecibo());
        this.vistaMenu.getBotonClientes().addActionListener(e -> abrirVentanaClientes());
        this.vistaMenu.getBotonReportes().addActionListener(e -> abrirVentanaReportes());
    }
    
    public void abrirVentanaRecibo(){
        ReciboView vistaRecibo = new ReciboView();
        JFrame ventanaFlotante1 = new JFrame("Registro de pagos");
        
        RegistroController registroController = new RegistroController(ventanaFlotante1, vistaRecibo);
        
        ventanaFlotante1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaFlotante1.getContentPane().add(vistaRecibo);
        ventanaFlotante1.pack();
        ventanaFlotante1.setLocationRelativeTo(null);
        ventanaFlotante1.setResizable(false);
        ventanaFlotante1.setVisible(true);
    }
    
    public void abrirVentanaClientes(){
        JFrame ventanaFlotante2 = new JFrame("Clientes");
        
        ClientesCoordinadorController coordinator = new ClientesCoordinadorController (ventanaFlotante2); /*se pasa el coordinador
        asi lo de aqui lo toma en cuenta. Solo se comunica con el coordinador.*/ 
        
        ventanaFlotante2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaFlotante2.pack();
        ventanaFlotante2.setLocationRelativeTo(null);
        ventanaFlotante2.setResizable(false);
        ventanaFlotante2.setVisible(true);
    }
    
    
    public void abrirVentanaReportes(){
        ModuloReportesView vistaReportes = new ModuloReportesView();
        JFrame ventanaFlotante3 = new JFrame("Reportes");
        
        ventanaFlotante3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaFlotante3.getContentPane().add(vistaReportes);
        ventanaFlotante3.pack();
        ventanaFlotante3.setLocationRelativeTo(null);
        ventanaFlotante3.setResizable(false);
        ventanaFlotante3.setVisible(true);
    }
    
    
}
