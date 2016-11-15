/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Curso;

/**
 *
 * @author mi
 */
public class CursoOP {

    public CursoOP() {
    }
    
    public List<Curso> retornaListaCurso() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT c FROM Curso c");
        List<Curso> listaCurso = query.getResultList();

        factory.close();

        return listaCurso;
    }
    
    public String excluirCurso(Curso c) {
        String erro = validaExclusao(c);
        if(erro != "")
            return erro;
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(c));
            manager.getTransaction().commit();
            
            factory.close();
            return "";
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String adicionarCurso(Curso curso) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(curso);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarCurso(Curso curso){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(curso);
            manager.getTransaction().commit();

            factory.close();
            
            return "";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public Curso retornaCursoPorId(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT c FROM Curso c WHERE c.id = " + id.toString());
        
        List<Curso> listaCurso = query.getResultList();
        
        Curso cRetorno = new Curso();
        for (Curso c : listaCurso){
            cRetorno = c;
        }        
        
        factory.close();
        return cRetorno;
    }
    
    public String validaExclusao(Curso curso){
        MatriculaOP matriculaOP = new MatriculaOP();
        
        if(!matriculaOP.retornaListaMatriculaPorCurso(curso.getId()).isEmpty())
            return "Curso " + curso.getNome() + " possui pelo menos uma turma vinculada.";
        return "";
    }
}
