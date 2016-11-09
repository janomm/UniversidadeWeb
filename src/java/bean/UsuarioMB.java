/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.UsuarioOP;
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
    private UsuarioOP usuarioOP;
    private String mensagemErro;

    public UsuarioMB() {
        usuarioOP = new UsuarioOP();
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
        return usuarioOP.retornaListaUsuario();
    }

    public void excluirUsuario(Usuario u) {
        String retorno = "";
        retorno = usuarioOP.excluirUsuario(u);
        if (retorno.length() != 0) {
            mensagemErro = retorno;
        }
    }

    public String adicionarUsuario() {
        String retorno = "";
        retorno = usuarioOP.adicionarUsuario(usuario);
        if (retorno.length() != 0) {
            mensagemErro = retorno;
            return "criaUsuario";
        } else {
            return "usuario";
        }
    }
    
    public String alterarUsuario(){
        String retorno = "";
        retorno = usuarioOP.alterarUsuario(usuario);
        if (retorno.length() != 0) {
            mensagemErro = retorno;
            return "criaUsuario";
        } else {
            return "usuario";
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

}
