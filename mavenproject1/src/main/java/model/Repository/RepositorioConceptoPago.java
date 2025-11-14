/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Repository;

import model.ConceptoPago;
import java.util.List;

/**
 *
 * @author diana
 */
public interface RepositorioConceptoPago {
    ConceptoPago buscarporID(int id);
    List<ConceptoPago> buscarTodos();
    void guardar(ConceptoPago concepto);
    void actualizar(ConceptoPago concepto);
    void eliminar(ConceptoPago concepto);   
}
