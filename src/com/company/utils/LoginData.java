package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Created by stopp on 28/06/2017.
 */
public class LoginData {
    String user,pass,enco;
    boolean log=false;
    public LoginData(String user,String pass){
        try {
            String enco=new String(Base64.getEncoder().encode((user+":"+pass).getBytes()));
            HttpURLConnection connection = (HttpURLConnection) new URL("https://myanimelist.net/api/account/verify_credentials.xml").openConnection();
            connection.setRequestProperty  ("Authorization", "Basic " + enco);
            new BufferedReader (new InputStreamReader(connection.getInputStream()));
            this.user=user;this.pass=pass;this.enco=enco;log=true;
        } catch(IOException e) {}
    }
    public String  getUsername(){return user;}
    public String  getPassword(){return pass;}
    public String  getEncoded (){return enco;}
    public boolean isLogged   (){return  log;}
}
