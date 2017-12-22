/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coletor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author eduardofigueiredo
 */
public class Writer {
    private File arquivo;
    
    public Writer() throws IOException{
        arquivo = new File("banco.txt");
        boolean existe = arquivo.exists();
        if(!existe){
            arquivo.createNewFile();
        }
    }
    
    synchronized public void escrever(String print) throws IOException{
        FileWriter fw = new FileWriter("banco.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(print);
        bw.newLine();
        
        bw.close();
        fw.close();
    }
}
