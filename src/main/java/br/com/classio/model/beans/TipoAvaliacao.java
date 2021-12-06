/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.beans;

/**
 *
 * @author vitor_9g3eyo1
 */
public class TipoAvaliacao {
    private int tipcodigo;
    private String tipdescricao;

    /**
     * @return the tipcodigo
     */
    public int getTipcodigo() {
        return tipcodigo;
    }

    /**
     * @param tipcodigo the tipcodigo to set
     */
    public void setTipcodigo(int tipcodigo) {
        this.tipcodigo = tipcodigo;
    }

    /**
     * @return the tipdescricao
     */
    public String getTipdescricao() {
        return tipdescricao;
    }

    /**
     * @param tipdescricao the tipdescricao to set
     */
    public void setTipdescricao(String tipdescricao) {
        this.tipdescricao = tipdescricao;
    }
    
    
}
