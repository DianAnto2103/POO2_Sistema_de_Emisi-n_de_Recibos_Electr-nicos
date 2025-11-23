package modelo.Factory;

public class PagoYape implements MetododePago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago con YAPE registrado: S/ " + monto);
    }

    @Override
    public String getNombre() {
        return "Yape";
    }
}
