/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

/**
 *
 * @author diana
 */

import java.net.URL;
import javax.swing.JOptionPane;
import model.FacadeCliente.FacadeRegistroCliente;
import vista.AgregarClienteView;
import Service.APISunatConexion;
import java.util.Scanner;
import org.json.JSONObject;

//este es el controlador para la vista de agregar cliente.
public final class AgregarClienteController {
    //se trae la vista
    private final AgregarClienteView vistaAgregarCliente;
    //se trae la interfaz navegación que tiene las funciones principales para "navegar" entre pantallas. 
    private final NavegacionCliente navegacion;
    //se trae facade Registro
    private final FacadeRegistroCliente facadeRegistro;
    
    private final APISunatConexion conexion;
    
    private String razonSocial = "no funciono";
    
    //este es el constructor, donde se trae la vista y la navegación. 
    public AgregarClienteController(AgregarClienteView vistaAgregarCliente, NavegacionCliente navegacion) {
        this.vistaAgregarCliente = vistaAgregarCliente;
        this.navegacion = navegacion;
        this.facadeRegistro = new FacadeRegistroCliente();
        this.conexion = new APISunatConexion(); 
        configurarEventos(); //esto es para la configuracion de eventos como => volver a pantalla mostrarCliente, cerrarVentana, etc. 
    }
    
    public void configurarEventos(){
        vistaAgregarCliente.getBotonRuc().addActionListener(e -> autoCompletarConexion());
        vistaAgregarCliente.getBotonVolver().addActionListener(e -> navegacion.mostrarListaClientes());
        vistaAgregarCliente.getBotonAgregar().addActionListener(e -> agregarCliente());
        vistaAgregarCliente.getBotonLimpiar().addActionListener(e -> limpiarFormulario());
    }
    
    public void agregarCliente(){
        try
        {
            String ruc = vistaAgregarCliente.getRUC();
            String razonSocial = vistaAgregarCliente.getRazonSocial();
            String telefono = vistaAgregarCliente.getTelefono();
            double mensualidad = Double.parseDouble(vistaAgregarCliente.getMensualidad().trim());
            
            if(razonSocial.matches("\\d+")){
                JOptionPane.showMessageDialog(vistaAgregarCliente, "La razón social no tiene el formato adecuado.");
                return;
            }
            if(!telefono.matches("\\d+")){
                JOptionPane.showMessageDialog(vistaAgregarCliente, "El teléfono debe contener solo números.");
            }
            
            
            boolean exito = facadeRegistro.registrarCliente(ruc, razonSocial, telefono, mensualidad);
            
            if (exito) 
            {
                JOptionPane.showMessageDialog(vistaAgregarCliente, "Cliente registrado exitosamente!!");
                limpiarFormulario();  
                //Primero se debe recargar la lista de clientes
                navegacion.recargarListaClientes();
                //Luego recien se muestra nuevamente la lista...
                navegacion.mostrarListaClientes();
            } else {
                JOptionPane.showMessageDialog(vistaAgregarCliente, 
                    "Error: RUC ya existe o datos invalidos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaAgregarCliente, 
                "Error: La mensualidad debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAgregarCliente, 
                "Error al agregar cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void autoCompletarConexion()
    {
        String RUC = vistaAgregarCliente.getRUC();
        if(RUC != null){
            conexion.establecerConexion(RUC);
            TraerDatos();
            vistaAgregarCliente.setRazonSocial(razonSocial);
        }  
    }
    
    public void TraerDatos(){
        try{
            if(conexion.getCodigo() != 200){
                JOptionPane.showMessageDialog(vistaAgregarCliente, 
                        "No se ha obtenido los datos del RUC ingresado. Por favor, verifique el RUC."
                );

            } else{
                StringBuilder informationString = new StringBuilder();
                Scanner lectura = new Scanner(conexion.getURL().openStream());
                
                while(lectura.hasNext()){
                    informationString.append(lectura.nextLine());
                }
                
                lectura.close();
                
                //Traer datos (en este caso solo el RUC)
                
                JSONObject objectJSON = new JSONObject(informationString.toString());
                
                razonSocial = objectJSON.getString("razon_social");
          
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void limpiarFormulario(){
        vistaAgregarCliente.setRUC("");
        vistaAgregarCliente.setRazonSocial("");
        vistaAgregarCliente.setTelefono("");
        vistaAgregarCliente.setMensualidad("");
    }
    
    public AgregarClienteView getVistaAgregarCliente() { //un getter para que lo pueda recibir la coordinacion. Ir a Clientes Coordinador Controller
        return vistaAgregarCliente;
    }
    
}
