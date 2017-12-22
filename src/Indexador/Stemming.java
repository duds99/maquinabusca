/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eduardofigueiredo
 */
public class Stemming {
    private final Map<String,String> stemming;
    
    public Stemming(){
        this.stemming = stemmingWords();
    }
    
    private Map<String,String> stemmingWords(){
        Map<String,String> sufixos = new HashMap<>();
        String stopFile = "/home/admin/web/efjcode.com.br/public_html/assets/apps/sufixos.txt";        
        String line;

        try(FileReader fr = new FileReader(stopFile);
            BufferedReader br = new BufferedReader(fr)) {
            while ((line = br.readLine()) != null) {
                sufixos.put(line.trim(),line.trim());
            }
            br.close();
        }
        catch (Exception ex) {
        }
        return sufixos;
    }
    
    public String stemming(String termo){
        for(int i=5;i>0;i--){     
            if(termo.length() > i){
                if(this.stemming.get(termo.substring(termo.length()-i, termo.length())) != null){
                    return termo.substring(0,termo.length()-i);
                }
            }
        }
        return termo;
    }
}
