/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FacadeRecibos;

import java.util.Date;
import model.ConceptoPago;

/**
 *
 * @author diana
 */
public class ValidadorConceptoService {
    public boolean validarConcepto(ConceptoPago concepto) {
        return concepto.getDescripcion().length() >= 5 && 
               concepto.getMonto() > 0 &&
               !concepto.getFecha().after(new Date());
    }
}
