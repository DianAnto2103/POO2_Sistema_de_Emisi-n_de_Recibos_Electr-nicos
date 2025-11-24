/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

import javax.swing.table.DefaultTableModel;
import model.FacadeCliente.FacadeListadoClientes;
import vista.ListarClientesView;

/**
 *
 * @author diana,flavia,darien
 */
public final class ListaClientesController {
    //se trae la vista
    private final ListarClientesView vistaListaCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    
    //Se trae a facadeCliente
    private final FacadeListadoClientes facadeCliente;
    
     //este es el constructor, donde se trae la vista y la navegación. 
    public ListaClientesController(ListarClientesView vistaCliente, NavegacionCliente navegacion){
        this.vistaListaCliente = vistaCliente;
        this.navegacion = navegacion;
        //Se iniacializa facade cliente
        this.facadeCliente = new FacadeListadoClientes();
        configurarEventos(); //esto es para la configuracion de eventos como => ir a la pantalla agregar cleinte, ir a la pantalla buscar cliente. 
        cargarClientes();
    }
    
    public void configurarEventos(){
        vistaListaCliente.getBotonAgregarCliente().addActionListener(e -> navegacion.mostrarAgregarCliente());
        // Configura el botón modificar cliente para editar cliente seleccionado
        vistaListaCliente.getBotonModificarCliente().addActionListener(e -> seleccionarCliente());
        
        vistaListaCliente.getbotonBuscar().addActionListener(e -> buscarClientes());
        vistaListaCliente.getTxtBuscar().addActionListener(e -> buscarClientes());
        
        vistaListaCliente.getRadioRUC().addActionListener(e -> buscarClientes());
        vistaListaCliente.getRadioRazonSocial().addActionListener(e -> buscarClientes());
        
    }
    
    public void seleccionarCliente(){
        // Obtener la fila seleccionada en la tabla de clientes
        int filaSeleccionada = vistaListaCliente.getTablaCliente().getSelectedRow();
            
        // Verificar si el usuario ha seleccionado alguna fila. Si es -1 entonces NO hay ninguna fila selccionada
        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(vistaListaCliente, "Por favor, seleccione un cliente de la lista!!", 
            "Selección requerida", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
        }
            
        // Obtener el ID del cliente desde la columna 0 de la fila seleccionada. Tiene que tener el id del cliente la columna
        int idCliente = (int) vistaListaCliente.getTablaCliente().getValueAt(filaSeleccionada, 0);
               
        // Navegar al formulario de modificación pasando el ID del cliente seleccionado
        navegacion.mostrarModificarCliente(idCliente);      
    }
    
    private void buscarClientes() {
        String criterio = vistaListaCliente.getTxtBuscar().getText().trim();
        String tipoBusqueda = vistaListaCliente.getTipoBusqueda();
        DefaultTableModel model;
    
        if (criterio.isEmpty()) {
            model = facadeCliente.obtenerClientesParaTabla();
        } else {
            switch (tipoBusqueda) {
                case "RUC":
                    model = facadeCliente.buscarClientesPorRUC(criterio);
                    break;
                case "RAZON_SOCIAL":
                    model = facadeCliente.buscarClientesPorRazonSocial(criterio);
                    break;
                default:
                    // Si no hay radio seleccionado, mostrar todos
                    model = facadeCliente.obtenerClientesParaTabla();
                    break;
            }
        }
        vistaListaCliente.getTablaCliente().setModel(model);
    }
    
    public void cargarClientes(){
        DefaultTableModel modelo = facadeCliente.obtenerClientesParaTabla();
        vistaListaCliente.getTablaCliente().setModel(modelo);
    }
    
    
    public ListarClientesView getVistaListaCliente() {
        return vistaListaCliente;
    }
    
    
}
