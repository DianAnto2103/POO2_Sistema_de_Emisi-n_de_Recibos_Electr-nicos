/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.RepositorioCliente;
import model.Cliente;

/**
 *
 * @author diana
 */
public class ActualizadorCliente {
    //Proceso de actualización
    public boolean actualizar(RepositorioCliente repo, int idCliente, 
                             String razonSocial, String telefono, double mensualidad) {
        Cliente cliente = repo.buscarPorID(idCliente);
        if (cliente == null) return false;
        
        cliente.setRazonSocial(razonSocial);
        cliente.setTelefono(telefono);
        cliente.setMensualidad(mensualidad);
        
        repo.actualizar(cliente);
        return true;
    } 
}
