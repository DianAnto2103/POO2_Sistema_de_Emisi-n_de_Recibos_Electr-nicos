/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Repository;

import java.util.List;
import model.Cliente;

/**
 *
 * @author diana
 */
public interface RepositorioCliente 
{
    Cliente buscarPorID(int id);
    List<Cliente> buscarTodos();
    List<Cliente> buscarPorEjemplo(Cliente cliente);  
    void guardar(Cliente cliente);
    void actualizar(Cliente cliente);
    void eliminar(Cliente cliente);
}
