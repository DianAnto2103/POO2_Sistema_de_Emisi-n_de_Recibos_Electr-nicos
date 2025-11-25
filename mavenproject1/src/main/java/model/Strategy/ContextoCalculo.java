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
   
    public void setEstrategia(CalculoStrategy estrategia){
        this.estrategia = estrategia;
    }
    
    /**
     * Calcula el monto final usando la estrategia actual
     * @param montoBase
     * @param cliente
     * @return 
     */
    public double calcular(double montoBase, Cliente cliente) {
        if (estrategia == null) {
            throw new IllegalStateException("No se ha configurado una estrategia de cálculo");
        }
        return estrategia.calcularMontoFinal(montoBase, cliente);
    }
    
    /**
     * Obtiene la descripción de la estrategia actual
     * @return 
     */
    public String obtenerDescripcionEstrategia() {
        return estrategia.obtenerDescripcion();
    }
    
    
    public CalculoStrategy getEstrategia() {
        return estrategia;
    }
}

    