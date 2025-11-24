/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Clientes;

/**
 *
 * @author diana
 */

import vista.AgregarClienteView;
import vista.ModificarClienteView;
import vista.ListarClientesView;
import javax.swing.*;

//la clase más importante, es para la coordinacion entre los demás controllers

public final class ClientesCoordinadorController implements NavegacionCliente {
    private final JFrame frameClientes; //es para el constructor, para cuando se coloque en MenuController se pueda colocar el frame momentaneo.
    //se traen los controllers
    private ListaClientesController controllerListaClientes;
    private AgregarClienteController controllerAgregarClientes;
    private ModificarClienteController controllerModificarClientes;
    
    //el constructor que tiene como parametro un JFrame, como se ha explicado anteriormente, es para traer el fraem momentaneo. 
    public ClientesCoordinadorController(JFrame frameClientes){
        this.frameClientes = frameClientes;
        inicializarControllers();
        mostrarListaClientes(); 
    }
    
    
    //se inicializa los controllers.
    public void inicializarControllers(){
        ListarClientesView vistaListaClientes = new ListarClientesView();
        AgregarClienteView vistaAgregarClientes = new AgregarClienteView();
        ModificarClienteView vistaModificarClientes = new ModificarClienteView();
        
        this.controllerListaClientes = new ListaClientesController(vistaListaClientes, this); //se pasa esta misma clase como NAVEGACIÓN, porque es el que maneja todo. 
        this.controllerAgregarClientes= new AgregarClienteController(vistaAgregarClientes, this); //igual aqui
        this.controllerModificarClientes = new ModificarClienteController(vistaModificarClientes, this);
    }
    
    @Override
    public void recargarListaClientes() 
    {
       // Llama al método recargarClientes del ListaClientesController
       controllerListaClientes.cargarClientes();
    }
    
    @Override
    public void mostrarListaClientes(){ 
        mostrarVista(controllerListaClientes.getVistaListaCliente()); //utilzia el metodo mostrarVista que esta al final. 
        //Le pasa el getVista que anteriormente habiamos creado en cada clase controller. 
    }
    @Override
    public void mostrarAgregarCliente(){
        mostrarVista(controllerAgregarClientes.getVistaAgregarCliente());
    }
    @Override
    public void mostrarModificarCliente(){
        mostrarVista(controllerModificarClientes.getVistaBuscarCliente());
    }
    
    @Override
    public void cerrarVentana(){ //este es para los botones: "Salir"
        frameClientes.dispose();
    }
    
    private void mostrarVista(JPanel vista) { /*aqui esta la el metodo mostrarVista, este es el que se encarga de borrar todo el contenido 
        anterior, y se encarga de agregar las vistas dependiendo de lo que vista se esta requieriendo.*/
        frameClientes.getContentPane().removeAll();
        frameClientes.getContentPane().add(vista);
        frameClientes.pack();
        frameClientes.setLocationRelativeTo(null);
        frameClientes.setResizable(false);
        frameClientes.revalidate();
        frameClientes.repaint();
    }
}
