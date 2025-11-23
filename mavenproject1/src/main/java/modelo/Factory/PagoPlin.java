package modelo.Factory;

public class PagoPlin implements MetododePago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago con PLIN registrado: S/ " + monto);
    }

    @Override
    public String getNombre() {
        return "Plin";
    }
}
