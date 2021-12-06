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
public class Aluno {
    
    private int alucodigo;
    private String alutelefone;
    private Usuario aluusucodigo;

    /**
     * @return the alucodigo
     */
    public int getAlucodigo() {
        return alucodigo;
    }

    /**
     * @param alucodigo the alucodigo to set
     */
    public void setAlucodigo(int alucodigo) {
        this.alucodigo = alucodigo;
    }


    /**
     * @return the aluusucodigo
     */
    public Usuario getAluusucodigo() {
        return aluusucodigo;
    }

    /**
     * @param aluusucodigo the aluusucodigo to set
     */
    public void setAluusucodigo(Usuario aluusucodigo) {
        this.aluusucodigo = aluusucodigo;
    }

    /**
     * @return the alutelefone
     */
    public String getAlutelefone() {
        return alutelefone;
    }

    /**
     * @param alutelefone the alutelefone to set
     */
    public void setAlutelefone(String alutelefone) {
        this.alutelefone = alutelefone;
    }


}
