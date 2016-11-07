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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Cadeira;
import model.Curso;

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
    private Cadeira cadeira;
    private CursoOP cursoOP;
    private String mensagemErro;
    
    public CadeiraMB() {
        cursoOP = new CursoOP();
        listaCadeiras = retornaListaCadeira();
        listaCursos = cursoOP.retornaListaCurso();
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
    
    public List<Cadeira> retornaListaCadeira() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT c FROM Cadeira c");
        List<Cadeira> listaCadeira = query.getResultList();

        factory.close();

        return listaCadeira;
    }
    
    public void excluirCadeira(Cadeira c) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(c));
            manager.getTransaction().commit();
            
            factory.close();
            
        } catch (Exception ex) {
            mensagemErro = ex.getMessage();
        }
    }
    
    public String adicionarCadeira() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(cadeira);
            manager.getTransaction().commit();

            factory.close();
            return "cadeira";
        } catch (Exception ex) {
            mensagemErro = ex.getMessage();
        }
        return "criaCadeira";
    }
    
    public String alterarcadeira(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(cadeira);
            manager.getTransaction().commit();

            factory.close();
            
            return "cadeira";
        } catch (Exception ex){
            mensagemErro = ex.getMessage();
        }
        return "criaCadeira";
    }
    
    public String editarCadeira(Cadeira c) {
        cadeira = c;
        return "editaCadeira";
    }
    
    public String criarCadeira() {
        cadeira = new Cadeira();
        return "criaCadeira";
    }
    
}
