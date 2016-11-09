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
import java.util.ArrayList;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.TurmaMatricula;
import view.TurmaConsulta;
import view.TurmaMatriculaView;
import view.TurmaView;

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
    private List<TurmaMatriculaView> listaTurmaMatriculaView;
    private List<TurmaView> listaTurmaView;
    private List<TurmaConsulta> listaTurmaConsulta;
    private TurmaOP turmaOP;
    private String mensagemErro;
    private TurmaMatriculaOP turmaMatriculaOP;
    private MatriculaMB matriculaMB;
    
    
    public TurmaMatriculaMB() {
        matriculaMB = retornaMatriculaMB();
        turmaOP = new TurmaOP();
        turmaMatriculaOP = new TurmaMatriculaOP();
        listaTurmaMatricula = retornaListaTurmaMatricula();
        listaTurmaMatriculaView = retornaListaTurmaMatriculaView();
        listaTurmaView = retornaListaTurmaView();
        listaTurmaConsulta = retornaListaTurmaConsulta();
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

    public List<TurmaMatriculaView> getListaTurmaMatriculaView() {
        return listaTurmaMatriculaView;
    }

    public void setListaTurmaMatriculaView(List<TurmaMatriculaView> listaTurmaMatriculaView) {
        this.listaTurmaMatriculaView = listaTurmaMatriculaView;
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

    public List<TurmaConsulta> getListaTurmaConsulta() {
        return listaTurmaConsulta;
    }

    public void setListaTurmaConsulta(List<TurmaConsulta> listaTurmaConsulta) {
        this.listaTurmaConsulta = listaTurmaConsulta;
    }
    
    /* Métodos */
    public MatriculaMB retornaMatriculaMB(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();   
        MatriculaMB matriculaMB = (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
        return matriculaMB;
    }
    
    public List<TurmaMatricula> retornaListaTurmaMatricula(){
        return turmaMatriculaOP.retornaTurmaMatriculaPorMatricula(matriculaMB.getMatricula().getId());
    }
    
    public List<TurmaMatriculaView> retornaListaTurmaMatriculaView(){
        Integer idMatricula = matriculaMB.getMatricula().getId();
        return turmaMatriculaOP.retornaTurmaMatriculaView(idMatricula);
    }
    
    public void excluirTurmaMatricula(TurmaMatriculaView t){
        String retorno = "";
        TurmaMatricula tm = turmaMatriculaOP.retornaTurmaMatriculaPorId(t.getId());
        retorno = turmaMatriculaOP.excluirTurmaMatricula(tm);
        if(retorno.length() != 0)
            mensagemErro = retorno;
        listaTurmaMatriculaView = retornaListaTurmaMatriculaView();
    }
    
    public String adicionarTurmaMatricula(TurmaView t) {
        TurmaMatricula tm = new TurmaMatricula();
        tm.setCodTurma(t.getId());
        tm.setCodMatricula(matriculaMB.getMatricula().getId());
        tm.setNota(0);
        tm.setCodProfessor(0);
        String retorno = "";
        retorno = turmaMatriculaOP.adicionarTurmaMatricula(tm);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTurmaMatricula";
        } else {
            listaTurmaMatriculaView = retornaListaTurmaMatriculaView();
            return "turmaMatricula";
        }
    }
    
    public String criarTurmaMatricula() {
        turmaMatricula = new TurmaMatricula();
        return "criaTurmaMatricula";
    }
    
    public List<TurmaView> retornaListaTurmaView(){
        listaTurmaConsulta = retornaListaTurmaConsulta();
        return turmaOP.retornaListaTurmaViewPorCurso(matriculaMB.getMatricula().getCodCurso());
    }
    
    public List<TurmaConsulta> retornaListaTurmaConsulta(){
        
        return turmaMatriculaOP.retornaListaTurmaConsulta(matriculaMB.retornaCodigoUsuario());
    }
}