/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.TurmaMatriculaOP;
import data.TurmaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Turma;
import view.TurmaAlunoView;
import view.TurmaConsulta;
import view.TurmaView;

/**
 *
 * @author mi
 */
@Named(value = "professorMB")
@SessionScoped
public class ProfessorMB implements Serializable {

    /**
     * Creates a new instance of ProfessorMB
     */
    private List<TurmaView> listaTurmaView;
    private TurmaOP turmaOP;
    private Turma turma;
    private Integer codUsuario;
    
    
    public ProfessorMB() {
        turma = new Turma();
        turmaOP = new TurmaOP();
        listaTurmaView = turmaOP.retornaListaTurmaView();
        codUsuario = retornaLoginMB().getUsuario().getId();
    }

    public List<TurmaView> getListaTurmaView() {
        return listaTurmaView;
    }

    public void setListaTurmaView(List<TurmaView> listaTurmaView) {
        this.listaTurmaView = listaTurmaView;
    }

    public TurmaOP getTurmaOP() {
        return turmaOP;
    }

    public void setTurmaOP(TurmaOP turmaOP) {
        this.turmaOP = turmaOP;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }
    
    public void ingressarComoProfessor(TurmaView t){
        turma = turmaOP.retornaTurmaPorId(t.getId());
        turma.setCodProfessor(codUsuario);
        turmaOP.alterarTurma(turma);
        listaTurmaView = turmaOP.retornaListaTurmaView();
    }

    public LoginMB retornaLoginMB(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();   
        //MatriculaMB matriculaMB = (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
        return (LoginMB) resolver.getValue(context.getELContext(), null, "loginMB");
    }
}
