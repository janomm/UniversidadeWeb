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
    private List<Cadeira> listaCadeiras;
    private List<Curso> listaCursos;
    private Turma turma;
    private String mensagemErro;
    private TurmaOP turmaOP;
    private CadeiraOP cadeiraOP;
    private CursoOP cursoOP;
    private List<TurmaView> listaTurmaView;
    
    public TurmaMB() {
        turmaOP = new TurmaOP();
        cadeiraOP = new CadeiraOP();
        cursoOP = new CursoOP();
        listaTurmas = retornaListaTurma();
        listaCadeiras = cadeiraOP.retornaListaCadeira();
        listaCursos = cursoOP.retornaListaCurso();
        listaTurmaView = carregaTurmaView();
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
    
    public List<Turma> retornaListaTurma() {
        return turmaOP.retornaListaTurma();
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
    
    public void excluirTurma(Turma c) {
        String retorno = "";
        retorno = turmaOP.excluirTurma(c);
        if(retorno.length() != 0)
            mensagemErro = retorno;
    }
    
    public String adicionarTurma() {
        String retorno = "";
        retorno = turmaOP.adicionarTurma(turma);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTurma";
        } else {
            return "turma";
        }
    }
    
    public String alterarTurma(){
        String retorno = "";
        retorno = turmaOP.alterarTurma(turma);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTurma";
        } else {
            return "turma";
        }
    }
    
    public String editarTurma(Turma t) {
        turma = t;
        return "editaTurma";
    }
    
    public String criarTurma() {
        turma = new Turma();
        return "criaTurma";
    }
    
    public List<TurmaView> carregaTurmaView(){
        List<TurmaView> lista = new ArrayList<TurmaView>();
        
        for(Turma t : retornaListaTurma()){
            TurmaView tv = new TurmaView();
            tv.setId(t.getId());
            tv.setCurso(cursoOP.retornaCursoPorId(t.getCodCurso()).getNome());
            tv.setCadeira(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getNome());
            tv.setVagas(t.getNumVagasDisp());
            lista.add(tv);
        }
        return lista;
    }
}
