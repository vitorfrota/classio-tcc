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
public class Turma {
    private int turcodigo;
    private String turdescricao;
    private Double turmedia;
    private int turdividenota;
    private Date turdtabertura;
    private StatusTurma turstacodigo;
    private Professor turprocodigo;

    /**
     * @return the turcodigo
     */
    public int getTurcodigo() {
        return turcodigo;
    }

    /**
     * @param turcodigo the turcodigo to set
     */
    public void setTurcodigo(int turcodigo) {
        this.turcodigo = turcodigo;
    }

    /**
     * @return the turdescricao
     */
    public String getTurdescricao() {
        return turdescricao;
    }

    /**
     * @param turdescricao the turdescricao to set
     */
    public void setTurdescricao(String turdescricao) {
        this.turdescricao = turdescricao;
    }

    /**
     * @return the turdtabertura
     */
    public Date getTurdtabertura() {
        return turdtabertura;
    }

    /**
     * @param turdtabertura the turdtabertura to set
     */
    public void setTurdtabertura(Date turdtabertura) {
        this.turdtabertura = turdtabertura;
    }



    /**
     * @return the turprocodigo
     */
    public Professor getTurprocodigo() {
        return turprocodigo;
    }

    /**
     * @param turprocodigo the turprocodigo to set
     */
    public void setTurprocodigo(Professor turprocodigo) {
        this.turprocodigo = turprocodigo;
    }

    /**
     * @return the turmedia
     */
    public Double getTurmedia() {
        return turmedia;
    }

    /**
     * @param turmedia the turmedia to set
     */
    public void setTurmedia(Double turmedia) {
        this.turmedia = turmedia;
    }

    /**
     * @return the turdividenota
     */
    public int getTurdividenota() {
        return turdividenota;
    }

    /**
     * @param turdividenota the turdividenota to set
     */
    public void setTurdividenota(int turdividenota) {
        this.turdividenota = turdividenota;
    }

    /**
     * @return the turstacodigo
     */
    public StatusTurma getTurstacodigo() {
        return turstacodigo;
    }

    /**
     * @param turstacodigo the turstacodigo to set
     */
    public void setTurstacodigo(StatusTurma turstacodigo) {
        this.turstacodigo = turstacodigo;
    }


    
    
}
