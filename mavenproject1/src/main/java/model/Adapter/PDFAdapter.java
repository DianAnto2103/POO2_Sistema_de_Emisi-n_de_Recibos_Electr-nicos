/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Adapter;

import java.util.List;
import model.*;

/**
 *
 * @author diana
 */
public class PDFAdapter implements InterfacePDF {
    private final ServicioPDF generadorPDF;
    
    public PDFAdapter() {
        this.generadorPDF = new ServicioPDF();
    }
    
    @Override
    public boolean generar(Recibo recibo, List<ConceptoPago> conceptos, double totalBase, double totalConDescuento, String rutaDestino) {
        return generadorPDF.generarPDF(recibo, conceptos, totalBase, totalConDescuento, rutaDestino);
    }
}
