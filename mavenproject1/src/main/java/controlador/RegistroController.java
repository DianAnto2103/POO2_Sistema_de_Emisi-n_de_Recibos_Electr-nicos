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
import model.Strategy.CalculoDescuento;
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
        vistaRecibo.getBotonConDescuento().setSelected(false);
        aplicarEstrategiaSinDescuento();
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
             
            ConceptoPago concepto = new ConceptoPago();
            concepto.setDescripcion(descripcion);
            concepto.setFecha(new java.sql.Date(fecha.getTime()));
            concepto.setMontoBase(montoBase); // Guardar monto base para referencia
            concepto.setMonto(montoBase);
            
            
            conceptosTemporales.add(concepto);
            actualizarTablaConceptos();
            limpiarFormularioConcepto();
            actualizarTotal();
            
            JOptionPane.showMessageDialog(vistaRecibo, "Concepto agregado: " + descripcion);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRecibo, "Monto debe ser un número válido");
        }
    }
    
    private void aplicarEstrategiaSinDescuento() {
        facadeRecibos.setEstrategiaNormal();
        actualizarTotal(); //Solo actualizar el TOTAL
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
        
            // Actualizar  total
            actualizarTotal();
        
            JOptionPane.showMessageDialog(vistaRecibo, "Concepto eliminado");
        }
    }
    
    private void actualizarTotal() 
    {
        // 1. Calcular total usando el Facade
        Cliente cliente = obtenerClienteSeleccionado();
        double total = facadeRecibos.aplicarEstrategiaAlTotal(conceptosTemporales, cliente);
    
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
    
    private void aplicarEstrategiaConDescuento() 
    {
        facadeRecibos.setEstrategiaConDescuento();
        actualizarTotal(); //Solo actualizar el TOTAL
    }
    

    private Cliente obtenerClienteSeleccionado() 
    {
        String nombreSeleccionado = (String) vistaRecibo.getBotonBusqueda().getSelectedItem();
        if (nombreSeleccionado != null) {
            return facadeRecibos.buscarClientePorNombre(nombreSeleccionado);
        }
        return null;
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
    
    
    private void actualizarTablaConceptos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("#");
        model.addColumn("Descripción");
        model.addColumn("Fecha");
        model.addColumn("Monto");
    
        int contador = 1;
        for (ConceptoPago concepto : conceptosTemporales) {
            model.addRow(new Object[]{
                contador++,
                concepto.getDescripcion(),
                concepto.getFecha(),
                "S/ " + String.format("%,.2f", concepto.getMonto()) // MONTO BASE
            });
        }
    
        vistaRecibo.getTablaConceptos().setModel(model);
    }
    
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    public ReciboView getVistaRegistro(){
        return vistaRecibo;
    }
}
   