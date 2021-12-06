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
public class Usuario {
    private int usucodigo;
    private String usulogin;
    private String usunome;
    private String ususenha;
    private TipoUsuario usutipcodigo;

    /**
     * @return the usulogin
     */
    public String getUsulogin() {
        return usulogin;
    }

    /**
     * @param usulogin the usulogin to set
     */
    public void setUsulogin(String usulogin) {
        this.usulogin = usulogin;
    }

    /**
     * @return the ususenha
     */
    public String getUsusenha() {
        return ususenha;
    }

    /**
     * @param ususenha the ususenha to set
     */
    public void setUsusenha(String ususenha) {
        this.ususenha = ususenha;
    }

    /**
     * @return the usutipo
     */

    public TipoUsuario getUsutipcodigo() {
        return usutipcodigo;
    }

    /**
     * @param usutipcodigo the usutipcodigo to set
     */
    public void setUsutipcodigo(TipoUsuario usutipcodigo) {
        this.usutipcodigo = usutipcodigo;
    }

    /**
     * @return the usunome
     */
    public String getUsunome() {
        return usunome;
    }

    /**
     * @param usunome the usunome to set
     */
    public void setUsunome(String usunome) {
        this.usunome = usunome;
    }

    /**
     * @return the usucodigo
     */
    public int getUsucodigo() {
        return usucodigo;
    }

    /**
     * @param usucodigo the usucodigo to set
     */
    public void setUsucodigo(int usucodigo) {
        this.usucodigo = usucodigo;
    }

   
    
}
