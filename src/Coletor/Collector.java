 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coletor;

import java.io.*;
import org.jsoup.Jsoup;

/**
 *
 * @author eduardofigueiredo
 */
public class Collector {  
    Filter filtro;
    Writer writer;
  
    public Collector() throws IOException{
        filtro = new Filter();
        writer = new Writer();
    }
    
    public void getContent(String url) throws IOException{
        try{
            org.jsoup.nodes.Document doc =  Jsoup.connect(url).get();
            org.jsoup.select.Elements link = doc.select("a");  

            //percorre array com todos os <a></a> da pagina
            for(int i=0;i<link.size();i++){
                String href = link.get(i).attr("href");
                String title = link.get(i).attr("title");

                //pega apenas links que contem /wiki/ acesso interno e verifica se o mesmo possui caracteristicas de nome proprio
                if(href.length()>=6){  
                    if(href.substring(0,6).equals("/wiki/")){
                       
                        //filtro de entidade
                        boolean entidadeverdade = filtro.verificaEntidade(title,href);
                                            
                        //verifica se é entidade, captura conteudo da pagina e escreve no arquivo o link + titulo + conteudo , apenas 2 linhas de conteudo
                        if(entidadeverdade == true){
                            href = "https://en.wikipedia.org" + href;
                            try{
                                org.jsoup.nodes.Document documento =  Jsoup.connect(href).get();
                                org.jsoup.select.Elements div = documento.getElementsByClass("mw-content-ltr");
                                org.jsoup.select.Elements paragrafo = div.select("p");
                                
                                //parse do texto em html para texto
                                String print = href + "\t" + title + "\t" + paragrafo.get(0).text() + " " + paragrafo.get(1).text();
                                
                                writer.escrever(print);
                            }catch(Exception e){
                                continue;
                            }         
                        }//fim if entidadeverdade
                    }//fim if substring 0,6
                }//fim if com length
            }//fim for com links da pagina 
        }catch(Exception e){
        }
    }
}