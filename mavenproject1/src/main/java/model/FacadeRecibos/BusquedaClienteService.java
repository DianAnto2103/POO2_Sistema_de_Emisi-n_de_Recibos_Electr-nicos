/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

import java.util.List;
import java.util.stream.Collectors;
import model.Cliente;

/**
 *
 * @author diana
 */
public class BusquedaClienteService {
    
    //Solo clientes que estan activos, los inactivos no. 
    public List<String> extraerNombresClientes(List<Cliente> clientes) {
        return clientes.stream()
                .filter(Cliente::isActivo)  // Solo activos
                .map(Cliente::getRazonSocial)
                .collect(Collectors.toList());
    }
    
    //Clientes por nombre EXACTO.
    public Cliente buscarPorNombreExacto(List<Cliente> clientes, String razonSocial) {
        return clientes.stream()
                .filter(c -> c.getRazonSocial().equals(razonSocial))
                .findFirst()
                .orElse(null);
    }
}
