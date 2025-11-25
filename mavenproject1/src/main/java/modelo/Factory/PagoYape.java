package modelo.Factory;

public class PagoYape implements MetododePago {
    
    @Override
    public String getNombre() { 
        return "YAPE"; 
    }
    
    @Override
    public String getDescripcion() { 
        return "Pago por Yape"; 
    }
    
    @Override
    public boolean validarMonto(double monto) { 
        return monto >= 0.10; 
    } 
    
    @Override
    public String generarComprobante() { 
        return "Comprobante Yape"; 
    }
    
}
