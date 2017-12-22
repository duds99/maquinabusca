/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eduardofigueiredo
 */
public class Index {
    private Map<String, Vocabulario> inversao;
    
    public Index(){
        inversao = new HashMap<String,Vocabulario>();
        File fw = new File("/home/admin/web/efjcode.com.br/public_html/assets/apps/indice.txt");
        fw.delete();
        fw = new File("/home/admin/web/efjcode.com.br/public_html/assets/apps/indice.bin");
        fw.delete();
        fw = new File("/home/admin/web/efjcode.com.br/public_html/assets/apps/lendj.txt");
        fw.delete();
    }
    
    public void indexar(String termo, long documento){
        boolean existe = false;
               
        if(inversao.get(termo) != null && !termo.equals("") && termo.length()>2){
            existe = true;
        }   
        //insere ou atualiza campo
        if(existe == true){
                Vocabulario a1 = inversao.get(termo);
                List<DocFreq> documentos = a1.getDocumentos();
                int tamanho = documentos.size();
                if(documentos.get(tamanho-1).getDocumento() == documento){
                    //incrementa + 1 na frequencia deste termo no documento
                    int frequenciadoc = documentos.get(tamanho-1).getFrequencia() + 1;
                    //deleta o documento da lista de docfreq
                    documentos.remove(tamanho-1);
                    //cria objeto de documento frequencia e coloca o documento com frequencia antiga + 1
                    DocFreq docfreq = new DocFreq(documento, frequenciadoc);
                    //adiciona o novo docfrec na lista de docfreq
                    documentos.add(docfreq);
                    //adiciona a lista docfreq no objeto vocabulario
                    a1.setDocumentos(documentos);          
                }else{
                    //documento novo acrescenta + 1 no ni e cria um objeto docfreq com a frequencia 1 no documento
                    a1.setNi(a1.getNi() + 1);
                    DocFreq docfreq = new DocFreq(documento,1);
                    //adiciona o objeto a lista de docfreq
                    documentos.add(docfreq);
                    //adiciona a lista ao objeto Vocabulario
                    a1.setDocumentos(documentos);
                }
                //remove o antigo do map e insere o novo com o novo objeto
                inversao.remove(termo);
                inversao.put(termo,a1);
        }else{
            //nao existe o documento no vocabulario
            if(!termo.equals("") && termo.length()>2){ 
                List<DocFreq> documentos = new ArrayList<DocFreq>();
                //cria objeto de documento frequencia e coloca o documento com frequencia 1
                DocFreq docfreq = new DocFreq(documento, 1);
                //adiciona o objeto acima em uma lista do mesmo objeto
                documentos.add(docfreq);
                //insere a lista de docfreq com ni = 1
                Vocabulario a1 = new Vocabulario(termo,documentos,1);
                inversao.put(termo,a1);
            }
        }
    }
    
    public void mostrarIndice(){
        for(Vocabulario it:inversao.values()){
           System.out.print(it.getTermo());
           int tamanho = it.getDocumentos().size();
           List<DocFreq> docfreq = it.getDocumentos();
           for(int i=0;i<tamanho;i++){
               System.out.print("\t" + docfreq.get(i).getDocumento() + " " + docfreq.get(i).getFrequencia());
           }
           
           System.out.println("\t" + it.getNi());
        }
    }
    
    public void escreverBinario(long documento,long avglen) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream("indice.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        oos.writeBytes(documento + "\n" + avglen + "\n");
        
        for(Vocabulario it:inversao.values()){
           oos.writeBytes(it.getTermo() + "\t" + it.getNi());
           int tamanho = it.getDocumentos().size();
           List<DocFreq> docfreq = it.getDocumentos();
           for(int i=0;i<tamanho;i++){
               oos.writeBytes("\t" + docfreq.get(i).getDocumento() + " " + docfreq.get(i).getFrequencia());
           }
          oos.writeBytes("\n");
        }
        oos.close();
        fos.close(); 
    }
    
    public void escreverIndice(long documento, long avglen) throws IOException{
        FileWriter fw = new FileWriter("indice.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("" + documento);
        bw.newLine();
        bw.write("" + avglen);
        bw.newLine();
        
        for(Vocabulario it:inversao.values()){
           bw.write(it.getTermo() + "\t" + it.getNi());
           int tamanho = it.getDocumentos().size();
           List<DocFreq> docfreq = it.getDocumentos();
           for(int i=0;i<tamanho;i++){
               bw.write("\t" + docfreq.get(i).getDocumento() + " " + docfreq.get(i).getFrequencia());
           }
          bw.newLine();  
        }
        bw.close();
        fw.close();   
    }
}
