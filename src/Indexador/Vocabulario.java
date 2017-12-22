/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexador;

import java.util.List;

/**
 *
 * @author eduardofigueiredo
 */
public class Vocabulario {
    private String termo;
    private List<DocFreq> documentos;
    private double idf;
    private double tf;
    private int frequencia;
    private int ni;
    
    public Vocabulario(String termo, List<DocFreq>documentos, int ni){
        this.termo = termo;
        this.documentos = documentos;
        this.ni = ni;
    }

    /**
     * @return the documentos
     */
    public List<DocFreq> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<DocFreq> documentos) {
        this.documentos = documentos;
    }

    /**
     * @return the ni
     */
    public int getNi() {
        return ni;
    }

    /**
     * @param ni the ni to set
     */
    public void setNi(int ni) {
        this.ni = ni;
    }

    /**
     * @return the termo
     */
    public String getTermo() {
        return termo;
    }

    /**
     * @param termo the termo to set
     */
    public void setTermo(String termo) {
        this.termo = termo;
    }
}
