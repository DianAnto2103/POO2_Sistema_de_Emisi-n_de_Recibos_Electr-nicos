/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author diana
 */
public class ConceptoPago {
    private int codigo;
    private String descripcion;
    private String metodoPago;
    private Date fecha;
    private double monto;
    private int reciboID;
    private String tipoEstrategia;
    private double montoBase;
    
    public String getTipoEstrategia() {
        return tipoEstrategia;
    }
    
    public void setTipoEstrategia(String tipoEstrategia) {
        this.tipoEstrategia = tipoEstrategia;
    }
    public double getMontoBase() {
        return montoBase;
    }
    
    public void setMontoBase(double montoBase) {
        this.montoBase = montoBase;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public int getReciboID() {
        return reciboID;
    }

    public void setReciboID(int reciboID) {
        this.reciboID = reciboID;
    }
    
}
