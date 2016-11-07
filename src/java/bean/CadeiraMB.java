/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.CadeiraOP;
import data.CursoOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Cadeira;
import model.Curso;
import view.CadeiraView;

/**
 *
 * @author mi
 */
@Named(value = "cadeiraMB")
@SessionScoped
public class CadeiraMB implements Serializable {

    /**
     * Creates a new instance of CadeiraMB
     */
    private List<Cadeira> listaCadeiras;
    private List<Curso> listaCursos;
    private List<CadeiraView> listaCadeiraView;
    private Cadeira cadeira;
    private CadeiraOP cadeiraOP;
    private CursoOP cursoOP;
    private String mensagemErro;
    
    public CadeiraMB() {
        cursoOP = new CursoOP();
        cadeiraOP = new CadeiraOP();
        listaCadeiras = retornaListaCadeira();
        listaCursos = cursoOP.retornaListaCurso();
        listaCadeiraView = cadeiraOP.retornaCadeiraView();
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

    public Cadeira getCadeira() {
        return cadeira;
    }

    public void setCadeira(Cadeira cadeira) {
        this.cadeira = cadeira;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public List<CadeiraView> getListaCadeiraView() {
        return listaCadeiraView;
    }

    public void setListaCadeiraView(List<CadeiraView> listaCadeiraView) {
        this.listaCadeiraView = listaCadeiraView;
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
    
    public List<Cadeira> retornaListaCadeira() {
        return cadeiraOP.retornaListaCadeira();
    }
    
    public void excluirCadeira(CadeiraView c) {
        String retorno = "";
        retorno = cadeiraOP.excluirCadeira(cadeiraOP.retornaCadeiraPorId(c.getId()));
        if(retorno.length() != 0)
            mensagemErro = retorno;
        listaCadeiraView = cadeiraOP.retornaCadeiraView();
    }
    
    public String adicionarCadeira() {
        String retorno = "";
        retorno = cadeiraOP.adicionarCadeira(cadeira);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaCadeira";
        } else {
            listaCadeiraView = cadeiraOP.retornaCadeiraView();
            return "cadeira";
        }
    }
    
    public String alterarcadeira(){
        String retorno = "";
        retorno = cadeiraOP.alterarCadeira(cadeira);
        if (retorno.length() != 0) {
            mensagemErro = retorno;
            return "criaCadeira";
        } else {
            listaCadeiraView = cadeiraOP.retornaCadeiraView();
            return "cadeira";
        }
    }
    
    public String editarCadeira(CadeiraView c) {
        cadeira = cadeiraOP.retornaCadeiraPorId(c.getId());
        return "editaCadeira";
    }
    
    public String criarCadeira() {
        cadeira = new Cadeira();
        return "criaCadeira";
    }
    
}
