/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.MenuPrincipalView;

/**
 *
 * @author diana
 */

//esta clase es la que interactua con el main.
public class HomeController {
    //aqui es donde se instancia el view del menu principal y su controller, solo interactua con ellos.
    MenuPrincipalView vistaMenu = new MenuPrincipalView();
    MenuController menu = new MenuController(vistaMenu);
    {
    vistaMenu.setVisible(true);
    }
}
