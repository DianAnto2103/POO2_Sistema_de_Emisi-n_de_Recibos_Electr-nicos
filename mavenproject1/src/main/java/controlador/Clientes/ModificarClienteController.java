/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

/**
 *
 * @author diana
 */
import javax.swing.JOptionPane;
import model.Cliente;
import model.FacadeCliente.FacadeActualizarCliente;
import vista.ModificarClienteView;

//esta es el controlador para la vista modificar clientes
public final class ModificarClienteController {
    //se trae la vista
    private final ModificarClienteView vistaModificarCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    // Fachada que encapsula la lógica compleja de actualización de clientes
    private final FacadeActualizarCliente facadeActualizar;
    // Identificador unico del cliente que se esta modificando
    private final int idCliente; //traer ID cliente para poder modificar
    
    
    //este es el constructor, donde se trae la vista y la navegación. 
    public ModificarClienteController(ModificarClienteView vistaModificarCliente, NavegacionCliente navegacion, int idCliente) {
        this.vistaModificarCliente = vistaModificarCliente; 
        this.navegacion = navegacion;
        this.idCliente = idCliente;
        this.facadeActualizar = new FacadeActualizarCliente();
        configurarEventos(); //esto es para la configuracion de eventos como => volver a pantalla mostrarCliente, cerrarVentana, etc. 
        cargarDatosCliente(); // carga los datos actuales del cliente en el formulario
    }
    
    public void configurarEventos(){
        vistaModificarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaModificarCliente.getBotonSalir().addActionListener(e -> navegacion.cerrarVentana());
        vistaModificarCliente.getBotonGuardar().addActionListener(e -> guardarCambios());
    }
    
    // Procesa y guarda los cambios realizados en el formulario de modificación
    private void guardarCambios() { 
        try {
            // Obtiene los datos modificados del formulario
            String razonSocial = vistaModificarCliente.getRazonSocial();
            String telefono = vistaModificarCliente.getTelefono();
            double mensualidad = Double.parseDouble(vistaModificarCliente.getMensualidad());
            
            // Delegar la actualización a la fachada especializada
            boolean exito = facadeActualizar.actualizarCliente(idCliente, razonSocial, telefono, mensualidad);
            
            if (exito) {
            // Notificar éxito al usuario y regresar a la lista actualizada
                JOptionPane.showMessageDialog(vistaModificarCliente, "Cliente actualizado exitosamente");
                navegacion.recargarListaClientes();
                navegacion.mostrarListaClientes();
            } else {
            // Notificar error genérico de actualización
                JOptionPane.showMessageDialog(vistaModificarCliente, 
                "Error al actualizar cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Captura error específico de formato numérico en la mensualidad
            JOptionPane.showMessageDialog(vistaModificarCliente, 
            "Error: La mensualidad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Carga los datos actuales del cliente desde la base de datos al formulario
    private void cargarDatosCliente() {
        // Obtener el cliente completo desde la base de datos usando su ID
        Cliente cliente = facadeActualizar.obtenerClientePorID(idCliente);
        if (cliente != null) {
            // Rellenar el formulario con los datos existentes del cliente
            vistaModificarCliente.setCodCliente(cliente.getID());
            vistaModificarCliente.setRUC(cliente.getRUC()); // RUC es de solo lectura
            vistaModificarCliente.setRazonSocial(cliente.getRazonSocial());
            vistaModificarCliente.setTelefono(cliente.getTelefono());
            vistaModificarCliente.setMensualidad(String.valueOf(cliente.getMensualidad()));
        } else {
            // Manejar caso donde el cliente no existe en la base de datos
            JOptionPane.showMessageDialog(vistaModificarCliente, 
                "Error: Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            navegacion.mostrarListaClientes(); // Regresar a la lista de clientes
        }
    }
    
    // Proporciona acceso a la vista para el coordinador de navegación
    public ModificarClienteView getVistaBuscarCliente() { //un getter para que lo pueda recibir la coordinacion. Ir a Clientes Coordinador Controller
        return vistaModificarCliente;
    }   
}
