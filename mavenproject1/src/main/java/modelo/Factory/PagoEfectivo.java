package modelo.Factory;

public class PagoEfectivo implements MetododePago {
    
    public void procesarPago(double monto) {
        System.out.println("Pago en EFECTIVO registrado: S/ " + monto);
    }
}
