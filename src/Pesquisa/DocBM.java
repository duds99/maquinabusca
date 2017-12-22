/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pesquisa;

/**
 *
 * @author eduardofigueiredo
 */
public class DocBM {
    private int documento;
    private double simdjq;
    
    public DocBM(int documento, double simdjq){
        this.documento = documento;
        this.simdjq = simdjq;
    }

    /**
     * @return the documento
     */
    public int getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(int documento) {
        this.documento = documento;
    }

    /**
     * @return the simdjq
     */
    public double getSimdjq() {
        return simdjq;
    }

    /**
     * @param simdjq the simdjq to set
     */
    public void setSimdjq(double simdjq) {
        this.simdjq = simdjq;
    }
}
