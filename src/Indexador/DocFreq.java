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
public class DocFreq {
    private long documento;
    private int frequencia;
    
    public DocFreq(long documento, int frequencia){
        this.documento = documento;
        this.frequencia = frequencia;
    }

    /**
     * @return the documento
     */
    public long getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(long documento) {
        this.documento = documento;
    }

    /**
     * @return the frequencia
     */
    public int getFrequencia() {
        return frequencia;
    }

    /**
     * @param frequencia the frequencia to set
     */
    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }
    
    
}
