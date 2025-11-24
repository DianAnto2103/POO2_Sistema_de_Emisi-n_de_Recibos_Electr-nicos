/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeCliente;

/**
 *
 * @author diana
 */
public class ValidadorActualizacionCliente {
    //Valida datos para actualización de cliente
    public boolean validarActualizacion(String razonSocial, String telefono, double mensualidad) {
        return razonSocial != null && !razonSocial.trim().isEmpty() && mensualidad > 0;
    }
    
}
