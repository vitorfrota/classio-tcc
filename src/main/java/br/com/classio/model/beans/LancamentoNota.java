/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.beans;

import java.util.Date;

/**
 *
 * @author vitor_9g3eyo1
 */
public class LancamentoNota {
    private Avaliacao conavacodigo;
    private Aluno conalucodigo;
    private Date condtlancamento;
    private Double connota;
    private String confeedback;

    /**
     * @return the conavacodigo
     */
    public Avaliacao getConavacodigo() {
        return conavacodigo;
    }

    /**
     * @param conavacodigo the conavacodigo to set
     */
    public void setConavacodigo(Avaliacao conavacodigo) {
        this.conavacodigo = conavacodigo;
    }

    /**
     * @return the conalucodigo
     */
    public Aluno getConalucodigo() {
        return conalucodigo;
    }

    /**
     * @param conalucodigo the conalucodigo to set
     */
    public void setConalucodigo(Aluno conalucodigo) {
        this.conalucodigo = conalucodigo;
    }

    /**
     * @return the condtlancamento
     */
    public Date getCondtlancamento() {
        return condtlancamento;
    }

    /**
     * @param condtlancamento the condtlancamento to set
     */
    public void setCondtlancamento(Date condtlancamento) {
        this.condtlancamento = condtlancamento;
    }

    /**
     * @return the connota
     */
    public Double getConnota() {
        return connota;
    }

    /**
     * @param connota the connota to set
     */
    public void setConnota(Double connota) {
        this.connota = connota;
    }

    /**
     * @return the confeedback
     */
    public String getConfeedback() {
        return confeedback;
    }

    /**
     * @param confeedback the confeedback to set
     */
    public void setConfeedback(String confeedback) {
        this.confeedback = confeedback;
    }

  
    
}
