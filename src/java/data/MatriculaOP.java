/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Matricula;
import view.MatriculaView;

/**
 *
 * @author mi
 */
public class MatriculaOP {

    public MatriculaOP() {
    }
    
    public List<Matricula> retornaListaMatricula(){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT m FROM Matricula m");
        List<Matricula> listaMatricula = query.getResultList();

        factory.close();

        return listaMatricula;
    }
    
    public List<Matricula> retornaListaMatriculaPorUsuario(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT m FROM Matricula m WHERE m.codUsuario = " + id.toString());
        List<Matricula> listaMatricula = query.getResultList();

        factory.close();

        return listaMatricula;
    }
    
    public String excluirMatricula(Matricula m) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(m));
            manager.getTransaction().commit();
            
            factory.close();
            return "";
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String adicionarMatricula(Matricula matricula) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(matricula);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarMatricula(Matricula matricula){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(matricula);
            manager.getTransaction().commit();

            factory.close();
            
            return "";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public Matricula retornaMatriculaPorId(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT m FROM Matricula m WHERE m.id = " + id.toString());
        
        List<Matricula> listaMatricula = query.getResultList();
        
        Matricula mRetorno = new Matricula();
        for (Matricula m : listaMatricula){
            mRetorno = m;
        }        
        
        factory.close();
        return mRetorno;
    }
    
    public List<MatriculaView> retornaListaMatriculaViewPorUsuario(Integer id){
        List<Matricula> listaMatricula = retornaListaMatriculaPorUsuario(id);
        CursoOP cursoOP = new CursoOP();
        List<MatriculaView> listaMatriculaView = new ArrayList<MatriculaView>();
        for(Matricula m : listaMatricula){
            MatriculaView mv = new MatriculaView();
            mv.setId(m.getId());
            mv.setNomeCurso(cursoOP.retornaCursoPorId(m.getCodCurso()).getNome());
            mv.setCodUsuario(m.getCodUsuario());
            listaMatriculaView.add(mv);
        }
        return listaMatriculaView;
    }
    
    public List<Matricula> retornaListaMatriculaPorCurso(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT m FROM Matricula m WHERE m.codCurso = " + id.toString());
        List<Matricula> listaMatricula = query.getResultList();

        factory.close();

        return listaMatricula;
    }
}
