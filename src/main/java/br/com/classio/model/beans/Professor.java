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
public class Professor {
    
    private int procodigo;
    private String proemail;
    private Usuario prousucodigo;

    /**
     * @return the procodigo
     */
    public int getProcodigo() {
        return procodigo;
    }

    /**
     * @param procodigo the procodigo to set
     */
    public void setProcodigo(int procodigo) {
        this.procodigo = procodigo;
    }

    /**
     * @return the proemail
     */
    public String getProemail() {
        return proemail;
    }

    /**
     * @param proemail the proemail to set
     */
    public void setProemail(String proemail) {
        this.proemail = proemail;
    }

    /**
     * @return the prousucodigo
     */
    public Usuario getProusucodigo() {
        return prousucodigo;
    }

    /**
     * @param prousucodigo the prousucodigo to set
     */
    public void setProusucodigo(Usuario prousucodigo) {
        this.prousucodigo = prousucodigo;
    }


  
}
