package modelo.Factory;

import java.util.Arrays;
import java.util.List;

public class MetodoPagoFactory {
    public static MetododePago crearMetodoPago(String tipo) {
        switch (tipo.toUpperCase()) {
            case "EFECTIVO":
                return new PagoEfectivo();
            case "TRANSFERENCIA":
                return new PagoTransferencia();
            case "YAPE":
                return new PagoYape();
            case "PLIN":
                return new PagoPlin();
            default:
                throw new IllegalArgumentException("Método de pago no soportado: " + tipo);
        }
    }
    
    public static List<String> obtenerMetodosDisponibles() {
        return Arrays.asList("EFECTIVO", "TRANSFERENCIA", "YAPE", "PLIN");
    }
}
