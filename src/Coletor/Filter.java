/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coletor;

import java.util.ArrayList;

/**
 *
 * @author eduardofigueiredo
 */
public class Filter {
    private ArrayList<String> excecoes;
    
    public Filter(){
        excecoes = new ArrayList<String>();
        AddExceptions();
    }
    
    public void AddExceptions(){
        excecoes.add("Especial:Categorias");
        excecoes.add("Ajude-nos");
        excecoes.add("Wikipédia:Sobre");
        excecoes.add("Editar");
        excecoes.add("Help:Category");
        excecoes.add("Support us");
        excecoes.add("Category:သက်ရှိထင်ရှားပုဂ္ဂိုလ်များ – Burmese");
        excecoes.add("Category:存命人物 – Japanese");
        excecoes.add("Kategorie:Läbigi Person – Alemannisch");
        excecoes.add("Category:Nolol – Somali");
        excecoes.add("Category:在生嘅人 – Cantonese");
        excecoes.add("Category:在世人物 – Chinese");
        excecoes.add("Wikipedia:About");
        excecoes.add("Wikipedia:Shortcut");
        excecoes.add("Help:Categories");
        excecoes.add("Wikipedia:NPOV");
        excecoes.add("Wikipedia:AD");
        excecoes.add("Wikipedia:CITE");
        excecoes.add("Collation");
        excecoes.add("Template:L");
        excecoes.add("Living_people_on_EN_wiki_who_are_dead_on_other_wikis");
        excecoes.add("Wikipedia:FAQ/Categorization");
        excecoes.add("File:Gulab Prem Kumar.jpg");
        excecoes.add("");
        
        for(int i=1940;i<=2017;i++){
            excecoes.add("Category:" + i);
            excecoes.add("Category:" + i + "年没 – Japanese");
            excecoes.add("Category:" + i + "年死 – Cantonese");
            excecoes.add("Category:" + i + "年逝世 – Chinese");
        }   
    }
    
    public boolean verificaEntidade(String title, String href){
        boolean resp = true;
        String[] entidades = title.split(" ");
        for(int j=0;j<entidades.length;j++){
            if(entidades[j].length() >= 3){
                if(entidades[j].charAt(0) < 'A' || entidades[j].charAt(0) > 'Z'){
                    return false;
                    //j = entidades.length;
                }
            }
        }

        //verifica excecoes coletadas das paginas
        for(int j=0;j<excecoes.size();j++){
            if(title.equals(excecoes.get(j))){
                return false;
            }
        }

        if(href.substring(0,10).equals("/wiki/Flag") || href.substring(0,10).equals("/wiki/flag") || href.substring(0,10).equals("/wiki/File") || href.substring(0,10).equals("/wiki/file") || href.substring(0,10).equals("/wiki/User")){
            return false;
        }
        return resp;
     }
}
