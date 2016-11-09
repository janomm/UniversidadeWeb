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
import model.Curso;
import model.Matricula;
import model.Turma;
import model.TurmaMatricula;
import model.Usuario;
import view.TurmaConsulta;
import view.TurmaMatriculaView;

/**
 *
 * @author mi
 */
public class TurmaMatriculaOP {

    public TurmaMatriculaOP() {
    }

    public List<TurmaMatricula> retornaListaTurmaMatricula() {
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

    public TurmaMatricula retornaTurmaMatriculaPorId(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT t FROM TurmaMatricula t WHERE t.id = " + id.toString());
        List<TurmaMatricula> listaTurmaMatricula = query.getResultList();

        TurmaMatricula tmRetorno = new TurmaMatricula();
        for (TurmaMatricula t : listaTurmaMatricula) {
            tmRetorno = t;
        }
        factory.close();

        return tmRetorno;
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

    public String alterarTurmaMatricula(TurmaMatricula turmaMatricula) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(turmaMatricula);
            manager.getTransaction().commit();

            factory.close();

            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public List<TurmaMatricula> retornaTurmaMatriculaPorMatricula(Integer id) {
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

    public List<TurmaMatriculaView> retornaTurmaMatriculaView(Integer idMatricula) {
        List<TurmaMatriculaView> listaTurmaMatriculaView = new ArrayList<TurmaMatriculaView>();
        TurmaOP turmaOP = new TurmaOP();
        CadeiraOP cadeiraOP = new CadeiraOP();

        List<TurmaMatricula> listaTurmaMatricula = retornaTurmaMatriculaPorMatricula(idMatricula);

        for (TurmaMatricula t : listaTurmaMatricula) {
            Turma turma = turmaOP.retornaTurmaPorId(t.getCodTurma());
            Cadeira cadeira = cadeiraOP.retornaCadeiraPorId(turma.getCodCadeira());
            TurmaMatriculaView tmv = new TurmaMatriculaView();
            tmv.setId(t.getId());
            tmv.setCodTurma(turma.getId());
            tmv.setNomeCadeira(cadeira.getNome());
            listaTurmaMatriculaView.add(tmv);
        }
        return listaTurmaMatriculaView;
    }

    public List<TurmaConsulta> retornaListaTurmaConsulta(Integer codUsuario) {
        List<TurmaConsulta> listaTurmaConsulta = new ArrayList<TurmaConsulta>();

        MatriculaOP matriculaOP = new MatriculaOP();
        List<Matricula> listaMatricula = matriculaOP.retornaListaMatriculaPorUsuario(codUsuario);

        TurmaOP turmaOP = new TurmaOP();
        CadeiraOP cadeiraOP = new CadeiraOP();
        CursoOP cursoOP = new CursoOP();
        
        for (Matricula m : listaMatricula) {
            List<TurmaMatricula> listaTurmaMatricula = retornaTurmaMatriculaPorMatricula(m.getId());
            for (TurmaMatricula turmaMatricula : listaTurmaMatricula) {
                Turma turma = turmaOP.retornaTurmaPorId(turmaMatricula.getCodTurma());
                Cadeira cadeira = cadeiraOP.retornaCadeiraPorId(turma.getCodCadeira());
                Curso curso = cursoOP.retornaCursoPorId(cadeira.getCodCurso());
                
                TurmaConsulta tc = new TurmaConsulta();
                tc.setCadeira(cadeira.getNome());
                tc.setCurso(curso.getNome());
                tc.setNota(turmaMatricula.getNota());
                listaTurmaConsulta.add(tc);
            }

        }
        return listaTurmaConsulta;
    }

}
