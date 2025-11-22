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
     * Buscar cliente por RUC (retorna el primero encontrado)
     */
    public Cliente buscarPorRUC(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) {
            System.out.println("✗ RUC vacío");
            return null;
        }
        
        List<Cliente> clientes = repositorioCliente.buscarPorRUC(ruc);
        
        if (!clientes.isEmpty()) {
            Cliente cliente = clientes.get(0);
            System.out.println("✓ Cliente encontrado: " + cliente.getRazonSocial());
            return cliente;
        }
        
        System.out.println("✗ Cliente no encontrado con RUC: " + ruc);
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
        System.out.println("✓ Clientes encontrados: " + clientes.size());
        return clientes;
    }
    
    /**
     * Buscar cliente por ID
     */
    public Cliente buscarPorID(int id) {
        Cliente cliente = repositorioCliente.buscarPorID(id);
        if (cliente != null) {
            System.out.println("✓ Cliente encontrado: " + cliente.getRazonSocial());
        }
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
            System.out.println("✗ Ya existe un cliente con el RUC: " + cliente.getRUC());
            return false;
        }
        
        try {
            repositorioCliente.guardar(cliente);
            System.out.println("✓ Cliente registrado: " + cliente.getRazonSocial());
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error al registrar cliente: " + e.getMessage());
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
            System.out.println("✓ Cliente actualizado: " + cliente.getRazonSocial());
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Eliminar cliente (eliminación lógica)
     */
    public boolean eliminarCliente(int id) {
        Cliente cliente = repositorioCliente.buscarPorID(id);
        if (cliente == null) {
            System.out.println("✗ Cliente no encontrado");
            return false;
        }
        
        try {
            cliente.setActivo(false);
            repositorioCliente.actualizar(cliente);
            System.out.println("✓ Cliente marcado como inactivo");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error al eliminar cliente: " + e.getMessage());
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
        
        System.out.println("✓ Clientes activos: " + activos.size());
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
            System.out.println("✗ Cliente no puede ser null");
            return false;
        }
        
        if (cliente.getRUC() == null || !cliente.getRUC().matches("\\d{11}")) {
            System.out.println("✗ RUC inválido (debe tener 11 dígitos)");
            return false;
        }
        
        if (cliente.getRazonSocial() == null || cliente.getRazonSocial().trim().isEmpty()) {
            System.out.println("✗ Razón social vacía");
            return false;
        }
        
        if (cliente.getTelefono() == null || !cliente.getTelefono().matches("\\d{9}")) {
            System.out.println("✗ Teléfono inválido (debe tener 9 dígitos)");
            return false;
        }
        
        if (cliente.getMensualidad() <= 0) {
            System.out.println("✗ Mensualidad debe ser mayor a 0");
            return false;
        }
        
        return true;
    }
}
