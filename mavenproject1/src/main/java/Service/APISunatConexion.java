/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author diana
 */
public class APISunatConexion {
    private URL url;
    private int responseCode;
    private String razonSocial;
    private String token = "sk_13952.3rhQwUhVmyrdLyakufvxSI1YY3IcK0SA";
    private String RUC;
    
    public void establecerConexion(String RUC){
        try{
            url = new URL("https://api.decolecta.com/v1/sunat/ruc?numero=" + RUC + "&token=" + token);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode = connection.getResponseCode();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getCodigo(){
        return responseCode;
    }
    
    public URL getURL(){
        return url;
    }
    
    
}
