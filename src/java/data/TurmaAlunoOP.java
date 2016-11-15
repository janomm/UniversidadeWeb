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
import model.Cadeira;
import model.Turma;
import model.TurmaAluno;
import view.TurmaAlunoView;

/**
 *
 * @author mi
 */
public class TurmaAlunoOP {

    public TurmaAlunoOP() {
    }

    public List<TurmaAluno> retornaListaTurmaAluno() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaAluno t");
        List<TurmaAluno> listaTurmaAluno = query.getResultList();

        factory.close();

        return listaTurmaAluno;
    }
    
    public TurmaAluno retornaTurmaAlunoPorId(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaAluno t WHERE t.id = " + id.toString());
        List<TurmaAluno> listaTurmaAluno = query.getResultList();

        TurmaAluno taRetorno = new TurmaAluno();
        for (TurmaAluno t : listaTurmaAluno) {
            taRetorno = t;
        }
        factory.close();

        return taRetorno;
    }
    
    public String excluirTurmaAluno(TurmaAluno t) {
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

    public String adicionarTurmaAluno(TurmaAluno turmaAluno) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(turmaAluno);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String alterarTurmaAluno(TurmaAluno turmaAluno) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(turmaAluno);
            manager.getTransaction().commit();

            factory.close();

            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public List<TurmaAlunoView> retornaTurmaAlunoView(){
        List<TurmaAlunoView> listaTurmaAlunoView = new ArrayList<TurmaAlunoView>();
        List<TurmaAluno> listaTurmaAluno = retornaListaTurmaAluno();
        CursoOP cursoOP = new CursoOP();
        CadeiraOP cadeiraOP = new CadeiraOP();
        TurmaOP turamOP = new TurmaOP();
        UsuarioOP usuarioOP = new UsuarioOP();
        
        for(TurmaAluno ta : listaTurmaAluno){
            TurmaAlunoView tav = new TurmaAlunoView();
            Turma t = turamOP.retornaTurmaPorId(ta.getCodTurma());
            
            tav.setId(ta.getId());
            tav.setCurso(cursoOP.retornaCursoPorId(ta.getCodCurso()).getNome());
            tav.setCadeira(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getNome());
            tav.setNota(ta.getNota());
            tav.setProfessor(usuarioOP.retornaUsuarioPorId(t.getCodProfessor()).getNome());
            tav.setAluno(usuarioOP.retornaUsuarioPorId(ta.getCodAluno()).getNome());
            tav.setTurma(ta.getCodTurma());
            listaTurmaAlunoView.add(tav);
        }
        return listaTurmaAlunoView;
    }
    
    public List<TurmaAluno> retornaListaTurmaAlunoPorTurma(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaAluno t WHERE t.codTurma = " + id.toString());
        List<TurmaAluno> listaTurmaAluno = query.getResultList();

        factory.close();

        return listaTurmaAluno;
    }
}
