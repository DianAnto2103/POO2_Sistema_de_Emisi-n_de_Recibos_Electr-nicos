/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author diana
 */
public class Recibo {
    private int ID;
    private String numeroRecibo;
    private Date fechaEmision;
    private double total;
    private Cliente cliente;
    private List<ConceptoPago> conceptos;
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ConceptoPago> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptoPago> conceptos) {
        this.conceptos = conceptos;
    }
    
}
