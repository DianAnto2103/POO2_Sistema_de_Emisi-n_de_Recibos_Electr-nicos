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
    private final ContextoCalculo estrategiaActual;
    private CalculoStrategy estrategiaActual;
    
    private JButton btnNormal;
    private JButton btnDescuento;
    private JButton btnRecargo;
    private JLabel lblEstrategia;
    
    public RegistroController(JFrame frameReportes){
        this.frameReportes = frameReportes;
        this.vistaRecibo = new ReciboView();
        this.facadeRecibos = new FacadeRecibos();
        this.conceptosTemporales = new ArrayList<>();
        this.contextoCalculo = new ContextoCalculo(); // Inicializar Strategy
        this.estrategiaActual = new CalculoNormal(); // Estrategia por defecto
        
        configurarComponentesStrategy();
        configurarEventos();
        inicializarFormulario();
    }
    
    // NUEVO MÉTODO: Configurar componentes del Strategy
    private void configurarComponentesStrategy() {
        // Crear botones para las estrategias
        btnNormal = new JButton("Pago Normal");
        btnDescuento = new JButton("Descuento 5%");
        btnRecargo = new JButton("Recargo 15%");
        lblEstrategia = new JLabel("Estrategia: Pago Normal");
        
        // Estilos para los botones
        btnNormal.setBackground(new Color(200, 200, 200));
        btnDescuento.setBackground(new Color(144, 238, 144)); // Verde claro
        btnRecargo.setBackground(new Color(255, 182, 193));   // Rosa claro
        
        lblEstrategia.setForeground(Color.BLUE);
        lblEstrategia.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Agregar a la vista
        vistaRecibo.agregarComponentesStrategy(btnNormal, btnDescuento, btnRecargo, lblEstrategia);
    }
    
    public void configurarEventos(){
        vistaRecibo.getBotonSalir().addActionListener(e -> {
            cerrarVentana();
        });
        
        vistaRecibo.getBotonBusqueda().addActionListener(e -> autocompletarDatosCliente());
        
        vistaRecibo.getBotonAgregar().addActionListener(e -> agregarConceptoPago());
        
        vistaRecibo.getBotonBorrar().addActionListener(e -> eliminarConcepto());
        
        vistaRecibo.getBotonGuardarPDF().addActionListener(e -> generarPDFRecibo());
        
        // NUEVOS EVENTOS PARA STRATEGY
        btnNormal.addActionListener(e -> aplicarEstrategiaNormal());
        btnDescuento.addActionListener(e -> aplicarEstrategiaDescuento());
        btnRecargo.addActionListener(e -> aplicarEstrategiaRecargo());
    }
    
    // NUEVOS MÉTODOS PARA MANEJAR LAS ESTRATEGIAS
    private void aplicarEstrategiaNormal() {
        estrategiaActual = new CalculoNormal();
        contextoCalculo.setEstrategia(estrategiaActual);
        lblEstrategia.setText("Estrategia: Pago Normal");
        lblEstrategia.setForeground(Color.BLUE);
        JOptionPane.showMessageDialog(vistaRecibo, 
            "Modo: Pago Normal\nSin descuentos ni recargos", 
            "Estrategia Cambiada", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void aplicarEstrategiaDescuento() {
        estrategiaActual = new DescuentoPagoAdelantado();
        contextoCalculo.setEstrategia(estrategiaActual);
        lblEstrategia.setText("Estrategia: Descuento 5% por Pago Adelantado");
        lblEstrategia.setForeground(Color.GREEN.darker());
        JOptionPane.showMessageDialog(vistaRecibo, 
            "Modo: Descuento 5%\nAplicado por pago adelantado", 
            "Estrategia Cambiada", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void aplicarEstrategiaRecargo() {
        estrategiaActual = new RecargoMora();
        contextoCalculo.setEstrategia(estrategiaActual);
        lblEstrategia.setText("Estrategia: Recargo 15% por Mora");
        lblEstrategia.setForeground(Color.RED);
        JOptionPane.showMessageDialog(vistaRecibo, 
            "Modo: Recargo 15%\nAplicado por pago en mora", 
            "Estrategia Cambiada", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    // MÉTODO MODIFICADO: aplicarStrategyAlMonto
    private double aplicarStrategyAlMonto(double montoBase) {
        try {
            // Obtener cliente seleccionado para aplicar la estrategia
            String nombreSeleccionado = (String) vistaRecibo.getBotonBusqueda().getSelectedItem();
            if (nombreSeleccionado != null) {
                Cliente cliente = facadeRecibos.buscarClientePorNombre(nombreSeleccionado);
                if (cliente != null) {
                    // Aplicar la estrategia seleccionada al monto
                    double montoModificado = contextoCalculo.calcular(montoBase, cliente);
                    
                    // Mostrar información del cálculo aplicado
                    mostrarInfoCalculo(montoBase, montoModificado);
                    
                    return montoModificado;
                }
            }
        } catch (Exception e) {
            System.err.println("Error aplicando estrategia: " + e.getMessage());
        }
        
        // Si hay error, retornar monto base sin cambios
        return montoBase;
    }
    
    // MÉTODO NUEVO: Mostrar información del cálculo aplicado
    private void mostrarInfoCalculo(double montoBase, double montoModificado) {
        double diferencia = montoModificado - montoBase;
        String mensaje;
        
        if (diferencia < 0) {
            mensaje = String.format(
                "✓ DESCUENTO APLICADO\nMonto base: S/ %.2f\nDescuento: S/ %.2f\nMonto final: S/ %.2f",
                montoBase, Math.abs(diferencia), montoModificado
            );
        } else if (diferencia > 0) {
            mensaje = String.format(
                "⚠ RECARGO APLICADO\nMonto base: S/ %.2f\nRecargo: S/ %.2f\nMonto final: S/ %.2f",
                montoBase, diferencia, montoModificado
            );
        } else {
            mensaje = String.format(
                "● PAGO NORMAL\nMonto: S/ %.2f\nSin ajustes aplicados",
                montoBase
            );
        }
        
        // Mostrar en consola para debugging
        System.out.println("=== APLICACIÓN DE ESTRATEGIA ===");
        System.out.println(mensaje);
        System.out.println("Estrategia: " + estrategiaActual.obtenerDescripcion());
        System.out.println("=================================");
    }
    
    // MÉTODO MODIFICADO: agregarConceptoPago (INTEGRAR STRATEGY)
    private void agregarConceptoPago() {
        try {
            // 1. Obtener datos del formulario
            String descripcion = vistaRecibo.getTxtDescripcion().getText();
            String metodoPago = (String) vistaRecibo.getComboMetodoPago().getSelectedItem();
            double montoBase = Double.parseDouble(vistaRecibo.getTxtMonto().getText());
            Date fecha = vistaRecibo.getDate().getDate();
            
            // 2. Validar campos obligatorios
            if (descripcion.isEmpty() || metodoPago == null || fecha == null) {
                JOptionPane.showMessageDialog(vistaRecibo, "Todos los campos son obligatorios");
                return;
            }
            
            // 3. APLICAR STRATEGIA AL MONTO
            double montoFinal = aplicarStrategyAlMonto(montoBase);
            
            // 4. Crear objeto ConceptoPago con el monto modificado
            ConceptoPago concepto = new ConceptoPago();
            concepto.setDescripcion(descripcion + " [" + estrategiaActual.getTipo() + "]");
            concepto.setMetodoPago(metodoPago);
            concepto.setMonto(montoFinal); // Usar monto modificado por la estrategia
            concepto.setFecha(new java.sql.Date(fecha.getTime()));
            concepto.setTipoEstrategia(estrategiaActual.getTipo()); // Guardar tipo de estrategia
            
            // 5. Agregar a lista temporal
            conceptosTemporales.add(concepto);
            actualizarTablaConceptos();
            limpiarFormularioConcepto();
            actualizarTotal();
            
            JOptionPane.showMessageDialog(vistaRecibo, 
                "Concepto agregado con " + estrategiaActual.obtenerDescripcion());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRecibo, "Monto debe ser un número válido");
        }
    }
    
    // MÉTODO MODIFICADO: actualizarTablaConceptos (mostrar estrategia)
    private void actualizarTablaConceptos() {
        // Convertir lista de conceptos a TableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Descripción");
        model.addColumn("Método Pago");
        model.addColumn("Monto");
        model.addColumn("Tipo"); // Nueva columna para mostrar la estrategia
        
        for (ConceptoPago concepto : conceptosTemporales) {
            String tipoEstrategia = concepto.getTipoEstrategia() != null ? 
                                  concepto.getTipoEstrategia() : "NORMAL";
            
            model.addRow(new Object[]{
                concepto.getDescripcion(),
                concepto.getMetodoPago(),
                "S/ " + String.format("%,.2f", concepto.getMonto()),
                tipoEstrategia
            });
        }
        
        vistaRecibo.getTablaConceptos().setModel(model);
    }
    
    // EL RESTO DE TUS MÉTODOS PERMANECEN IGUAL...
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
                                                           Recibo guardado yy PDF generado exitosamente!!!
                                                           Archivo: recibos/Recibo-[NUMERO].pdf""");
                
                // Limpiar formulario para nuevo recibo
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
        
        // Resetear estrategia a Normal
        aplicarEstrategiaNormal();
    }
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    public ReciboView getVistaRegistro(){
        return vistaRecibo;
    }
}