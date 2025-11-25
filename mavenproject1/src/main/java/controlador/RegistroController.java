/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.ConceptoPago;
import model.FacadeRecibos.FacadeRecibos;
import vista.ReciboView;
import model.Strategy.CalculoStrategy;
import model.Strategy.CalculoNormal;
import model.Strategy.DescuentoPagoAdelantado;
import model.Strategy.RecargoMora;
import model.Strategy.ContextoCalculo;


/**
 *
 * @author diana y Flavia
 */
public final class RegistroController {
    private final JFrame frameReportes;
    private final ReciboView vistaRecibo;
    private final FacadeRecibos facadeRecibos;
    private final List<ConceptoPago> conceptosTemporales;
    private final ContextoCalculo contextoCalculo;
    private CalculoStrategy estrategiaActual;
    
 public RegistroController(JFrame frameReportes){
        this.frameReportes = frameReportes;
        this.vistaRecibo = new ReciboView();
        this.facadeRecibos = new FacadeRecibos();
        this.conceptosTemporales = new ArrayList<>();
        this.contextoCalculo = new ContextoCalculo(); // Inicializar Strategy
        this.estrategiaActual = new CalculoNormal(); // Estrategia por defecto
        
        configurarEventos();
        inicializarFormulario();
        configurarStrategyInicial();
    }
    
    public void configurarEventos(){
        vistaRecibo.getBotonSalir().addActionListener(e -> {
            cerrarVentana();
        });
        
        vistaRecibo.getBotonBusqueda().addActionListener(e -> autocompletarDatosCliente());
        
        vistaRecibo.getBotonAgregar().addActionListener(e -> agregarConceptoPago());
        
        vistaRecibo.getBotonBorrar().addActionListener(e -> eliminarConcepto());
        
        vistaRecibo.getBotonGuardarPDF().addActionListener(e -> generarPDFRecibo());
        
     
        vistaRecibo.getBotonSinDescuento().addActionListener(e -> aplicarEstrategiaSinDescuento());
        vistaRecibo.getBotonConDescuento().addActionListener(e -> aplicarEstrategiaConDescuento());
        
    }
    
     private void configurarStrategyInicial() {
        
        vistaRecibo.getBotonSinDescuento().setSelected(true);
        aplicarEstrategiaSinDescuento();
    }
    
    // MÉTODOS PARA MANEJAR LAS ESTRATEGIAS
    private void aplicarEstrategiaSinDescuento() {
        estrategiaActual = new CalculoNormal();
        contextoCalculo.setEstrategia(estrategiaActual);
        
        
        vistaRecibo.getBotonSinDescuento().setSelected(true);
        vistaRecibo.getBotonConDescuento().setSelected(true);
        
        
        JOptionPane.showMessageDialog(vistaRecibo, 
            "Modo: Sin Descuento\nSe aplicará el monto sin modificaciones", 
            "Estrategia Cambiada", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void aplicarEstrategiaConDescuento() {
        estrategiaActual = new DescuentoPagoAdelantado();
        contextoCalculo.setEstrategia(estrategiaActual);
        
        // Actualizar interfaz
        vistaRecibo.getBotonSinDescuento().setSelected(true);
        vistaRecibo.getBotonConDescuento().setSelected(true);
        
        // Mostrar mensaje
        JOptionPane.showMessageDialog(vistaRecibo, 
            "Modo: Con Descuento\nSe aplicará 5% de descuento al monto base", 
            "Estrategia Cambiada", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private double aplicarStrategyAlMonto(double montoBase) {
        try {
            // Obtener cliente seleccionado para aplicar la estrategia
            String nombreSeleccionado = (String) vistaRecibo.getBotonBusqueda().getSelectedItem();
            if (nombreSeleccionado != null) {
                Cliente cliente = facadeRecibos.buscarClientePorNombre(nombreSeleccionado);
                if (cliente != null) {
                    // Aplicar la estrategia seleccionada al monto
                    double montoModificado = contextoCalculo.calcular(montoBase, cliente);
                    
                    return montoModificado;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaRecibo, 
                "Error aplicando estrategia: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        
        return montoBase;
    }
    
    private void agregarConceptoPago() {
        try {
            
            String descripcion = vistaRecibo.getTxtDescripcion().getText();
            String metodoPago = (String) vistaRecibo.getComboMetodoPago().getSelectedItem();
            double montoBase = Double.parseDouble(vistaRecibo.getTxtMonto().getText());
            Date fecha = vistaRecibo.getDate().getDate();
            
           
            if (descripcion.isEmpty() || metodoPago == null || fecha == null) {
                JOptionPane.showMessageDialog(vistaRecibo, "Todos los campos son obligatorios");
                return;
            }
            
            if (montoBase <= 0) {
                JOptionPane.showMessageDialog(vistaRecibo, "El monto debe ser mayor a 0");
                return;
            }
            
            // 3. Validar que hay cliente seleccionado
            String nombreSeleccionado = (String) vistaRecibo.getBotonBusqueda().getSelectedItem();
            if (nombreSeleccionado == null) {
                JOptionPane.showMessageDialog(vistaRecibo, "Seleccione un cliente primero");
                return;
            }
            
            
            double montoFinal = aplicarStrategyAlMonto(montoBase);
            
            
            ConceptoPago concepto = new ConceptoPago();
            concepto.setDescripcion(descripcion);
            concepto.setMetodoPago(metodoPago);
            concepto.setMonto(montoFinal); // Usar monto modificado por la estrategia
            concepto.setFecha(new java.sql.Date(fecha.getTime()));
            concepto.setTipoEstrategia(estrategiaActual.getTipo()); // Guardar tipo de estrategia
            concepto.setMontoBase(montoBase); // Guardar monto base para referencia
            
            
            conceptosTemporales.add(concepto);
            actualizarTablaConceptos();
            limpiarFormularioConcepto();
            actualizarTotal();
            
            mostrarConfirmacionConcepto(concepto, montoBase);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRecibo, "Monto debe ser un número válido");
        }
    }
    
    // MÉTODO NUEVO: Mostrar confirmación con detalles del concepto
    private void mostrarConfirmacionConcepto(ConceptoPago concepto, double montoBase) {
        double diferencia = concepto.getMonto() - montoBase;
        String mensaje;
        
        if (diferencia < 0) {
            mensaje = String.format(
                "<html><b>Concepto agregado con DESCUENTO 5%%</b><br><br>" +
                "Descripción: %s<br>" +
                "Monto base: S/ %.2f<br>" +
                "Descuento: S/ %.2f<br>" +
                "<b>Monto final: S/ %.2f</b><br><br>" +
                "<font color='green'>¡Ahorro para el cliente!</font></html>",
                concepto.getDescripcion(),
                montoBase,
                Math.abs(diferencia),
                concepto.getMonto()
            );
        } else {
            mensaje = String.format(
                "<html><b>Concepto agregado</b><br><br>" +
                "Descripción: %s<br>" +
                "Monto: S/ %.2f<br>" +
                "Tipo: Pago normal</html>",
                concepto.getDescripcion(),
                concepto.getMonto()
            );
        }
        
        JOptionPane.showMessageDialog(vistaRecibo, mensaje, "Concepto Agregado", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    // MÉTODO MODIFICADO: actualizarTablaConceptos (mostrar estrategia)
    private void actualizarTablaConceptos() {
        // Convertir lista de conceptos a TableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("#");
        model.addColumn("Descripción");
        model.addColumn("Método Pago");
        model.addColumn("Monto");
        model.addColumn("Tipo"); // Nueva columna para mostrar la estrategia
        
        int contador = 1;
        for (ConceptoPago concepto : conceptosTemporales) {
            String tipoEstrategia = concepto.getTipoEstrategia() != null ? 
                                  concepto.getTipoEstrategia() : "NORMAL";
            
            // Agregar icono según el tipo de estrategia
            String tipoConIcono = tipoEstrategia.equals("DESCUENTO_ADELANTADO") ? 
                                "✓ Con Descuento" : " Normal";
            
            model.addRow(new Object[]{
                contador++,
                concepto.getDescripcion(),
                concepto.getMetodoPago(),
                "S/ " + String.format("%,.2f", concepto.getMonto()),
                tipoConIcono
            });
        }
        
        vistaRecibo.getTablaConceptos().setModel(model);
    }
    
    
    private void inicializarFormulario() {
        
        String numeroRecibo = facadeRecibos.generarNuevoNumeroRecibo();
        vistaRecibo.getTxtNumeroRecibo().setText(numeroRecibo);
        
        
        cargarClientesEnComboBox();
    }
    
    private void cargarClientesEnComboBox() {
        List<String> nombres = facadeRecibos.obtenerNombresClientes();
        JComboBox<String> combo = vistaRecibo.getBotonBusqueda();
        combo.removeAllItems();
        for (String nombre : nombres) {
            combo.addItem(nombre);
        }
    }
    
    private void autocompletarDatosCliente() {
        String nombreSeleccionado = (String) vistaRecibo.getBotonBusqueda().getSelectedItem();
        if (nombreSeleccionado != null) {
            Cliente cliente = facadeRecibos.buscarClientePorNombre(nombreSeleccionado);
            if (cliente != null) {
                vistaRecibo.setTxtCodigo(String.valueOf(cliente.getID()));
                vistaRecibo.setTxtRUC(cliente.getRUC());
                vistaRecibo.setTxtRazonSocial(cliente.getRazonSocial());
                vistaRecibo.setTxtTelefono(cliente.getTelefono());
                vistaRecibo.setTxtMensualidad(String.valueOf(cliente.getMensualidad()));
            }
        }
    }
    
    private void eliminarConcepto() {
        int filaSeleccionada = vistaRecibo.getTablaConceptos().getSelectedRow();
    
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaRecibo, "Seleccione un concepto para eliminar");
            return;
        }
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(
            vistaRecibo, 
            "¿Está seguro de eliminar este concepto?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar de la lista temporal
            conceptosTemporales.remove(filaSeleccionada);
        
            // Actualizar tabla y total
            actualizarTablaConceptos();
            actualizarTotal();
        
            JOptionPane.showMessageDialog(vistaRecibo, "Concepto eliminado");
        }
    }
    
    private void actualizarTotal() {
        // 1. Calcular total usando el Facade
        double total = facadeRecibos.calcularTotalConceptos(conceptosTemporales);
    
        // 2. Formatear y mostrar
        String totalFormateado = "S/ " + String.format("%,.2f", total);
        vistaRecibo.getTxtTotal().setText(totalFormateado);
    
        // 3. Resaltar si supera S/ 10,000 (HU5)
        if (total > 10000.00) {
            vistaRecibo.getTxtTotal().setBackground(Color.YELLOW);
        } else {
            vistaRecibo.getTxtTotal().setBackground(Color.WHITE);
        }
    }
    
    private void limpiarFormularioConcepto() {
        vistaRecibo.getTxtDescripcion().setText("");
        vistaRecibo.getTxtMonto().setText("");
        vistaRecibo.getComboMetodoPago().setSelectedIndex(0);
        vistaRecibo.getDate().setDate(new Date()); // Fecha actual
    }
    
    private void generarPDFRecibo() {
        try {
            // Validar que hay conceptos agregados
            if (conceptosTemporales.isEmpty()) {
                JOptionPane.showMessageDialog(vistaRecibo, "Agregue al menos un concepto de pago");
                return;
            }
            
            // Validar que hay cliente seleccionado
            String rucCliente = vistaRecibo.getTxtRUC().getText();
            if (rucCliente.isEmpty()) {
                JOptionPane.showMessageDialog(vistaRecibo, "Seleccione un cliente primero");
                return;
            }
            
            // Llamar al facade para generar recibo completo y PDF
            boolean exito = facadeRecibos.generarReciboCompleto(rucCliente, conceptosTemporales);
            
            if (exito) {
                JOptionPane.showMessageDialog(vistaRecibo, """
                                                           Recibo guardado y PDF generado exitosamente!!!
                                                           Archivo: recibos/Recibo-[NUMERO].pdf""");
                
                
                limpiarFormularioCompleto();
                
            } else {
                JOptionPane.showMessageDialog(vistaRecibo, 
                    "Error al generar el recibo", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (HeadlessException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaRecibo, 
                "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormularioCompleto() {
        // Limpiar datos del cliente
        vistaRecibo.getTxtCodigo().setText("");
        vistaRecibo.getTxtRUC().setText("");
        vistaRecibo.getTxtRazonSocial().setText("");
        vistaRecibo.getTxtTelefono().setText("");
        vistaRecibo.getTxtMensualidad().setText("");
    
        // Limpiar lista de conceptos y tabla
        conceptosTemporales.clear();
        actualizarTablaConceptos();
        actualizarTotal();
    
        // Generar nuevo número de recibo para el siguiente
        String nuevoNumero = facadeRecibos.generarNuevoNumeroRecibo();
        vistaRecibo.getTxtNumeroRecibo().setText(nuevoNumero);
        
        // Resetear estrategia a Sin Descuento
        aplicarEstrategiaSinDescuento();
    }
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    public ReciboView getVistaRegistro(){
        return vistaRecibo;
    }
}
   