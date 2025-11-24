/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

import Repository.*;
import java.util.List;
import model.Cliente;

/**
 *
 * @author diana
 */
public class ValidadorClienteFR {
    public boolean validarFormatoDatos(String ruc, String razonSocial, String telefono, double mensualidad){
        // Verificar que el RUC tenga exactamente 11 dígitos (formato perueano)
        boolean rucValido = ruc != null && ruc.length() == 11;
        // Verificar que la razón social no esté vacía
        boolean razonSocialValida = razonSocial != null && !razonSocial.trim().isEmpty();
        // Verificar que la mensualidad sea un valor positivo
        boolean mensualidadValida = mensualidad > 0;
        //Todos los datos deben ser válidos para continuar
        return rucValido && razonSocialValida && mensualidadValida;
    }
    
    public boolean existeRUC(RepositorioCliente repositorioCliente, String ruc) {
        // Consulta la base de datos buscando clientes con el mismo RUC
        List<Cliente> clientesExistentes = repositorioCliente.buscarPorRUC(ruc);
        // Si la lista no está vacía, significa que el RUC ya está registrado
        boolean rucYaExiste = !clientesExistentes.isEmpty();
        
        return rucYaExiste;
    }
    
}
