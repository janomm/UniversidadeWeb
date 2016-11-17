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
import java.util.ArrayList;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Turma;
import model.TurmaAluno;
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
    private List<TurmaView> listaMinhasTurmas;
    private List<TurmaAlunoView> listaTurmaAluno;
    private TurmaOP turmaOP;
    private TurmaAlunoOP turmaAlunoOP;
    private Turma turma;
    private Integer codUsuario;
    private String mensagemErro;

    public ProfessorMB() {
        codUsuario = retornaLoginMB().getUsuario().getId();
        turma = new Turma();
        turmaOP = new TurmaOP();
        turmaAlunoOP = new TurmaAlunoOP();
        listaTurmaView = turmaOP.retornaListaTurmaView();
        listaMinhasTurmas = retornaTurmaViewProfessor();
        listaTurmaAluno = new ArrayList<TurmaAlunoView>();
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

    public List<TurmaView> getListaMinhasTurmas() {
        return listaMinhasTurmas;
    }

    public void setListaMinhasTurmas(List<TurmaView> listaMinhasTurmas) {
        this.listaMinhasTurmas = listaMinhasTurmas;
    }

    public List<TurmaAlunoView> getListaTurmaAluno() {
        return listaTurmaAluno;
    }

    public void setListaTurmaAluno(List<TurmaAlunoView> listaTurmaAluno) {
        this.listaTurmaAluno = listaTurmaAluno;
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

    public void ingressarComoProfessor(TurmaView t) {
        turma = turmaOP.retornaTurmaPorId(t.getId());
        if (turma.getCodProfessor() == 0) {
            turma.setCodProfessor(codUsuario);
            turmaOP.alterarTurma(turma);
            listaTurmaView = turmaOP.retornaListaTurmaView();
            listaMinhasTurmas = retornaTurmaViewProfessor();
            mensagemErro = "";
        } else {
            mensagemErro = "Turma já tem professor.";
        }
    }

    public void deixarTurma(TurmaView t) {
        turma = turmaOP.retornaTurmaPorId(t.getId());
        if (turma.getCodProfessor() == codUsuario) {
            turma.setCodProfessor(0);
            turmaOP.alterarTurma(turma);
            listaTurmaView = turmaOP.retornaListaTurmaView();
            listaMinhasTurmas = retornaTurmaViewProfessor();
            mensagemErro = "";
        } else {
            mensagemErro = "Voce não é o professor desta turma.";
        }
    }

    public String entrarTurma(TurmaView t) {
        //turma = turmaOP.retornaTurmaPorId(t.getId());
        listaTurmaAluno = retoraTurmaAlunoPorTurma(t.getId());
        return "listarAlunos";
    }

    public LoginMB retornaLoginMB() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        //MatriculaMB matriculaMB = (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
        return (LoginMB) resolver.getValue(context.getELContext(), null, "loginMB");
    }

    public List<TurmaView> retornaTurmaViewProfessor() {
        return turmaOP.retornaListaTurmaViewPorProfessor(codUsuario);
    }

    public List<TurmaAlunoView> retoraTurmaAlunoPorTurma(Integer idTurma) {
        List<TurmaAlunoView> listaTurmaAlunoTurma = new ArrayList<TurmaAlunoView>();
        for (TurmaAlunoView t : turmaAlunoOP.retornaTurmaAlunoView()) {
            if (t.getTurma().equals(idTurma)) {
                listaTurmaAlunoTurma.add(t);
            }
        }
        return listaTurmaAlunoTurma;
    }

    public void salvarNotas() {
        TurmaAluno turmaAluno;
        for (TurmaAlunoView t : listaTurmaAluno) {
            turmaAluno = turmaAlunoOP.retornaTurmaAlunoPorId(t.getId());
            turmaAluno.setNota(t.getNota());
            turmaAlunoOP.alterarTurmaAluno(turmaAluno);
        }
    }
}
