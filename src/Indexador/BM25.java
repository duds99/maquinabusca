/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

/**
 *
 * @author eduardofigueiredo
 */
public class BM25 {
    double k1;
    double b;
    
    public BM25(double k1, double b){
        this.k1 = k1;
        this.b = b;
    }
    
    public double Bij(long lendj, long avgdoc, int fij, int N, int ni){
        double numerador = (this.k1 + 1) * fij;
       
        double denominador = (this.k1 * ((1 - this.b) + (b * (lendj/avgdoc)))) + fij;
        
        double bij = numerador/denominador;
        
        double simdjq = bij * (Math.log((N - ni + 0.5) / (ni + 0.5)));
        
        return simdjq;
    }
}
