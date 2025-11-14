/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Reportes;

import vista.ListaClientesDeudoresView;

/**
 *
 * @author diana
 */
public final class ListaDeudoresController {
    private final ListaClientesDeudoresView vistaListaDeudores;
    private final NavegacionReporte navegacion;
    
    public ListaDeudoresController(ListaClientesDeudoresView vistaListaDeudores,NavegacionReporte navegacion){
        this.vistaListaDeudores = vistaListaDeudores;
        this.navegacion = navegacion;
        configurarEventos(); 
    }
    
    public void configurarEventos(){
        this.vistaListaDeudores.getBotonVolver().addActionListener(e -> navegacion.mostrarModuloReportes());     
    }
    
    public ListaClientesDeudoresView getVistaListaDeudores() {
        return vistaListaDeudores;
    }
    
}
