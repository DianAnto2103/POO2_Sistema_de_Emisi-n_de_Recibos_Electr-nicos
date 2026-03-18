/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author diana
 */

import java.io.IOException;
import java.net.URL;
import org.json.JSONObject;


public class ConectarAPISunat {
    String RUC;
    
    public ConectarAPISunat(String RUC){
        this.RUC = RUC;   
    }
    
    public void Conexión(){
        try{
            String ruc = RUC;
            URL url = new URL("https://api.decolecta.com/v1/sunat/ruc/full?numero=" + ruc);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    
    private static JSONObject getRazonSocialconRUC(String RUC){
        RUC = RUC.replace("", "+");
        
        return null;
        
    }
    
    
}
