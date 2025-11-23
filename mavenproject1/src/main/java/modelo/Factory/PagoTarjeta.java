package modelo.Factory;

public class PagoTarjeta implements MetododePago {
    public void procesarPago(double monto) {
        System.out.println("Pago con TARJETA registrado: S/ " + monto);
    }
}
