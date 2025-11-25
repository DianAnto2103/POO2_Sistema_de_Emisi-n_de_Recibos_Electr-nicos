/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Strategy;
import model.Cliente;
/**
 *
 * @author Flavia
 */
public class RecargoMora implements CalculoStrategy {
    
    private static final double PORCENTAJE_RECARGO = 0.10; // 15%
    
    @Override
    public double calcularMontoFinal(double montoBase, Cliente cliente) {
        double recargo = montoBase * PORCENTAJE_RECARGO;
        double montoFinal = montoBase + recargo;
        return montoFinal;
    }
    
    @Override
    public String obtenerDescripcion() {
        return "Recargo por mora - 10% adicional por pago atrasado";
    }
    
    @Override
    public String getTipo() {
        return "RECARGO_MORA";
    }    
}
