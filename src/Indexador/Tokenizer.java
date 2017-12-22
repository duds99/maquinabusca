/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import Main.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author eduardofigueiredo
 */
public class Tokenizer {
    private Map<String,String> stopwords;
    private List<Integer> lendj;
    private List<Long> lendjchar;
    long documento;
    long avglen;
    Parser parser;
    Index indexar;
    Stemming stemming;
    
    public Tokenizer() throws IOException{
        lendj = new ArrayList<>();
        lendjchar = new ArrayList<>();
        this.stopwords = stopWords();
        this.indexar = new Index();
        this.stemming = new Stemming();
        this.parser = new Parser();
        this.documento = 1;
        this.avglen = 0;
        RemoveStop();
    }
    
    private void RemoveStop() throws FileNotFoundException, IOException{
        FileReader arq = new FileReader("/home/admin/web/efjcode.com.br/public_html/assets/apps/banco.txt");
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = lerArq.readLine();
        int lendj = 0;
        long lendjchar = 0;
        
        while(lerArq.ready()){
            String explode[] = linha.split("\t");
            
            if(explode.length > 2){
                //reune titulos e texto para indexar
                //String pegaPalavras[] = Stream.concat(Arrays.stream(explode[1].split(" ")), Arrays.stream(explode[2].split(" "))).toArray(String[]::new);
                String pegaPalavras[] = explode[1].split(" ");
                for(int i =0;i<pegaPalavras.length;i++){
                    avglen++;
                    //coloca palavras em minusculo, remove acentos e remove demais caracteres
                    pegaPalavras[i] = this.parser.parser(pegaPalavras[i]);
                  
                    //stopwords primeira vez
                    pegaPalavras[i] = stopW(pegaPalavras[i]);
                    
                    //realiza a remoção de sufixos
                    pegaPalavras[i] = this.stemming.stemming(pegaPalavras[i]);
                   
                    //stopwords segunda vez
                    pegaPalavras[i] = stopW(pegaPalavras[i]);
                    
                    //indexa o termo
                    this.indexar.indexar(pegaPalavras[i].trim(),this.documento); 
                    lendj++;
                }
            }   
            
            byte[] bytes = linha.getBytes();
            lendjchar += bytes.length + 1;
            this.lendjchar.add(lendjchar);
            this.lendj.add(lendj);
            lendj = 0;
            linha = lerArq.readLine();
            this.documento++;
        }
        escrever();
        indexar.escreverIndice(documento,avglen/documento);
        indexar.escreverBinario(documento,avglen/documento);
    }
    
    private Map<String,String> stopWords(){
        Map<String,String> stopwords = new HashMap<String,String>();
        String stopFile = "stop.txt";        
        String line;

        try(FileReader fr = new FileReader(stopFile);
            BufferedReader br = new BufferedReader(fr)) {
            while ((line = br.readLine()) != null) {
                stopwords.put(line.trim(),line.trim());
            }
            br.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return stopwords;
    }
    
    private String stopW(String termo){
        if(this.stopwords.get(termo) != null){
            termo = "";
        }
        return termo;
    }
    
    private void escrever() throws IOException{
        new Thread() {
            @Override
            public void run() {
                try {
                    FileWriter fw = new FileWriter("/home/admin/web/efjcode.com.br/public_html/assets/apps/lendj.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    for(int i=0;i<lendj.size();i++){
                        if(lendj.get(i) < 100){
                            bw.write("0" + lendj.get(i) + "\t" + lendjchar.get(i));
                            bw.newLine();
                        }else{
                            bw.write("" + lendj.get(i) + "\t" + lendjchar.get(i));
                            bw.newLine();
                        }
                    }

                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
