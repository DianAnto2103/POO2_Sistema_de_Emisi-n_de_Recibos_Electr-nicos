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
    
    private static final double PORCENTAJE_RECARGO = 0.15; // 15%
    
    @Override
    public double calcularMontoFinal(double montoBase, Cliente cliente) {
        double recargo = montoBase * PORCENTAJE_RECARGO;
        double montoFinal = montoBase + recargo;
        
        System.out.println("═══════════════════════════════════════════");
        System.out.println("   RECARGO POR MORA");
        System.out.println("═══════════════════════════════════════════");
        System.out.println(String.format("  Cliente: %s", cliente.getRazonSocial()));
        System.out.println(String.format("  Monto base: S/ %.2f", montoBase));
        System.out.println(String.format("  Recargo (15%%): S/ %.2f", recargo));
        System.out.println(String.format("  Monto final: S/ %.2f", montoFinal));
        System.out.println("  ATENCIÓN: Pago con atraso");
        System.out.println("═══════════════════════════════════════════\n");
        
        return montoFinal;
    }
    
    @Override
    public String obtenerDescripcion() {
        return "Recargo por mora - 15% adicional por pago atrasado";
    }
    
    @Override
    public String getTipo() {
        return "RECARGO_MORA";
    }    
}
