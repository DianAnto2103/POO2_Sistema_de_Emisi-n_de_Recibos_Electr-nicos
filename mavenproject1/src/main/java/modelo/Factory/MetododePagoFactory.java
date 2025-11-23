package modelo.Factory;

public class MetododePagoFactory {

    public static MetododePago getMetodoPago(String tipo) {
        if (tipo == null) return null;

        switch (tipo.toUpperCase()) {
            case "EFECTIVO":
                return new PagoEfectivo();
            case "TRANSFERENCIA":
                return new PagoTransferencia();
            case "TARJETA":
                return new PagoTarjeta();
            case "YAPE":
                return new PagoYape();
            case "PLIN":
                return new PagoPlin();

            default:
                System.out.println("Método de pago no válido.");
                return null;
        }
    }
}
