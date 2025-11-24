/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.*;
import model.Cliente;

/**
 *
 * @author diana
 */
public class FacadeActualizarCliente {
    private final RepositorioClienteImp repoCliente;
    private final ValidadorActualizacionCliente validador;
    private final ActualizadorCliente actualizador;
    
    public FacadeActualizarCliente() {
        this.repoCliente = new RepositorioClienteImp();
        this.validador = new ValidadorActualizacionCliente();
        this.actualizador = new ActualizadorCliente();
    }
    
    //metodo para actualizar cliente
    public boolean actualizarCliente(int idCliente, String razonSocial, String telefono, double mensualidad) {
        // Usar validador
        if (!validador.validarActualizacion(razonSocial, telefono, mensualidad)) {
            return false;
        }
        
        // Usar actualizador
        return actualizador.actualizar(repoCliente, idCliente, razonSocial, telefono, mensualidad);
    }
    
    public Cliente obtenerClientePorID(int idCliente) {
        return repoCliente.buscarPorID(idCliente);
    }
    
}
