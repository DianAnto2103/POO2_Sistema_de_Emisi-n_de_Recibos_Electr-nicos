/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Adapter;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Recibo;
import java.io.File;
import model.ConceptoPago;
import model.Cliente;
import java.io.FileOutputStream;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 *
 * @author diana
 */
public class ServicioPDF {
    public boolean generarPDF(Recibo recibo, List<ConceptoPago> conceptos, double totalBase, double totalConDescuento, 
            String rutaDestino) 
    {
        
        Document document = new Document();
        
        try {
            File archivo = new File(rutaDestino);
            archivo.getParentFile().mkdirs();
            PdfWriter.getInstance(document, new FileOutputStream(rutaDestino));
            document.open();
            
            // Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("RECIBO DE PAGO", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Información del recibo
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            
            // Datos del recibo
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            
            infoTable.addCell(crearCelda("Número de Recibo:", boldFont));
            infoTable.addCell(crearCelda(recibo.getNumeroRecibo(), normalFont));
            infoTable.addCell(crearCelda("Fecha de Emisión:", boldFont));
            infoTable.addCell(crearCelda(new SimpleDateFormat("dd/MM/yyyy").format(recibo.getFechaEmision()), normalFont));
            
            document.add(infoTable);
            document.add(Chunk.NEWLINE);
            
            // Datos del cliente
            Cliente cliente = recibo.getCliente();
            Paragraph clienteInfo = new Paragraph();
            clienteInfo.add(new Chunk("Cliente: ", boldFont));
            clienteInfo.add(new Chunk(cliente.getRazonSocial(), normalFont));
            clienteInfo.add(Chunk.NEWLINE);
            clienteInfo.add(new Chunk("RUC: ", boldFont));
            clienteInfo.add(new Chunk(cliente.getRUC(), normalFont));
            clienteInfo.add(Chunk.NEWLINE);
            clienteInfo.add(new Chunk("Teléfono: ", boldFont));
            clienteInfo.add(new Chunk(cliente.getTelefono() != null ? cliente.getTelefono() : "N/A", normalFont));
            
            document.add(clienteInfo);
            document.add(Chunk.NEWLINE);
            
            // Tabla de conceptos
            Paragraph conceptosTitulo = new Paragraph("DETALLE DE PAGOS", boldFont);
            conceptosTitulo.setSpacingAfter(10);
            document.add(conceptosTitulo);
            
            PdfPTable tablaConceptos = new PdfPTable(new float[]{3, 2, 1});
            tablaConceptos.setWidthPercentage(100);
            
            // Encabezados de tabla
            tablaConceptos.addCell(crearCeldaHeader("DESCRIPCIÓN"));
            tablaConceptos.addCell(crearCeldaHeader("MÉTODO DE PAGO"));
            tablaConceptos.addCell(crearCeldaHeader("MONTO"));
            
            // Filas de conceptos
            for (ConceptoPago concepto : conceptos) {
                tablaConceptos.addCell(crearCelda(concepto.getDescripcion(), normalFont));
                tablaConceptos.addCell(crearCelda(concepto.getMetodoPago().getNombre(), normalFont));
                tablaConceptos.addCell(crearCelda("S/ " + String.format("%.2f", concepto.getMonto()), normalFont));
            }
            
            document.add(tablaConceptos);
            document.add(Chunk.NEWLINE);
            
            //MOSTRAR DESCUENTO SI APLICA
            if (totalConDescuento < totalBase) {
                // Hay descuento aplicado
                Paragraph subtotal = new Paragraph();
                subtotal.add(new Chunk("Subtotal: ", boldFont));
                subtotal.add(new Chunk("S/ " + String.format("%.2f", totalBase), normalFont));
                subtotal.setAlignment(Element.ALIGN_RIGHT);
                document.add(subtotal);
                
                double descuento = totalBase - totalConDescuento;
                Paragraph descuentoLinea = new Paragraph();
                descuentoLinea.add(new Chunk("Descuento aplicado: ", boldFont));
                descuentoLinea.add(new Chunk("S/ " + String.format("%.2f", descuento), normalFont));
                descuentoLinea.setAlignment(Element.ALIGN_RIGHT);
                document.add(descuentoLinea);
            }
            
            //TOTAL FINAL (CON O SIN DESCUENTO)
            Paragraph total = new Paragraph();
            total.add(new Chunk("TOTAL A PAGAR: ", boldFont));
            total.add(new Chunk("S/ " + String.format("%.2f", totalConDescuento), 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)));
            total.setAlignment(Element.ALIGN_RIGHT);
            
            document.add(total);
            
            document.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            if (document.isOpen()) {
                document.close();
            }
            return false;
        }
    }
    
    public boolean generarPDF(Recibo recibo, List<ConceptoPago> conceptos, String rutaDestino) {
        double totalBase = calcularTotalBase(conceptos);
        return generarPDF(recibo, conceptos, totalBase, totalBase, rutaDestino);
    }
    
    private double calcularTotalBase(List<ConceptoPago> conceptos) {
        double total = 0;
        for (ConceptoPago concepto : conceptos) {
            total += concepto.getMonto();
        }
        return total;
    }
    
    private PdfPCell crearCelda(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        return cell;
    }
    
    private PdfPCell crearCeldaHeader(String texto) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setPadding(5);
        return cell;
    }
}
