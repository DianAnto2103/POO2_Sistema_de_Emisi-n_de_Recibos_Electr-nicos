/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Cliente;

/**
 *
 * @author diana
 */
public class ConvertidorModelo {
    
    public static DefaultTableModel clientesATableModelo(List<Cliente> clientes)
    {
        String[] columnNames = {"N°", "RUC", "Razón Social", "Teléfono", "Mensualidad"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Cliente cliente : clientes) { //esto recorre cliente por cliente
            Object[] row = {
                cliente.getID(),
                cliente.getRUC(),
                cliente.getRazonSocial(),
                cliente.getTelefono(),
                "S/ " + String.format("%.2f", cliente.getMensualidad())
            };
            model.addRow(row);
        }
        return model;   
    }
}
