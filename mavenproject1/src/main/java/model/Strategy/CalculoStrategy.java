/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Strategy;
import model.Cliente;

/**
 *
 * @author Flavia
 */
public interface CalculoStrategy {

    double calcularMontoFinal(double montoBase, Cliente cliente);
    String obtenerDescripcion();
    String getTipo();
}
    
