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
import model.Usuario;

/**
 *
 * @author mi
 */
public class UsuarioOP {

    public UsuarioOP() {
    }
    
    public List<Usuario> retornaListaUsuario() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT u FROM Usuario u");
        List<Usuario> listaUsuarios = query.getResultList();

        factory.close();

        return listaUsuarios;
    }

    public String excluirUsuario(Usuario u) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(u));
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String adicionarUsuario(Usuario usuario) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(usuario);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String alterarUsuario(Usuario usuario){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(usuario);
            manager.getTransaction().commit();

            factory.close();
            
            return "";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public Usuario retornaUsuarioPorId(Integer id){
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory(
                        "UniversidadeWebPU");
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(
                "SELECT u FROM Usuario u WHERE u.id = " + id.toString());
        List<Usuario> listaUsuario = query.getResultList();
        
        Usuario uRetorno = new Usuario();
        for (Usuario u : listaUsuario){
            uRetorno = u;
        }        
        
        factory.close();
        return uRetorno;
    }
}
