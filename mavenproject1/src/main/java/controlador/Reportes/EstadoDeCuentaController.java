/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.FacadeReportes.FacadeReportes;
import model.Recibo;
import vista.EstadodeCuentaView;

/**
 *
 * @author diana
 */
public final class EstadoDeCuentaController {
    private final JFrame frameReportes;
    private final EstadodeCuentaView vistaEstadodeCuenta;
    private final FacadeReportes facadeReportes;
    private List<Cliente> clientesEncontrados;
    
    public EstadoDeCuentaController(JFrame frameReportes) {
        this.frameReportes = frameReportes;
        this.vistaEstadodeCuenta = new EstadodeCuentaView();
        this.facadeReportes = new FacadeReportes();
        this.clientesEncontrados = new ArrayList<>();
        configurarEventos(); 
        configurarFechasPorDefecto();
    }
    
    private void configurarFechasPorDefecto() {
        // Fecha inicio: primer día del mes actual
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        vistaEstadodeCuenta.getFechaInicio().setDate(cal.getTime());
        
        // Fecha fin: hoy
        vistaEstadodeCuenta.getFechaFin().setDate(new Date());
    }
    
    private void buscarCliente() {
        try {
            String criterio = vistaEstadodeCuenta.getTextBuscar().getText().trim();
            if (criterio.isEmpty()) {
                JOptionPane.showMessageDialog(vistaEstadodeCuenta, "Ingrese RUC o Razón Social");
                return;
            }
            
            // Buscar por RUC si el botón RUC está seleccionado, sino por Razón Social
            if (vistaEstadodeCuenta.getBotonRUC().isSelected()) {
                clientesEncontrados = facadeReportes.buscarClientesPorRUC(criterio);
            } else {
                clientesEncontrados = facadeReportes.buscarClientesPorRazonSocial(criterio);
            }
            
            if (clientesEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(vistaEstadodeCuenta, "No se encontraron clientes");
                limpiarDatos();
                return;
            }
            
            // Tomar el primer cliente encontrado (puedes expandir para mostrar lista)
            Cliente cliente = clientesEncontrados.get(0);
            mostrarDatosCliente(cliente);
            
            // Generar estado de cuenta automáticamente con las fechas seleccionadas
            generarEstadoCuenta(cliente);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaEstadodeCuenta, "Error al buscar cliente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void mostrarDatosCliente(Cliente cliente) {
        vistaEstadodeCuenta.getTxtRUC().setText(cliente.getRUC());
        vistaEstadodeCuenta.getTxtRazonSocial().setText(cliente.getRazonSocial());
        vistaEstadodeCuenta.getTxtTelefono().setText(cliente.getTelefono());
        vistaEstadodeCuenta.getTxtMensualidad().setText(String.format("S/ %,.2f", cliente.getMensualidad()));
    }
    
    private void generarEstadoCuenta(Cliente cliente) {
        try {
            Date fechaInicio = vistaEstadodeCuenta.getFechaInicio().getDate();
            Date fechaFin = vistaEstadodeCuenta.getFechaFin().getDate();
            
            if (fechaInicio == null || fechaFin == null) {
                JOptionPane.showMessageDialog(vistaEstadodeCuenta, "Seleccione rango de fechas");
                return;
            }
            
            if (fechaInicio.after(fechaFin)) {
                JOptionPane.showMessageDialog(vistaEstadodeCuenta, "La fecha inicio no puede ser mayor a la fecha fin");
                return;
            }
            
            Map<String, Object> estadoCuenta = facadeReportes.generarEstadoCuenta(
                cliente.getRUC(), fechaInicio, fechaFin
            );
            
            mostrarResultados(estadoCuenta);
            
            JOptionPane.showMessageDialog(vistaEstadodeCuenta, 
                "Estado de cuenta generado para el período seleccionado");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaEstadodeCuenta, "Error al generar estado de cuenta: " + ex.getMessage());
        }
    }
    
    private void mostrarResultados(Map<String, Object> estadoCuenta) {
        // Mostrar total pagado
        double totalPagado = (Double) estadoCuenta.get("totalPagado");
        vistaEstadodeCuenta.getTxtTotalPagado().setText(String.format("S/ %,.2f", totalPagado));
        
        // Mostrar último pago
        Date ultimoPago = (Date) estadoCuenta.get("ultimoPago");
        if (ultimoPago != null) {
            vistaEstadodeCuenta.getTxtUltimoPago().setText(new java.text.SimpleDateFormat("dd/MM/yyyy").format(ultimoPago));
        } else {
            vistaEstadodeCuenta.getTxtUltimoPago().setText("Sin pagos en el período");
        }
        
        // Llenar tabla de historial
        List<Recibo> recibos = (List<Recibo>) estadoCuenta.get("recibos");
        llenarTablaHistorial(recibos);
    }
    
    private void llenarTablaHistorial(List<Recibo> recibos) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("N° Recibo");
        model.addColumn("Fecha");
        model.addColumn("Monto");
        
        for (Recibo recibo : recibos) {
            model.addRow(new Object[]{
                recibo.getNumeroRecibo(),
                new java.text.SimpleDateFormat("dd/MM/yyyy").format(recibo.getFechaEmision()),
                String.format("S/ %,.2f", recibo.getTotal())
            });
        }
        
        vistaEstadodeCuenta.getTablaHistorialPagos().setModel(model);
    }
    
    private void limpiarDatos() {
        vistaEstadodeCuenta.getTxtRUC().setText("");
        vistaEstadodeCuenta.getTxtRazonSocial().setText("");
        vistaEstadodeCuenta.getTxtTelefono().setText("");
        vistaEstadodeCuenta.getTxtMensualidad().setText("");
        vistaEstadodeCuenta.getTxtTotalPagado().setText("");
        vistaEstadodeCuenta.getTxtUltimoPago().setText("");
        vistaEstadodeCuenta.getTablaHistorialPagos().setModel(new DefaultTableModel());
    }
    

    public void configurarEventos(){
        this.vistaEstadodeCuenta.getBotonVolver().addActionListener(e -> cerrarVentana());
        this.vistaEstadodeCuenta.getBotonBuscar().addActionListener(e -> buscarCliente());
    }
    
    private void cerrarVentana(){
        frameReportes.dispose();
    }
    
    public EstadodeCuentaView getVistaEstadodeCuenta() {
        return vistaEstadodeCuenta;
    }
    
}
