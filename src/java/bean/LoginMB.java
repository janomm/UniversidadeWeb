/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Usuario;

/**
 *
 * @author mi
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    /**
     * Creates a new instance of LoginMB
     */
    private List<Usuario> listaUsuarios;
    private Usuario usuario;
    private String mensagemErro;
    private String user;
    private String password;

    public LoginMB() {
        usuario = new Usuario();
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String realizarLogin() {
        usuario.setUsuario(user);
        usuario.setSenha(password);
        for (Usuario u : listaUsuarios) {
            if(u.getUsuario().equalsIgnoreCase(usuario.getUsuario()) &&
                    u.getSenha().equals(usuario.getSenha())){
                usuario = u;
                if(usuario.getAdministrador())
                    return "admin/administrador";
                if(u.getProfessor())
                    return "prof/professor";
                if(u.getAluno())
                    return "aluno/aluno";
            }
        }
        user = "";
        password = "";
        mensagemErro = "- Usuário e Senha Inválidos!";
        return "index";
    }
    
    public String realizaLogout() {
        usuario = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return ("/index?faces-redirect=true");
    }

    public String linkAdministrador(){
        if(usuario.getAdministrador())
            return "Administrador";
        return "";
    }

    public String linkProfessor(){
        if(usuario.getProfessor())
            return "Professor";
        return "";
    }

    public String linkAluno(){
        if(usuario.getAluno())
            return "Aluno";
        return "";
    }
    
    public String direcionaAdministrador(){
        if(usuario.getAdministrador())
            return "/admin/administrador";
        else
            return "#";
    }
    
    public String direcionaProfessor(){
        if(usuario.getProfessor())
            return "/prof/professor";
        else
            return "#";
    }
    
    public String direcionaAluno(){
        if(usuario.getAluno())
            return "/aluno/aluno";
        else
            return "#";
    }
    
    public String linkCadastroUsuario(){
        return "usuario";
    }
    
    public String linkCadastroCurso(){
        return "curso";
    }
    
    public String linkCadastroCadeira(){
        return "cadeira";
    }
    
    public String linkCadastroTurma(){
        return "turma";
    }
    
    public String linkAlunoMatricula(){
        return "matricula";
    }
    
    public String linkAlunoTurma(){
        return "turma";
    }
    
    public String linkAlunoHistorico(){
        return "historico";
    }
    
    private List<Usuario> retornaListaUsuario() {
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

}
