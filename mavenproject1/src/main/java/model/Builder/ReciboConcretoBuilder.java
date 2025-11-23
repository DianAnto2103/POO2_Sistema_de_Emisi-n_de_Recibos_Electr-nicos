package model.Builder;

public class ReciboConcretoBuilder implements ReciboBuilder {

    private Recibo recibo;

    public ReciboConcretoBuilder() {
        this.recibo = new Recibo();  // se crea aquí
    }

    @Override
    public ReciboBuilder setCliente(String cliente) {
        recibo.setCliente(cliente);
        return this;
    }

    @Override
    public ReciboBuilder setConcepto(String concepto) {
        recibo.setConcepto(concepto);
        return this;
    }

    @Override
    public ReciboBuilder setMonto(double monto) {
        recibo.setMonto(monto);
        return this;
    }

    @Override
    public Recibo build() {
        return this.recibo; // devuelve el recibo completado
    }
}
