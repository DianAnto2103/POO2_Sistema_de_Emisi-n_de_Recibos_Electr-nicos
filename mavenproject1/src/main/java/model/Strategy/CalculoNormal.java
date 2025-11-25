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
public class CalculoNormal implements CalculoStrategy {
    
    @Override
    public double calcularMontoFinal(double montoBase, Cliente cliente) {
        return montoBase;
    }
    
    @Override
    public String obtenerDescripcion() {
        return "Pago normal - Sin descuentos ni recargos";
    }
    
    @Override
    public String getTipo() {
        return "NORMAL";
    }
}