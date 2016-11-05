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
import model.Turma;

/**
 *
 * @author mi
 */
public class TurmaOP {

    public TurmaOP() {
    }
    
    public List<Turma> retornaListaTurma() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT c FROM Turma c");
        List<Turma> listaTurma = query.getResultList();

        factory.close();

        return listaTurma;
    }
    
    public String excluirTurma(Turma c) {
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
    
    public String adicionarTurma(Turma curso) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            curso.setNumVagasDisp(curso.getNumVagas());
            manager.persist(curso);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarTurma(Turma curso){
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
}
