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
import model.Turma;
import view.TurmaView;

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

    public String excluirTurma(Turma t) {
        String erro = validaExclusao(t);
        if (erro != "") {
            return erro;
        }
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

    public String alterarTurma(Turma curso) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(curso);
            manager.getTransaction().commit();

            factory.close();

            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public Turma retornaTurmaPorId(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT t FROM Turma t WHERE t.id = " + id.toString());

        List<Turma> listaTurma = query.getResultList();

        Turma tRetorno = new Turma();
        for (Turma t : listaTurma) {
            tRetorno = t;
        }

        factory.close();
        return tRetorno;
    }

    public List<Turma> retornaTurmaPorCadeira(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT t FROM Turma t WHERE t.codCadeira = " + id.toString());

        List<Turma> listaTurma = query.getResultList();

        factory.close();
        return listaTurma;
    }

    public List<Turma> retornaTurmaPorProfessor(Integer id) {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT t FROM Turma t WHERE t.codProfessor = " + id.toString());

        List<Turma> listaTurma = query.getResultList();

        factory.close();
        return listaTurma;
    }

    public List<Turma> retornaTurmaPorCurso(Integer id) {
        CursoOP cursoOP = new CursoOP();
        Curso curso = cursoOP.retornaCursoPorId(id);
        CadeiraOP cadeiraOP = new CadeiraOP();
        List<Cadeira> listaCadeiras = cadeiraOP.retornaListaCadeiraPorCurso(curso.getId());
        List<Turma> listaTurma = new ArrayList<Turma>();
        List<Turma> listaAux = new ArrayList<Turma>();

        for (Cadeira c : listaCadeiras) {
            listaAux = retornaTurmaPorCadeira(c.getId());
            for (Turma t : listaAux) {
                listaTurma.add(t);
            }
        }

        return listaTurma;
    }

    public List<TurmaView> retornaListaTurmaView() {
        List<Turma> listaTurmas = retornaListaTurma();
        CadeiraOP cadeiraOP = new CadeiraOP();
        CursoOP cursoOP = new CursoOP();
        List<TurmaView> listaTurmaView = new ArrayList<TurmaView>();
        UsuarioOP usuarioOP = new UsuarioOP();
        for (Turma t : listaTurmas) {
            TurmaView tv = new TurmaView();
            tv.setId(t.getId());
            tv.setNomeCadeira(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getNome());
            tv.setNomeCurso(cursoOP.retornaCursoPorId(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getCodCurso()).getNome());
            tv.setVagas(t.getNumVagasDisp());
            
            String nomeProfessor = usuarioOP.retornaUsuarioPorId(t.getCodProfessor()).getNome();
            if(nomeProfessor == null)
                tv.setProfessor("");
            else
                tv.setProfessor(nomeProfessor);
            listaTurmaView.add(tv);
        }
        return listaTurmaView;
    }

    public List<TurmaView> retornaListaTurmaViewPorCurso(Integer idCurso) {
        List<Turma> listaTurmas = retornaListaTurma();
        CadeiraOP cadeiraOP = new CadeiraOP();
        CursoOP cursoOP = new CursoOP();
        List<TurmaView> listaTurmaView = new ArrayList<TurmaView>();
        UsuarioOP usuarioOP = new UsuarioOP();
        for (Turma t : listaTurmas) {
            TurmaView tv = new TurmaView();
            Cadeira cadeira = cadeiraOP.retornaCadeiraPorId(t.getCodCadeira());
            Curso curso = cursoOP.retornaCursoPorId(cadeira.getCodCurso());
            if (curso.getId().equals(idCurso)) {
                tv.setId(t.getId());
                tv.setNomeCadeira(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getNome());
                tv.setNomeCurso(cursoOP.retornaCursoPorId(cadeiraOP.retornaCadeiraPorId(t.getCodCadeira()).getCodCurso()).getNome());
                tv.setVagas(t.getNumVagasDisp());
                tv.setProfessor(usuarioOP.retornaUsuarioPorId(t.getCodProfessor()).getNome());
                listaTurmaView.add(tv);
            }
        }
        return listaTurmaView;
    }

    public List<TurmaView> retornaListaTurmaViewPorProfessor(Integer idProfessor) {
        UsuarioOP usuarioOP = new UsuarioOP();
        String nomeProfessor = usuarioOP.retornaUsuarioPorId(idProfessor).getNome();
        List<TurmaView> listaTurmaViews = retornaListaTurmaView();
        List<TurmaView> listaTurmaViewsProfessor = new ArrayList<TurmaView>();

        for (TurmaView t : listaTurmaViews) {
            if (t.getProfessor().equalsIgnoreCase(nomeProfessor)) {
                listaTurmaViewsProfessor.add(t);
            }
        }
        return listaTurmaViewsProfessor;
    }

    public String validaExclusao(Turma turma) {
        TurmaAlunoOP turmaAlunoOP = new TurmaAlunoOP();
        if (!turmaAlunoOP.retornaListaTurmaAlunoPorTurma(turma.getId()).isEmpty()) {
            return "Turma " + turma.getId() + " tem pelo menos uma matrícula vinculada.";
        }
        return "";
    }
}
