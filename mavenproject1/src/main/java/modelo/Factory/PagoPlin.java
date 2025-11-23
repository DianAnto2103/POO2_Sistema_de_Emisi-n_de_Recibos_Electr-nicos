package modelo.Factory;

public class PagoPlin implements MetododePago {
    public void procesarPago(double monto) {
        System.out.println("Pago con PLIN registrado: S/ " + monto);
    }
}
