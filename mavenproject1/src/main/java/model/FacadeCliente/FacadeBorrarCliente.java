/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.RepositorioClienteImp;
import model.Cliente;

/**
 *
 * @author diana
 */
public class FacadeBorrarCliente {
    private RepositorioClienteImp repoCliente;
    private BorrarCliente borrador; 
    
    public FacadeBorrarCliente(){
        this.repoCliente = new RepositorioClienteImp();
        this.borrador = new BorrarCliente();
    }
    
    //metodo para borrarCliente
    public boolean borrarCliente(int IDCliente){
        return borrador.borrar(repoCliente, IDCliente);
    }
    
    public Cliente obtenerClientePorID(int idCliente) {
        return repoCliente.buscarPorID(idCliente);
    }
    
}
