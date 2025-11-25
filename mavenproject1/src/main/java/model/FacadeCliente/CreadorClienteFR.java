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
public class CreadorClienteFR {
    public boolean crearYGuardar(RepositorioCliente reposi, String ruc, String razonSocial, String telefono, double mensualidad) 
    {
        // Crea una nueva instancia de Cliente 
        Cliente cliente = new Cliente();
        // Asigna todos los datos recibidos del formulario
        cliente.setRUC(ruc);
        cliente.setRazonSocial(razonSocial);
        cliente.setTelefono(telefono);
        cliente.setMensualidad(mensualidad);
        // Establece valores por defecto como 'activo = true'
        cliente.setActivo(true);
        
        // Finalmente guarda el cliente en la Base de Datos
        reposi.guardar(cliente);
        return true;
    }
    
}
