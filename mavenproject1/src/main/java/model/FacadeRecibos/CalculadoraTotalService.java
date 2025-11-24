/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

import java.util.List;
import model.ConceptoPago;

/**
 *
 * @author diana
 */
public class CalculadoraTotalService {
    
    //Calcula el total sumando todos los montos de conceptos
    public double calcularTotal(List<ConceptoPago> conceptos) {
        return conceptos.stream()
                .mapToDouble(ConceptoPago::getMonto)
                .sum();
    }
    
    
    public double recalcularTotalAlAgregar(double totalActual, ConceptoPago nuevoConcepto) {
        return totalActual + nuevoConcepto.getMonto();
    }
    
    //Recalcula total después de eliminar un concepto
    public double recalcularTotalAlEliminar(double totalActual, ConceptoPago conceptoEliminado) {
        return totalActual - conceptoEliminado.getMonto();
    }
    
    //Formatea el total como moneda "S/ 1,250.00"
    public String formatearTotal(double total) {
        return String.format("S/ %,.2f", total);
    }
    
    // Valida si el total supera un límite (para HU5 - resaltar > S/ 10,000)
    public boolean superaLimite(double total, double limite) {
        return total > limite;
    }
}
