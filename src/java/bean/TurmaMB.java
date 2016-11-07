/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.CadeiraOP;
import data.CursoOP;
import data.TurmaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Cadeira;
import model.Curso;
import model.Turma;
import view.TurmaView;

/**
 *
 * @author mi
 */
@Named(value = "turmaMB")
@SessionScoped
public class TurmaMB implements Serializable {

    /**
     * Creates a new instance of TurmaMB
     */
    private List<Turma> listaTurmas;
    private List<TurmaView> listaTurmaView;
    private List<Cadeira> listaCadeiras;
    private List<Curso> listaCursos;
    private Turma turma;
    private String mensagemErro;
    private TurmaOP turmaOP;
    private CadeiraOP cadeiraOP;
    private CursoOP cursoOP;
    
    public TurmaMB() {
        turmaOP = new TurmaOP();
        cadeiraOP = new CadeiraOP();
        cursoOP = new CursoOP();
        listaTurmas = retornaListaTurma();
        listaCadeiras = cadeiraOP.retornaListaCadeira();
        listaCursos = cursoOP.retornaListaCurso();
        listaTurmaView = turmaOP.retornaListaTurmaView();
    }

    public List<Turma> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(List<Turma> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String MensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public TurmaOP getTurmaOP() {
        return turmaOP;
    }

    public void setTurmaOP(TurmaOP turmaOP) {
        this.turmaOP = turmaOP;
    }

    public CadeiraOP getCadeiraOP() {
        return cadeiraOP;
    }

    public void setCadeiraOP(CadeiraOP cadeiraOP) {
        this.cadeiraOP = cadeiraOP;
    }

    public CursoOP getCursoOP() {
        return cursoOP;
    }

    public void setCursoOP(CursoOP cursoOP) {
        this.cursoOP = cursoOP;
    }
    
    public List<Cadeira> getListaCadeiras() {
        return listaCadeiras;
    }

    public void setListaCadeiras(List<Cadeira> listaCadeiras) {
        this.listaCadeiras = listaCadeiras;
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public List<TurmaView> getListaTurmaView() {
        return listaTurmaView;
    }

    public void setListaTurmaView(List<TurmaView> listaTurmaView) {
        this.listaTurmaView = listaTurmaView;
    }
    
    public List<Turma> retornaListaTurma() {
        return turmaOP.retornaListaTurma();
    }
    
    public void excluirTurma(TurmaView t) {
        String retorno = "";
        retorno = turmaOP.excluirTurma(turmaOP.retornaTurmaPorId(t.getId()));
        if(retorno.length() != 0)
            mensagemErro = retorno;
        listaTurmaView = turmaOP.retornaListaTurmaView();
    }
    
    public String adicionarTurma() {
        String retorno = "";
        retorno = turmaOP.adicionarTurma(turma);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTurma";
        } else {
            listaTurmaView = turmaOP.retornaListaTurmaView();
            return "turma";
        }
    }
    
    public String alterarTurma(){
        String retorno = "";
        // tratar para se existtir gente matriculada nesta cadeira não deixar alterar
        turma.setNumVagasDisp(turma.getNumVagas());
        retorno = turmaOP.alterarTurma(turma);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTurma";
        } else {
            listaTurmaView = turmaOP.retornaListaTurmaView();
            return "turma";
        }
    }
    
    public String editarTurma(TurmaView t) {
        turma = turmaOP.retornaTurmaPorId(t.getId());
        return "editaTurma";
    }
    
    public String criarTurma() {
        turma = new Turma();
        return "criaTurma";
    }
}
