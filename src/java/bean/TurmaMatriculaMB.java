/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.TurmaMatriculaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.TurmaMatricula;

/**
 *
 * @author mi
 */
@Named(value = "turmaMatriculaMB")
@SessionScoped
public class TurmaMatriculaMB implements Serializable {

    /**
     * Creates a new instance of TurmaMatriculaMB
     */
    private TurmaMatricula turmaMatricula;
    private List<TurmaMatricula> listaTurmaMatricula;
    private String mensagemErro;
    private TurmaMatriculaOP turmaMatriculaOP;
    private MatriculaMB matriculaMB;
    
    public TurmaMatriculaMB() {
        matriculaMB = retornaMatriculaMB();
        turmaMatriculaOP = new TurmaMatriculaOP();
        listaTurmaMatricula = turmaMatriculaOP.retornaTurmaMatriculaPorMatricula(matriculaMB.getMatricula().getId());
    }

    public TurmaMatricula getTurmaMatricula() {
        return turmaMatricula;
    }

    public void setTurmaMatricula(TurmaMatricula turmaMatricula) {
        this.turmaMatricula = turmaMatricula;
    }

    public List<TurmaMatricula> getListaTurmaMatricula() {
        return listaTurmaMatricula;
    }

    public void setListaTurmaMatricula(List<TurmaMatricula> listaTurmaMatricula) {
        this.listaTurmaMatricula = listaTurmaMatricula;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public TurmaMatriculaOP getTurmaMatriculaOP() {
        return turmaMatriculaOP;
    }

    public void setTurmaMatriculaOP(TurmaMatriculaOP turmaMatriculaOP) {
        this.turmaMatriculaOP = turmaMatriculaOP;
    }

    public MatriculaMB getMatriculaMB() {
        return matriculaMB;
    }

    public void setMatriculaMB(MatriculaMB matriculaMB) {
        this.matriculaMB = matriculaMB;
    }
    
    public MatriculaMB retornaMatriculaMB(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();   
        MatriculaMB matriculaMB = (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
        return matriculaMB;
    }
}
