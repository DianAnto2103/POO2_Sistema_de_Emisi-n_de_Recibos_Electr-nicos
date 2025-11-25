package modelo.Factory;

public class PagoPlin implements MetododePago {
    @Override
    public String getNombre() { 
        return "PLIN"; 
    }
    
    @Override
    public String getDescripcion() { 
        return "Pago por Plin"; 
    }
    
    @Override
    public boolean validarMonto(double monto) { 
        return monto >= 0.10; 
    }
    
    @Override
    public String generarComprobante() { 
        return "Comprobante Plin"; 
    }
}
