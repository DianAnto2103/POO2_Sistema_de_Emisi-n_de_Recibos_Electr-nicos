package modelo.Factory;

public class PagoEfectivo implements MetododePago {
    @Override
    public String getNombre() { 
        return "EFECTIVO"; 
    }
    
    @Override
    public String getDescripcion() { 
        return "Pago en efectivo"; 
    }
    
    @Override
    public boolean validarMonto(double monto) { 
        return monto > 0; 
    }
    
    @Override
    public String generarComprobante() { 
        return "Comprobante de pago en efectivo"; 
    }
}
