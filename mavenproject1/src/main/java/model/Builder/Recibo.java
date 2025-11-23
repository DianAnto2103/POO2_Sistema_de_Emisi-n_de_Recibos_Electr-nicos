package model.Builder;

public class Recibo {

    private String cliente;
    private String concepto;
    private double monto;

    // Constructor vacío
    public Recibo() {}

    // SETTERS
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public void setMonto(double monto) { this.monto = monto; }

    @Override
    public String toString() {
        return "Cliente: " + cliente +
               "\nConcepto: " + concepto +
               "\nMonto: S/ " + monto;
    }
}
