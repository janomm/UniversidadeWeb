/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Usuario;

/**
 *
 * @author mi
 */
@Named(value = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable {

    /**
     * Creates a new instance of UsuarioMB
     */
    private List<Usuario> listaUsuarios;
    private Usuario usuario;
    private String mensagemErro;

    public UsuarioMB() {
        listaUsuarios = new ArrayList<Usuario>();
        listaUsuarios = retornaListaUsuario();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
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

    public void excluirUsuario(Usuario u) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            
            /*String strQuery = "DELETE FROM Usuario WHERE id = " + u.getId();
            
            manager.getTransaction().begin();
            manager.createQuery(strQuery).executeUpdate();
            manager.getTransaction().commit();*/
            manager.getTransaction().begin();
            manager.remove(manager.merge(u));
            manager.getTransaction().commit();
            
            factory.close();
            
        } catch (Exception ex) {
            mensagemErro = ex.getMessage();
        }
    }

    public String editarUsuario(Usuario u) {
        usuario = u;
        return "editaUsuario";
    }

    public String criarUsuario() {
        usuario = new Usuario();
        return "criaUsuario";
    }

    public String adicionarUsuario() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(usuario);
            manager.getTransaction().commit();

            factory.close();
            return "usuario";
        } catch (Exception ex) {
            mensagemErro = ex.getMessage();
        }
        return "criaUsuario";
    }
    
    public String alterarUsuario(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversidadeWebPU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(usuario);
            manager.getTransaction().commit();

            factory.close();
            
            return "usuario";
        } catch (Exception ex){
            mensagemErro = ex.getMessage();
        }
        return "criaUsuario";
    }
    

}
