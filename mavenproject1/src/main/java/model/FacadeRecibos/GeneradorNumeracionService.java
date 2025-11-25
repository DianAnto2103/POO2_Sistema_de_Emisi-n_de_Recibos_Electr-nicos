/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

/**
 *
 * @author diana
 */
public class GeneradorNumeracionService {
    
    public String generarNumeroRecibo(String ultimoNumeroDeLaBD) {
        // 1. Calcular siguiente número
        int siguienteNumero = calcularSiguienteNumero(ultimoNumeroDeLaBD);
        
        // 2. Formatear con ceros "001"
        return "R-" + String.format("%03d", siguienteNumero);
    }
    
    private int calcularSiguienteNumero(String ultimoNumero) {
        // Si no hay recibos, empezar desde 1
        if (ultimoNumero == null || ultimoNumero.equals("R-000")) {
            return 1;
        }
        
        try {
            // Extraer número del formato "R-001"
            String numeroStr = ultimoNumero.replace("R-", "");
            int ultimoNum = Integer.parseInt(numeroStr);
            
            return ultimoNum + 1;
            
        } catch (Exception e) {
            // Si hay error, empezar desde 1
            return 1;
        }
    }
}