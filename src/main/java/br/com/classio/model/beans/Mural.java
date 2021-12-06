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
public class Mural {
    private int murcodigo;
    private String muraviso;
    private Date murdtpostagem;
    private Professor murprocodigo;
    private Turma murturcodigo;

    /**
     * @return the murcodigo
     */
    public int getMurcodigo() {
        return murcodigo;
    }

    /**
     * @param murcodigo the murcodigo to set
     */
    public void setMurcodigo(int murcodigo) {
        this.murcodigo = murcodigo;
    }

    /**
     * @return the muraviso
     */
    public String getMuraviso() {
        return muraviso;
    }

    /**
     * @param muraviso the muraviso to set
     */
    public void setMuraviso(String muraviso) {
        this.muraviso = muraviso;
    }

    /**
     * @return the murprocodigo
     */
    public Professor getMurprocodigo() {
        return murprocodigo;
    }

    /**
     * @param murprocodigo the murprocodigo to set
     */
    public void setMurprocodigo(Professor murprocodigo) {
        this.murprocodigo = murprocodigo;
    }

    /**
     * @return the murturcodigo
     */
    public Turma getMurturcodigo() {
        return murturcodigo;
    }

    /**
     * @param murturcodigo the murturcodigo to set
     */
    public void setMurturcodigo(Turma murturcodigo) {
        this.murturcodigo = murturcodigo;
    }

    /**
     * @return the murdtpostagem
     */
    public Date getMurdtpostagem() {
        return murdtpostagem;
    }

    /**
     * @param murdtpostagem the murdtpostagem to set
     */
    public void setMurdtpostagem(Date murdtpostagem) {
        this.murdtpostagem = murdtpostagem;
    }


    
}
