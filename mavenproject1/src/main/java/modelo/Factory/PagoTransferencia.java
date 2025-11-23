package modelo.Factory;

public class PagoTransferencia implements MetododePago {
    
    public void procesarPago(double monto) {
        System.out.println("Pago por TRANSFERENCIA registrado: S/ " + monto);
    }
}
