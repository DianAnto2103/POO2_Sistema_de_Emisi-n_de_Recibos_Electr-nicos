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
public class BorrarCliente {
    //Proceso de borrar un cliente
    public boolean borrar(RepositorioCliente repo, int idCliente){
        Cliente cliente = repo.buscarPorID(idCliente);
        if(cliente == null){
            return false;
        }
        
        repo.eliminar(cliente);
        return true;
    }
}
