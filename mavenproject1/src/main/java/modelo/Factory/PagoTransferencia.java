package modelo.Factory;

public class PagoTransferencia implements MetododePago {
    
    @Override
    public String getNombre() { 
        return "TRANSFERENCIA"; 
    }
    
    @Override
    public String getDescripcion() { 
        return "Transferencia bancaria"; 
    }
    
    @Override
    public boolean validarMonto(double monto) { 
        return monto >= 1.0; 
    } 
    
    @Override
    public String generarComprobante() { 
        return "Comprobante de transferencia"; 
    }
}

