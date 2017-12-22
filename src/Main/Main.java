/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Coletor.PreFilter;
import Indexador.Tokenizer;
import Pesquisa.Consulta;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eduardofigueiredo
 */
public final class Main {

    public Main() throws IOException{
        Coletor();
    }
    
    public void Coletor() throws IOException{
        PreFilter prefiltro = new PreFilter(); 
        new Thread() {
            @Override
            public void run() {
                try {
                    prefiltro.LivingPeople();
                    System.out.println("Terminou pessoas vivas");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();     
        new Thread() {
            @Override
            public void run() {
                try {
                    prefiltro.DeadPeople();
                    System.out.println("Terminou pessoas mortas");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    prefiltro.Country();
                    System.out.println("Terminou paises");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start(); 
        new Thread() {
            @Override
            public void run() {
                try {
                    prefiltro.Cities();
                    System.out.println("Terminou cidades");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    public static void main(String[] args) throws IOException{
        //coletor FEITO
        //Main coletar = new Main();
        //Tokenizer index = new Tokenizer();
        Consulta a1 = new Consulta("brazil");
    }
}
