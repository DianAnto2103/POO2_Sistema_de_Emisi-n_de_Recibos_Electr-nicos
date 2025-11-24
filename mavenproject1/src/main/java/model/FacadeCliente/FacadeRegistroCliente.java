/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.*;

/**
 *
 * @author diana
 */
public class FacadeRegistroCliente {
    private final RepositorioCliente repoCliente;
    private final ValidadorClienteFR validador;
    private final CreadorClienteFR creador;
    
    public FacadeRegistroCliente() {
        this.repoCliente = new RepositorioClienteImp();
        this.validador = new ValidadorClienteFR();
        this.creador = new CreadorClienteFR();
    }

    public boolean registrarCliente(String ruc, String razonSocial, String telefono, double mensualidad) 
    {
        // PRIMERO: Validar formato de datos
        if (!validador.validarFormatoDatos(ruc, razonSocial, telefono, mensualidad)) {
            return false;
        }
        
        // SEGUNDO: Validar que no sea duplicado
        if (validador.existeRUC(repoCliente, ruc)) {
            return false;
        }
        
        // TERCERO: Crear y guardar cliente
        return creador.crearYGuardar(repoCliente, ruc, razonSocial, telefono, mensualidad);
    }
    
}
