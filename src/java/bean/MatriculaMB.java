/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.CursoOP;
import data.MatriculaOP;
import data.TurmaMatriculaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Curso;
import model.Matricula;
import model.TurmaMatricula;
import view.MatriculaView;

/**
 *
 * @author mi
 */
@Named(value = "matriculaMB")
@SessionScoped
public class MatriculaMB implements Serializable {

    private List<Matricula> listaMatriculas;
    private List<Curso> listaCursos;
    private List<MatriculaView> listaMatriculaView;
    private Matricula matricula;
    private String mensagemErro;
    private MatriculaOP matriculaOP;
    private CursoOP cursoOP;
    private Integer idUsuario;
    /**
     * Creates a new instance of MatriculaMB
     */
    public MatriculaMB() {
        matriculaOP = new MatriculaOP();
        cursoOP = new CursoOP();
        idUsuario = retornaCodigoUsuario();
        listaMatriculas = retornaListaMatriculas();
        listaCursos = cursoOP.retornaListaCurso();
        listaMatriculaView = matriculaOP.retornaListaMatriculaViewPorUsuario(idUsuario);
    }

    public List<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }

    public void setListaMatriculas(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }
    
    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    
    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public MatriculaOP getMatriculaOP() {
        return matriculaOP;
    }

    public void setMatriculaOP(MatriculaOP matriculaOP) {
        this.matriculaOP = matriculaOP;
    }

    public CursoOP getCursoOP() {
        return cursoOP;
    }

    public void setCursoOP(CursoOP cursoOP) {
        this.cursoOP = cursoOP;
    }

    public List<MatriculaView> getListaMatriculaView() {
        return listaMatriculaView;
    }

    public void setListaMatriculaView(List<MatriculaView> listaMatriculaView) {
        this.listaMatriculaView = listaMatriculaView;
    }

    /* Métodos  */
    public List<Matricula> retornaListaMatriculas(){
            return matriculaOP.retornaListaMatriculaPorUsuario(idUsuario);
    }
    
    public void excluirMatricula(MatriculaView m){
        String retorno = "";
        retorno = matriculaOP.excluirMatricula(matriculaOP.retornaMatriculaPorId(m.getId()));
        listaMatriculaView = matriculaOP.retornaListaMatriculaViewPorUsuario(idUsuario);
        if(retorno.length() != 0)
            mensagemErro = retorno;
    }
    
    public String turmaMatricula(MatriculaView m){
        matricula = matriculaOP.retornaMatriculaPorId(m.getId());
        return "turmaMatricula";
    }
    
    public String criarMatricula() {
        matricula = new Matricula();
        return "criaMatricula";
    }
    
    public String adicionarMatricula() {
        String retorno = "";
        matricula.setCodUsuario(retornaCodigoUsuario());
        retorno = matriculaOP.adicionarMatricula(matricula);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaMatricula";
        } else {
            listaMatriculaView = matriculaOP.retornaListaMatriculaViewPorUsuario(idUsuario);
            return "matricula";
        }
    }
    
    public Integer retornaCodigoUsuario(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();   
        LoginMB login = (LoginMB) resolver.getValue(context.getELContext(), null, "loginMB");
        return login.getUsuario().getId();
    }
}
