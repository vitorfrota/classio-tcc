/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.beans;

import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author vitor_9g3eyo1
 */
public class Avaliacao {
    private int avacodigo;
    private String avadescricao;
    private String avaobservacao;
    private Date avadtabertura;
    private Date avadtentrega;
    private double avanota;
    private InputStream avaanexo;
    private Turma avaturcodigo;
    private TipoAvaliacao avatipcodigo;

    /**
     * @return the avacodigo
     */
    public int getAvacodigo() {
        return avacodigo;
    }

    /**
     * @param avacodigo the avacodigo to set
     */
    public void setAvacodigo(int avacodigo) {
        this.avacodigo = avacodigo;
    }

    /**
     * @return the avadescricao
     */
    public String getAvadescricao() {
        return avadescricao;
    }

    /**
     * @param avadescricao the avadescricao to set
     */
    public void setAvadescricao(String avadescricao) {
        this.avadescricao = avadescricao;
    }

    /**
     * @return the avaobservacao
     */
    public String getAvaobservacao() {
        return avaobservacao;
    }

    /**
     * @param avaobservacao the avaobservacao to set
     */
    public void setAvaobservacao(String avaobservacao) {
        this.avaobservacao = avaobservacao;
    }

    /**
     * @return the avadtentrega
     */
    public Date getAvadtentrega() {
        return avadtentrega;
    }

    /**
     * @param avadtentrega the avadtentrega to set
     */
    public void setAvadtentrega(Date avadtentrega) {
        this.avadtentrega = avadtentrega;
    }

    /**
     * @return the avanota
     */
    public double getAvanota() {
        return avanota;
    }

    /**
     * @param avanota the avanota to set
     */
    public void setAvanota(double avanota) {
        this.avanota = avanota;
    }

    public Turma getAvaturcodigo() {
        return avaturcodigo;
    }

    /**
     * @param avaturcodigo the avaturcodigo to set
     */
    public void setAvaturcodigo(Turma avaturcodigo) {
        this.avaturcodigo = avaturcodigo;
    }

    /**
     * @return the avatipcodigo
     */
    public TipoAvaliacao getAvatipcodigo() {
        return avatipcodigo;
    }

    /**
     * @param avatipcodigo the avatipcodigo to set
     */
    public void setAvatipcodigo(TipoAvaliacao avatipcodigo) {
        this.avatipcodigo = avatipcodigo;
    }

    /**
     * @return the avadtabertura
     */
    public Date getAvadtabertura() {
        return avadtabertura;
    }

    /**
     * @param avadtabertura the avadtabertura to set
     */
    public void setAvadtabertura(Date avadtabertura) {
        this.avadtabertura = avadtabertura;
    }

    /**
     * @return the avaanexo
     */
    public InputStream getAvaanexo() {
        return avaanexo;
    }

    /**
     * @param avaanexo the avaanexo to set
     */
    public void setAvaanexo(InputStream avaanexo) {
        this.avaanexo = avaanexo;
    }

    
    
}
