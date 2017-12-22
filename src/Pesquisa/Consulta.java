/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pesquisa;

import Indexador.BM25;
import Indexador.Parser;
import Indexador.Stemming;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eduardofigueiredo
 */
public final class Consulta {
    Map <String,String> valores;
    private final Map<String,String> stopwords;
    DocBM documentos[];
    List <Integer> lendj;
    List <Long> lendjchar;
    Parser parser;
    Stemming stemming;
    
    public Consulta(String q) throws IOException{
        //long inicio = System.currentTimeMillis();
        
        lendjchar = new ArrayList<>();
        lendj = new ArrayList<>();
        carregaLen();
        this.valores = new LinkedHashMap<>();
        this.stopwords = stopWords();
        stemming = new Stemming();
        parser = new Parser();
        ranking(q);
        
        //long finall = System.currentTimeMillis();
        //System.out.println(finall - inicio);
    }
    
    public void ranking(String q) throws IOException{
        String consulta[] = q.split(" ");
        
        
        for(int i=0;i<consulta.length;i++){ 
            consulta[i] = this.parser.parser(consulta[i]);

            //stopwords primeira vez
            consulta[i] = stopW(consulta[i]);

            //realiza a remoção de sufixos
            consulta[i] = this.stemming.stemming(consulta[i]);

            //stopwords segunda vez
            consulta[i] = stopW(consulta[i]);
            
            if(!consulta[i].equals(" ") && consulta[i].length() > 2){
                buscaindice(consulta[i]);
            } 
        }
        
        documentos = new DocBM[valores.size()];
        
        int i = 0;
        for (Map.Entry it : valores.entrySet()) {
            documentos[i] = new DocBM(Integer.parseInt((String) it.getKey()), Double.parseDouble((String) it.getValue()));
            i++;
        }
        //ordena o array
        sort();

        int array[] = new int[105];
        int cont = 0;
        
        for(int j = documentos.length-1;j>=0;j--){
            if(cont<15){
                array[cont] = documentos[j].getDocumento();
                cont++;
            }
        }
        
        RandomAccessFile leitura = new RandomAccessFile("banco.txt","r");
        for(int j=0;j<cont;j++){
            leitura.seek(lendjchar.get(array[j]-2));
            String linha = leitura.readLine();
            byte[] ptext = linha.getBytes(ISO_8859_1); 
            linha = new String(ptext, UTF_8); 
            System.out.println(linha);
        }
        leitura.close();
    }
    
    private void buscaindice(String termo) throws FileNotFoundException, IOException{
        try{
            FileReader arq = new FileReader("indice.txt");
 
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();

            //valor de N da colecao
            int N = Integer.parseInt(linha);
            linha = lerArq.readLine();
            int avglen = Integer.parseInt(linha);
            BM25 bm25 = new BM25(1.0,0.75);

            boolean encontrou = false;
            
            while(linha != null){

                String explode[] = linha.split("\t");

                if(explode[0].equals(termo)){
                    encontrou = true;

                    //pega o valor de ni do documento
                    int ni = Integer.parseInt(explode[1]);

                    for(int i=2;i<explode.length;i++){

                        //separa o documento da frequencia
                        String explodemenor[] = explode[i].split(" ");

                        //pesquisa no documento lendj o valor do len do documento -1 é o correto
                        int lenthdj = this.lendj.get(Integer.parseInt(explodemenor[0])-1);

                        //calcula o valor do bm25 daquele termo da consulta
                        double resp = bm25.Bij((long)lenthdj, (long)avglen, Integer.parseInt(explodemenor[1]),N,ni);
                        //System.out.println("termo= " + termo + " avglen= " + avglen + " ni= " + ni + " fij= " + explodemenor[1] + " documento= " + explodemenor[0] + " lendj= " + lenthdj + " bm25= " + resp);

                        //se nao existir o termo apenas insere o numero do documento com o dim djq, 
                        //se existir realiza o somatorio com o valor q ja existe
                        if(valores.get(explodemenor[0]) == null){
                            valores.put(explodemenor[0], "" + resp);
                        }else{
                            resp = Double.parseDouble(valores.get(explodemenor[0])) + resp;
                            valores.remove(explodemenor[0]);
                            valores.put(explodemenor[0], "" + resp);
                        }
                    }
                }
                if(encontrou == true){
                    linha = null;
                }else{
                    linha = lerArq.readLine();
                }
            }
        }catch(IOException | NumberFormatException e){}
    }
    
    private void carregaLen() throws FileNotFoundException, IOException{
        try{
            FileReader arq = new FileReader("lendj.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();

            while(linha != null){
                String explode[] = linha.split("\t");
                lendj.add(Integer.parseInt(explode[0]));
                lendjchar.add(Long.parseLong(explode[1]));
                linha = lerArq.readLine();
            }
        }catch(IOException | NumberFormatException e){
        }
    }
      
    private Map<String,String> stopWords(){
        Map<String,String> palavrasirrelevantes = new HashMap<>();
        String stopFile = "stop.txt";        
        String line;

        try(FileReader fr = new FileReader(stopFile);
            BufferedReader br = new BufferedReader(fr)) {
            while ((line = br.readLine()) != null) {
                palavrasirrelevantes.put(line.trim(),line.trim());
            }
            br.close();
        }
        catch (Exception ex) {
        }
        return palavrasirrelevantes;
    }
    
    private String stopW(String termo){
        if(this.stopwords.get(termo) != null){
            termo = "";
        }
        return termo;
    }
    
    private DocBM[] numbers;
    private int number;
    
    public void sort(){
        if (documentos ==null || documentos.length==0){
                return;
        }
        this.numbers = documentos;
        number = documentos.length;
        quicksort(0, number - 1);
    }

    private void quicksort(int low, int high){
        int i = low, j = high;
        DocBM pivot = numbers[low + (high-low)/2];
        while (i <= j) {
            while (numbers[i].getSimdjq() < pivot.getSimdjq()){
                i++;
            }

            while (numbers[j].getSimdjq() > pivot.getSimdjq()){
                j--;
            }
            if (i <= j){
                exchange(i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j){
            DocBM temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
    }
}
