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
import model.TurmaMatricula;

/**
 *
 * @author mi
 */
public class TurmaMatriculaOP {

    public TurmaMatriculaOP() {
    }
    
        public List<TurmaMatricula> retornaListaTurmaMatricula(){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaMatricula t");
        List<TurmaMatricula> listaTurmaMatricula = query.getResultList();

        factory.close();

        return listaTurmaMatricula;
    }
    
    public List<TurmaMatricula> retornaListaTurmaMatriculaPorId(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaMatricula t WHERE t.id = " + id.toString());
        List<TurmaMatricula> listaTurmaMatricula = query.getResultList();

        factory.close();

        return listaTurmaMatricula;
    }
    
    public String excluirTurmaMatricula(TurmaMatricula t) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(t));
            manager.getTransaction().commit();
            
            factory.close();
            return "";
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String adicionarTurmaMatricula(TurmaMatricula turmaMatricula) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(turmaMatricula);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarTurmaMatricula(TurmaMatricula turmaMatricula){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(turmaMatricula);
            manager.getTransaction().commit();

            factory.close();
            
            return "";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public List<TurmaMatricula> retornaTurmaMatriculaPorMatricula(Integer id){
        MatriculaOP matriculaOP = new MatriculaOP();
        Matricula m = matriculaOP.retornaMatriculaPorId(id);
        
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT t FROM TurmaMatricula t WHERE t.codMatricula = " + m.getId());
        
        List<TurmaMatricula> listaTurmaMatricula = query.getResultList();
        
        factory.close();
        return listaTurmaMatricula;
    }
    
    /*public List<TurmaMatricula> retornaTurmaMatriculaPor(Integer id){
        MatriculaOP matriculaOP = new MatriculaOP();
        Matricula m = matriculaOP.retornaMatriculaPorId(id);
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT t FROM Turma t WHERE t.codMatricula = " + id.toString());
        
        List<TurmaMatricula> listaTurmaMatricula = query.getResultList();
        
        factory.close();
        return listaTurmaMatricula;
    }*/
    
    /*public List<MatriculaView> retornaListaMatriculaViewPorUsuario(Integer id){
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
    }*/
    
}
