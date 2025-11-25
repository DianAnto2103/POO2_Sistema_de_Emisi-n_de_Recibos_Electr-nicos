package modelo.Factory;

public interface MetododePago {
    String getNombre();
    String getDescripcion();
    boolean validarMonto(double monto);
    String generarComprobante();
}
