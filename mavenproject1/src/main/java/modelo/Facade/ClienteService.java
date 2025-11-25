/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Facade;
import model.Cliente;
import Repository.RepositorioCliente;
import Repository.RepositorioClienteImp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavia
 */
public class ClienteService {

    private RepositorioCliente repositorioCliente;
    
    public ClienteService() {
        this.repositorioCliente = new RepositorioClienteImp();
    }
    
    /**
     * Buscar cliente por RUC
     */
    public Cliente buscarPorRUC(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) {
            return null;
        }
        
        List<Cliente> clientes = repositorioCliente.buscarPorRUC(ruc);
        
        if (!clientes.isEmpty()) {
            Cliente cliente = clientes.get(0);
            return cliente;
        }
        
        return null;
    }
    
    /**
     * Buscar clientes por razón social
     */
    public List<Cliente> buscarPorRazonSocial(String razonSocial) {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Cliente> clientes = repositorioCliente.buscarPorRazonSocial(razonSocial);
        return clientes;
    }
    
    /**
     * Buscar cliente por ID
     */
    public Cliente buscarPorID(int id) {
        Cliente cliente = repositorioCliente.buscarPorID(id);
        return cliente;
    }
    
    /**
     * Registrar nuevo cliente
     */
    public boolean registrarCliente(Cliente cliente) {
        if (!validarCliente(cliente)) {
            return false;
        }
        
        // Verificar si ya existe
        List<Cliente> existentes = repositorioCliente.buscarPorRUC(cliente.getRUC());
        if (!existentes.isEmpty()) {
            return false;
        }
        
        try {
            repositorioCliente.guardar(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Actualizar cliente existente
     */
    public boolean actualizarCliente(Cliente cliente) {
        if (!validarCliente(cliente)) {
            return false;
        }
        
        try {
            repositorioCliente.actualizar(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Eliminar cliente (eliminación lógica)
     */
    public boolean eliminarCliente(int id) {
        Cliente cliente = repositorioCliente.buscarPorID(id);
        if (cliente == null) {
            return false;
        }
        
        try {
            cliente.setActivo(false);
            repositorioCliente.actualizar(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Listar todos los clientes
     */
    public List<Cliente> listarTodos() {
        return repositorioCliente.buscarTodos();
    }
    
    /**
     * Listar solo clientes activos
     */
    public List<Cliente> listarActivos() {
        List<Cliente> todos = repositorioCliente.buscarTodos();
        List<Cliente> activos = new ArrayList<>();
        
        for (Cliente cliente : todos) {
            if (cliente.isActivo()) {
                activos.add(cliente);
            }
        }
        
        return activos;
    }
    
    /**
     * Verificar si un cliente existe
     */
    public boolean existeCliente(String ruc) {
        List<Cliente> clientes = repositorioCliente.buscarPorRUC(ruc);
        return !clientes.isEmpty();
    }
    
    /**
     * Validar datos del cliente
     */
    private boolean validarCliente(Cliente cliente) {
        if (cliente == null) {
            return false;
        }
        
        if (cliente.getRUC() == null || !cliente.getRUC().matches("\\d{11}")) {
            return false;
        }
        
        if (cliente.getRazonSocial() == null || cliente.getRazonSocial().trim().isEmpty()) {
            return false;
        }
        
        if (cliente.getTelefono() == null || !cliente.getTelefono().matches("\\d{9}")) {
            return false;
        }
        
        if (cliente.getMensualidad() <= 0) {
            return false;
        }
        
        return true;
    }
}