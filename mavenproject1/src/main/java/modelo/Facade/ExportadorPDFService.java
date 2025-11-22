/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Facade;
import model.Recibo;
import model.ConceptoPago;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 *
 * @author Flavia
 */
public class ExportadorPDFService {
    
    private SimpleDateFormat formato;
    
    public ExportadorPDFService() {
        this.formato = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    public boolean exportarRecibo(Recibo recibo, List<ConceptoPago> conceptos, String rutaDestino) {
        try {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║   GENERANDO PDF (Simulación)      ║");
            System.out.println("╚════════════════════════════════════╝\n");
            
            System.out.println("Recibo: " + recibo.getNumeroRecibo());
            System.out.println("Cliente: " + recibo.getCliente().getRazonSocial());
            System.out.println("RUC: " + recibo.getCliente().getRUC());
            System.out.println("Fecha: " + formato.format(recibo.getFechaEmision()));
            System.out.println("\nCONCEPTOS:");
            
            double total = 0.0;
            for (ConceptoPago c : conceptos) {
                System.out.println("  - " + c.getDescripcion() + ": S/ " + String.format("%.2f", c.getMonto()));
                total += c.getMonto();
            }
            
            System.out.println("\nTOTAL: S/ " + String.format("%.2f", total));
            System.out.println("\n✓ PDF generado en: " + rutaDestino + "\n");
            
            return true;
            
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            return false;
        }
    }
}
