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
public class Matricula {
    private Turma matturcodigo;
    private Aluno matalucodigo;
    private Date matdtingresso;

    /**
     * @return the matturcodigo
     */
    public Turma getMatturcodigo() {
        return matturcodigo;
    }

    /**
     * @param matturcodigo the matturcodigo to set
     */
    public void setMatturcodigo(Turma matturcodigo) {
        this.matturcodigo = matturcodigo;
    }

    /**
     * @return the matalucodigo
     */
    public Aluno getMatalucodigo() {
        return matalucodigo;
    }

    /**
     * @param matalucodigo the matalucodigo to set
     */
    public void setMatalucodigo(Aluno matalucodigo) {
        this.matalucodigo = matalucodigo;
    }

    /**
     * @return the matdtingresso
     */
    public Date getMatdtingresso() {
        return matdtingresso;
    }

    /**
     * @param matdtingresso the matdtingresso to set
     */
    public void setMatdtingresso(Date matdtingresso) {
        this.matdtingresso = matdtingresso;
    }

   
}
