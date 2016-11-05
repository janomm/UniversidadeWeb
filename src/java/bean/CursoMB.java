/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.CursoOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Curso;

/**
 *
 * @author mi
 */
@Named(value = "cursoMB")
@SessionScoped
public class CursoMB implements Serializable {

    /**
     * Creates a new instance of CursoMB
     */
    private List<Curso> listaCursos;
    private Curso curso;
    private String mensagemErro;
    private CursoOP cursoOP;
    
    public CursoMB() {
        cursoOP = new CursoOP();
        listaCursos = new ArrayList<Curso>();
        listaCursos = retornaListaCurso();
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
    
    public List<Curso> retornaListaCurso() {
        return cursoOP.retornaListaCurso();
    }
    
    public void excluirCurso(Curso c) {
        String retorno = "";
        retorno = cursoOP.excluirCurso(c);
        if(retorno.length() != 0)
            mensagemErro = retorno;
    }
    
    public String adicionarCurso() {
        String retorno = "";
        retorno = cursoOP.adicionarCurso(curso);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaCurso";
        } else {
            return "curso";
        }
    }
    
    public String alterarCurso(){
                String retorno = "";
        retorno = cursoOP.alterarCurso(curso);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaCurso";
        } else {
            return "curso";
        }
    }
    
    public String editarCurso(Curso c) {
        curso = c;
        return "editaCurso";
    }
    
    public String criarCurso() {
        curso = new Curso();
        return "criaCurso";
    }
}
