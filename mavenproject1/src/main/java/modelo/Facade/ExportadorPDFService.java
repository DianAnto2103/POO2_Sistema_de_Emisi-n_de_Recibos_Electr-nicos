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
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
}