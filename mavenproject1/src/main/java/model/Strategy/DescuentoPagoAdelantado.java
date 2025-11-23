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
public class DescuentoPagoAdelantado implements CalculoStrategy {
    
    private static final double PORCENTAJE_DESCUENTO = 0.05; // 5%
    
   @Override
    public double calcularMontoFinal(double montoBase, Cliente cliente) {
        double descuento = montoBase * PORCENTAJE_DESCUENTO;
        double montoFinal = montoBase - descuento;
        
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  DESCUENTO POR PAGO ADELANTADO");
        System.out.println("═══════════════════════════════════════════");
        System.out.println(String.format("  Cliente: %s", cliente.getRazonSocial()));
        System.out.println(String.format("  Monto base: S/ %.2f", montoBase));
        System.out.println(String.format("  Descuento (5%%): S/ %.2f", descuento));
        System.out.println(String.format("  Monto final: S/ %.2f", montoFinal));
        System.out.println(String.format("  ¡Ahorro: S/ %.2f!", descuento));
        System.out.println("═══════════════════════════════════════════\n");
        
        return montoFinal;
    }
    
    @Override
    public String obtenerDescripcion() {
        return "Descuento por pago adelantado - 5% de descuento";
    }
    
    @Override
    public String getTipo() {
        return "DESCUENTO_ADELANTADO";
    }
}
