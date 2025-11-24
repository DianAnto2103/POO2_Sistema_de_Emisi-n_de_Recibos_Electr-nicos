/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Cliente;

/**
 *
 * @author diana
 */
public class FacadeListadoClientes {
    private RepositorioCliente repoCliente;
    
    public FacadeListadoClientes() 
    {
        this.repoCliente = new RepositorioClienteImp();
    }
    
    public DefaultTableModel obtenerClientesParaTabla() {
        List<Cliente> clientes = repoCliente.buscarTodos();
        return ConvertidorModelo.clientesATableModelo(clientes);
    }
    
}
