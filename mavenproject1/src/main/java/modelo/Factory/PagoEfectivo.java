package modelo.Factory;

public class PagoEfectivo implements MetododePago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago en EFECTIVO registrado: S/ " + monto);
    }

    @Override
    public String getNombre() {
        return "Efectivo";
    }
}
