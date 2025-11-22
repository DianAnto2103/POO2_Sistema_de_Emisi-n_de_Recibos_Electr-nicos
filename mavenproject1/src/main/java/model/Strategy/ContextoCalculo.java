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
public class ContextoCalculo {

    private CalculoStrategy estrategia;
    
    /**
    Usa cálculo normal
     */
    public ContextoCalculo() {
        this.estrategia = new CalculoNormal();
    }
    
    public ContextoCalculo(CalculoStrategy estrategia) {
        this.estrategia = estrategia;
    }
    
    
    public CalculoStrategy getEstrategia() {
        return estrategia;
    }
    
    /**
     * Calcula el monto final usando la estrategia actual
     */
    public double calcular(double montoBase, Cliente cliente) {
        if (estrategia == null) {
            throw new IllegalStateException("No se ha configurado una estrategia de cálculo");
        }
        return estrategia.calcularMontoFinal(montoBase, cliente);
    }
    
    /**
     * Obtiene la descripción de la estrategia actual
     */
    public String obtenerDescripcionEstrategia() {
        return estrategia != null ? estrategia.obtenerDescripcion() : "Sin estrategia";
    }

    public void setEstrategia(DescuentoPagoAdelantado descuentoPagoAdelantado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}