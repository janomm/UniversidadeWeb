/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.TurmaAlunoOP;
import data.TurmaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Turma;
import model.TurmaAluno;
import view.TurmaAlunoView;
import view.TurmaView;

/**
 *
 * @author mi
 */
@Named(value = "turmaAlunoMB")
@SessionScoped
public class TurmaAlunoMB implements Serializable {

    /**
     * Creates a new instance of TurmaAlunoMB
     */
    private List<TurmaView> listaTurmaView;
    private TurmaOP turmaOP;
    private MatriculaMB matriculaMB;
    private TurmaAluno turmaAluno;
    private Integer codAluno;
    private TurmaAlunoOP turmaAlunoOP;
    private String mensagemErro;
    private List<TurmaAlunoView> listaTurmaAlunoView;

    public TurmaAlunoMB() {
        codAluno = retornaLoginMB().getUsuario().getId();
        turmaAluno = new TurmaAluno();
        turmaAlunoOP = new TurmaAlunoOP();
        matriculaMB = retornaMatriculaMB();
        turmaOP = new TurmaOP();
        listaTurmaView = retornaListaTurmaView();
        listaTurmaAlunoView = retornaTurmaAlunoView();
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

    public MatriculaMB getMatriculaMB() {
        return matriculaMB;
    }

    public void setMatriculaMB(MatriculaMB matriculaMB) {
        this.matriculaMB = matriculaMB;
    }

    public TurmaAluno getTurmaAluno() {
        return turmaAluno;
    }

    public void setTurmaAluno(TurmaAluno turmaAluno) {
        this.turmaAluno = turmaAluno;
    }

    public Integer getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(Integer codAluno) {
        this.codAluno = codAluno;
    }

    public TurmaAlunoOP getTurmaAlunoOP() {
        return turmaAlunoOP;
    }

    public void setTurmaAlunoOP(TurmaAlunoOP turmaAlunoOP) {
        this.turmaAlunoOP = turmaAlunoOP;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public List<TurmaAlunoView> getListaTurmaAlunoView() {
        return listaTurmaAlunoView;
    }

    public void setListaTurmaAlunoView(List<TurmaAlunoView> listaTurmaAlunoView) {
        this.listaTurmaAlunoView = listaTurmaAlunoView;
    }

    public String adicionarTurmaAluno(TurmaView t) {
        String retorno = "";

        TurmaOP turmaOP = new TurmaOP();
        Turma turma = turmaOP.retornaTurmaPorId(t.getId());
        if (turma.getNumVagasDisp() > 1) {
            turma.setNumVagasDisp(turma.getNumVagasDisp() - 1);
            turmaAluno.setCodCurso(matriculaMB.getMatricula().getCodCurso());
            turmaAluno.setCodTurma(t.getId());
            turmaAluno.setCodAluno(codAluno);
            turmaAluno.setNota(0.0);
            retorno = turmaAlunoOP.adicionarTurmaAluno(turmaAluno);
            listaTurmaAlunoView = retornaTurmaAlunoView();
        } else {
            mensagemErro = "Não há mais vagas nesta turma.";
            return "turmaAluno";
        }
        
        if (retorno.length() != 0) {
            mensagemErro = retorno;
            return "turmaAluno";
        } else {
            turmaOP.alterarTurma(turma);
            listaTurmaAlunoView = turmaAlunoOP.retornaTurmaAlunoView();
            return "matricula";
        }
    }

    public void removerTurmaAluno(TurmaView t) {
        String retorno = "";
        retorno = turmaAlunoOP.excluirTurmaAluno(turmaAlunoOP.retornaTurmaAlunoPorId(t.getId()));
        listaTurmaAlunoView = turmaAlunoOP.retornaTurmaAlunoView();
        if (retorno.length() != 0) {
            mensagemErro = retorno;
        }
    }

    public List<TurmaView> retornaListaTurmaView() {
        matriculaMB = retornaMatriculaMB();
        Integer codCurso = matriculaMB.getMatricula().getCodCurso();
        return turmaOP.retornaListaTurmaViewPorCurso(codCurso);
    }

    public MatriculaMB retornaMatriculaMB() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
    }

    public LoginMB retornaLoginMB() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        return (LoginMB) resolver.getValue(context.getELContext(), null, "loginMB");
    }
    
    public List<TurmaAlunoView> retornaTurmaAlunoView(){
        return turmaAlunoOP.retornaTurmaAlunoViewPorAluno(codAluno);
    }
            
}
