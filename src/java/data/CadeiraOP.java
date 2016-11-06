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
import model.Cadeira;

/**
 *
 * @author mi
 */
public class CadeiraOP {

    public CadeiraOP() {
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
    
    public String excluirCadeira(Cadeira c) {
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
    
    public String adicionarCadeira(Cadeira cadeira) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(cadeira);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarCadeira(Cadeira cadeira){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(cadeira);
            manager.getTransaction().commit();

            factory.close();
            
            return "";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public Cadeira retornaCadeiraPorId(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT c FROM Cadeira c WHERE id = " + id.toString());
        
        List<Cadeira> listaCadeira = query.getResultList();
        
        Cadeira cRetorno = new Cadeira();
        for (Cadeira c : listaCadeira){
            cRetorno = c;
        }        
        
        factory.close();
        return cRetorno;
    }
}
