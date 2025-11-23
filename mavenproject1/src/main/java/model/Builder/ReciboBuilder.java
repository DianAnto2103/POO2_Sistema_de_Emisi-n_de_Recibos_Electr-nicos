package model.Builder;

public interface ReciboBuilder {

    ReciboBuilder setCliente(String cliente);

    ReciboBuilder setConcepto(String concepto);

    ReciboBuilder setMonto(double monto);

    Recibo build();   // RETORNA EL RECIBO LISTO
}
