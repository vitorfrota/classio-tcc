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
public class Comentarios {
    private int comcodigo;
    private String commensagem;
    private Date comdtpostagem;
    private Aluno comalucodigo;
    private Avaliacao comavacodigo;

    /**
     * @return the commensagem
     */
    public String getCommensagem() {
        return commensagem;
    }

    /**
     * @param commensagem the commensagem to set
     */
    public void setCommensagem(String commensagem) {
        this.commensagem = commensagem;
    }

    /**
     * @return the comdtpostagem
     */
    public Date getComdtpostagem() {
        return comdtpostagem;
    }

    /**
     * @param comdtpostagem the comdtpostagem to set
     */
    public void setComdtpostagem(Date comdtpostagem) {
        this.comdtpostagem = comdtpostagem;
    }


    /**
     * @return the comavacodigo
     */
    public Avaliacao getComavacodigo() {
        return comavacodigo;
    }

    /**
     * @param comavacodigo the comavacodigo to set
     */
    public void setComavacodigo(Avaliacao comavacodigo) {
        this.comavacodigo = comavacodigo;
    }

    /**
     * @return the comalucodigo
     */
    public Aluno getComalucodigo() {
        return comalucodigo;
    }

    /**
     * @param comalucodigo the comalucodigo to set
     */
    public void setComalucodigo(Aluno comalucodigo) {
        this.comalucodigo = comalucodigo;
    }

    /**
     * @return the comcodigo
     */
    public int getComcodigo() {
        return comcodigo;
    }

    /**
     * @param comcodigo the comcodigo to set
     */
    public void setComcodigo(int comcodigo) {
        this.comcodigo = comcodigo;
    }
    
    
    
}
