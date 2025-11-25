package modelo.Facade;

import model.Cliente;
import model.ConceptoPago;
import model.Recibo;
import model.Strategy.ContextoCalculo;
import model.Strategy.CalculoNormal;
import model.Strategy.DescuentoPagoAdelantado;
import model.Strategy.RecargoMora;

import java.sql.Date;
import java.util.List;


public class CobranzaFacade {
    
   
    private ClienteService clienteService;
    private ReciboService reciboService;
    private CobranzaService cobranzaService;
    private ExportadorPDFService exportadorPDFService;
    
    
    private ContextoCalculo contextoCalculo;
    
    
    public CobranzaFacade() {
        this.clienteService = new ClienteService();
        this.reciboService = new ReciboService();
        this.cobranzaService = new CobranzaService(clienteService);
        this.exportadorPDFService = new ExportadorPDFService();
        this.contextoCalculo = new ContextoCalculo();
        
    }
    
    
    public ResultadoOperacion registrarPagoCompleto(
            String clienteRUC,
            String descripcionPago,
            double monto,
            String metodoPago,
            String referencia,
            Date fecha,
            String tipoCalculo) {
        
        try {
                     
            // 1. Buscar y validar cliente
            Cliente cliente = clienteService.buscarPorRUC(clienteRUC);
            if (cliente == null) {
                return new ResultadoOperacion(false, "Cliente no encontrado", null);
            }
            
            if (!cliente.isActivo()) {
                return new ResultadoOperacion(false, "Cliente inactivo", null);
            }
            
            // 2. Configurar Strategy y calcular monto
            configurarEstrategia(tipoCalculo);
            double montoFinal = contextoCalculo.calcular(monto, cliente);
            
            // 3. Generar recibo
            Recibo recibo = reciboService.generarNuevoRecibo(cliente);
            
            // 4. Crear y registrar concepto
            ConceptoPago concepto = new ConceptoPago();
            concepto.setDescripcion(descripcionPago);
            concepto.setMonto(montoFinal);
            concepto.setMetodoPago(metodoPago);
            concepto.setFecha(fecha);
            concepto.setReciboID(recibo.getID());
            
            if (!cobranzaService.registrarPago(concepto)) {
                return new ResultadoOperacion(false, "Error al registrar pago", null);
            }
            
            // 5. Actualizar y guardar recibo
            reciboService.agregarConcepto(recibo, concepto);
            reciboService.guardarRecibo(recibo);
            
            System.out.println("✓ PAGO REGISTRADO EXITOSAMENTE\n");
            
            return new ResultadoOperacion(true, "Pago registrado", recibo.getNumeroRecibo());
            
        } catch (Exception e) {
            return new ResultadoOperacion(false, "Error: " + e.getMessage(), null);
        }
    }
    
    /**
     * Registrar múltiples conceptos
     */
    public ResultadoOperacion registrarPagoMultiple(
            String clienteRUC,
            List<ConceptoPago> conceptos,
            String tipoCalculo) {
        
        try {
            Cliente cliente = clienteService.buscarPorRUC(clienteRUC);
            if (cliente == null) {
                return new ResultadoOperacion(false, "Cliente no encontrado", null);
            }
            
            configurarEstrategia(tipoCalculo);
            Recibo recibo = reciboService.generarNuevoRecibo(cliente);
            
            for (ConceptoPago concepto : conceptos) {
                double montoCalculado = contextoCalculo.calcular(concepto.getMonto(), cliente);
                concepto.setMonto(montoCalculado);
                concepto.setReciboID(recibo.getID());
                
                cobranzaService.registrarPago(concepto);
                reciboService.agregarConcepto(recibo, concepto);
            }
            
            reciboService.guardarRecibo(recibo);
            
            return new ResultadoOperacion(true, "Pagos registrados", recibo.getNumeroRecibo());
            
        } catch (Exception e) {
            return new ResultadoOperacion(false, "Error: " + e.getMessage(), null);
        }
    }
    
    /**
     * Generar PDF de recibo
     */
    public ResultadoOperacion generarPDF(String numeroRecibo, String rutaDestino) {
        try {
            Recibo recibo = reciboService.obtenerReciboPorNumero(numeroRecibo);
            if (recibo == null) {
                return new ResultadoOperacion(false, "Recibo no encontrado", null);
            }
            
            List<ConceptoPago> conceptos = cobranzaService.obtenerConceptosPorRecibo(recibo.getID());
            boolean generado = exportadorPDFService.exportarRecibo(recibo, conceptos, rutaDestino);
            
            if (generado) {
                return new ResultadoOperacion(true, "PDF generado", rutaDestino);
            } else {
                return new ResultadoOperacion(false, "Error al generar PDF", null);
            }
            
        } catch (Exception e) {
            return new ResultadoOperacion(false, "Error: " + e.getMessage(), null);
        }
    }
    
    
    
    public ResultadoOperacion registrarCliente(String ruc, String razonSocial, String telefono, double mensualidad) {
        Cliente cliente = new Cliente();
        cliente.setRUC(ruc);
        cliente.setRazonSocial(razonSocial);
        cliente.setTelefono(telefono);
        cliente.setMensualidad(mensualidad);
        cliente.setActivo(true);
        
        return clienteService.registrarCliente(cliente) 
            ? new ResultadoOperacion(true, "Cliente registrado", ruc)
            : new ResultadoOperacion(false, "Error al registrar cliente", null);
    }
    
    public Cliente buscarCliente(String criterio) {
        return criterio.matches("\\d{11}") 
            ? clienteService.buscarPorRUC(criterio)
            : clienteService.buscarPorRazonSocial(criterio).stream().findFirst().orElse(null);
    }
    
    public List<Cliente> listarClientes() {
        return clienteService.listarTodos();
    }
    
    public List<CobranzaService.ClienteDeudor> obtenerDeudores() {
        return cobranzaService.obtenerClientesDeudores();
    }
    
    public double calcularTotalPagado(String clienteRUC) {
        Cliente cliente = clienteService.buscarPorRUC(clienteRUC);
        return cliente != null ? cobranzaService.calcularTotalPagado(cliente.getID()) : 0.0;
    }
    
     
    
    private void configurarEstrategia (String tipo){
    switch (tipo.toUpperCase()){
        case "DESCUENTO_ADELANTADO":
        case "ADELANTADO":
            contextoCalculo.setEstrategia(new DescuentoPagoAdelantado());
            break;
        case "RECARGO_MORA":
        case "MORA":
            contextoCalculo.setEstrategia (new RecargoMora());
            break;
            default:
            contextoCalculo.setEstrategia(new CalculoNormal() );
            break;
    }
    }
    
    public static class ResultadoOperacion {
        private boolean exito;
        private String mensaje;
        private final String dato;
        
        public ResultadoOperacion(boolean exito, String mensaje, String dato) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.dato = dato;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public String getDato() { return dato; }
    }
}