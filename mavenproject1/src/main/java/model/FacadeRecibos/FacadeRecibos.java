/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

/**
 *
 * @author diana
 */
import Repository.RepositorioRecibo;
import Repository.RepositorioReciboImp;

public class FacadeRecibos {
    private final GeneradorNumeracionService generadorNumeros;
    private final RepositorioRecibo repoRecibo;
    
    public FacadeRecibos() {
        this.generadorNumeros = new GeneradorNumeracionService();
        this.repoRecibo = new RepositorioReciboImp();
    }
    
    public String generarNuevoNumeroRecibo() {
        // 1. Obtener último número de la BD
        String ultimoNumero = repoRecibo.obtenerUltimoNumeroRecibo();
        
        // 2. Usar el servicio para generar el nuevo número
        return generadorNumeros.generarNumeroRecibo(ultimoNumero);
    }
}
