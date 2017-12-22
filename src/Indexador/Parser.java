/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import java.text.Normalizer;

/**
 *
 * @author eduardofigueiredo
 */
public class Parser {
    
    public Parser(){
    
    }
    
    public String parser(String termo){
       termo = termo.trim().toLowerCase();
       termo = termo.replaceAll("[^a-z]", "");
       termo = Normalizer.normalize(termo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
       return termo;
    }
}
