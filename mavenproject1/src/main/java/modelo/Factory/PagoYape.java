package modelo.Factory;

public class PagoYape implements MetododePago {
    
    public void procesarPago(double monto) {
        System.out.println("Pago con YAPE registrado: S/ " + monto);
    }
}
