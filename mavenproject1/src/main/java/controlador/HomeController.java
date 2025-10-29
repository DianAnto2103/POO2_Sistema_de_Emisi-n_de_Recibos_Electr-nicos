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
public class HomeController {
    MenuPrincipalView vistaMenu = new MenuPrincipalView();
    MenuController menu = new MenuController(vistaMenu);
    {
    vistaMenu.setVisible(true);
    }
}
