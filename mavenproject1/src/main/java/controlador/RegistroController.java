/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.ConceptoPago;
import model.FacadeRecibos.FacadeRecibos;
import vista.ReciboView;

/**
 *
 * @author diana
 */
public final class RegistroController {
    private final JFrame frameReportes;
    private final ReciboView vistaRecibo;
    private final FacadeRecibos facadeRecibos;
    private final List<ConceptoPago> conceptosTemporales;
    
    public RegistroController(JFrame frameReportes){
        this.frameReportes = frameReportes;
        this.vistaRecibo = new ReciboView();
        this.facadeRecibos = new FacadeRecibos();
        this.conceptosTemporales = new ArrayList<>();
        configurarEventos();
        inicializarFormulario();
        
    }
    
    public void configurarEventos(){
        vistaRecibo.getBotonSalir().addActionListener(e -> {
            cerrarVentana();
        });
        
        vistaRecibo.getBotonBusqueda().addActionListener(e -> autocompletarDatosCliente());
        
        vistaRecibo.getBotonAgregar().addActionListener(e -> agregarConceptoPago());
    }
    
    private void inicializarFormulario() {
        // 1. Generar número de recibo
        String numeroRecibo = facadeRecibos.generarNuevoNumeroRecibo();
        vistaRecibo.getTxtNumeroRecibo().setText(numeroRecibo);
        
        // 2. Cargar nombres en el JComboBox
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
    
    private void agregarConceptoPago() {
        try {
            // 1. Obtener datos del formulario
            String descripcion = vistaRecibo.getTxtDescripcion().getText();
            String metodoPago = (String) vistaRecibo.getComboMetodoPago().getSelectedItem();
            double monto = Double.parseDouble(vistaRecibo.getTxtMonto().getText());
            Date fecha = vistaRecibo.getDate().getDate();
            
            // 2. Validar campos obligatorios
            if (descripcion.isEmpty() || metodoPago == null || fecha == null) {
                JOptionPane.showMessageDialog(vistaRecibo, "Todos los campos son obligatorios");
                return;
            }
            
            // 3. Crear objeto ConceptoPago - CORRECCIÓN AQUÍ
            ConceptoPago concepto = new ConceptoPago();
            concepto.setDescripcion(descripcion);
            concepto.setMetodoPago(metodoPago);
            concepto.setMonto(monto);
            concepto.setFecha(new java.sql.Date(fecha.getTime())); // ✅ Corrección
            
            // 4. Agregar a lista temporal (por ahora sin validación del Facade)
            conceptosTemporales.add(concepto);
            actualizarTablaConceptos();
            limpiarFormularioConcepto();
            actualizarTotal();
            
            JOptionPane.showMessageDialog(vistaRecibo, "Concepto agregado");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRecibo, "Monto debe ser un número válido");
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
    
    private void actualizarTablaConceptos() {
        // Convertir lista de conceptos a TableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Descripción");
        model.addColumn("Método Pago");
        model.addColumn("Monto");
        
        for (ConceptoPago concepto : conceptosTemporales) {
            model.addRow(new Object[]{
                concepto.getDescripcion(),
                concepto.getMetodoPago(),
                "S/ " + concepto.getMonto()
            });
        }
        
        vistaRecibo.getTablaConceptos().setModel(model);
    }
    
    private void limpiarFormularioConcepto() {
        vistaRecibo.getTxtDescripcion().setText("");
        vistaRecibo.getTxtMonto().setText("");
        vistaRecibo.getComboMetodoPago().setSelectedIndex(0);
        vistaRecibo.getDate().setDate(new Date()); // Fecha actual
    }
    
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    
    public ReciboView getVistaRegistro(){
        return vistaRecibo;
    }
}
