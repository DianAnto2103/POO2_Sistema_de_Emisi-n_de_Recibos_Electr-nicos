package modelo.Factory;

public class PagoTransferencia implements MetododePago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago por TRANSFERENCIA registrado: S/ " + monto);
    }

    @Override
    public String getNombre() {
        return "Transferencia";
    }
}
