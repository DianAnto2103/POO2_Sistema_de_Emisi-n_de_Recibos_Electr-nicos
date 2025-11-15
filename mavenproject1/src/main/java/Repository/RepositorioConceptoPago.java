/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repository;

import model.ConceptoPago;
import java.util.List;

/**
 *
 * @author diana
 */
public interface RepositorioConceptoPago {
    ConceptoPago buscarPorID(int id);
    List<ConceptoPago> buscarPorRecibo(int reciboID);
    void guardarTodos(List<ConceptoPago> conceptos);
    void guardar(ConceptoPago concepto);
    void actualizar(ConceptoPago concepto);
    void eliminar(ConceptoPago concepto);   
}
