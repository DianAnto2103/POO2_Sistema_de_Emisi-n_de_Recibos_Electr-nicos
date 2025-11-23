package modelo.Factory;

public class PagoTarjeta implements MetododePago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago con TARJETA registrado: S/ " + monto);
    }

    @Override
    public String getNombre() {
        return "Tarjeta";
    }
}
