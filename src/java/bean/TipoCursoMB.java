/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.TipoCursoOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.TipoCurso;

/**
 *
 * @author mi
 */
@Named(value = "tipoCursoMB")
@SessionScoped
public class TipoCursoMB implements Serializable {

    /**
     * Creates a new instance of TipoCursoMB
     */
    private List<TipoCurso> listaTipoCurso;
    private TipoCurso tipoCurso;
    private String mensagemErro;
    private TipoCursoOP tipoCursoOP;
    
    public TipoCursoMB() {
        listaTipoCurso = new ArrayList<TipoCurso>();
        tipoCursoOP = new TipoCursoOP();
        listaTipoCurso = retornaListaTipoCurso();
    }

    public List<TipoCurso> getListaTipoCurso() {
        return listaTipoCurso;
    }

    public void setListaTipoCurso(List<TipoCurso> listaTipoCurso) {
        this.listaTipoCurso = listaTipoCurso;
    }

    public TipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
    
    public List<TipoCurso> retornaListaTipoCurso(){
        return tipoCursoOP.retornaListaTipoCurso();
    }
    
    public void excluirTipoCurso(TipoCurso t) {
        String retorno = "";
        retorno = tipoCursoOP.excluirTipoCurso(t);
        if(retorno.length() != 0)
            mensagemErro = retorno;
    }
    
    public String adicionarTipoCurso() {
        String retorno = "";
        retorno = tipoCursoOP.adicionarTipoCurso(tipoCurso);
        if(retorno.length() != 0){
            mensagemErro = retorno;
            return "criaTipoCurso";
        } else {
            return "tipoCurso";
        }
    }
    
    public String criarTipoCurso() {
        tipoCurso = new TipoCurso();
        return "criaTipoCurso";
    }
}
