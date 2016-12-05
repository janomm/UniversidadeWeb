/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import rn.UsuarioRN;


/**
 * REST Web Service
 *
 * @author mi
 */
@Path("usuario")
public class UsuarioWS {

    @EJB
    private UsuarioRN usuarioRN;

    @Context
    private UriInfo context;

    public UsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Usuario> getUsuarios() {
        return usuarioRN.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Usuario getUsuario(@PathParam("id") Integer id) {
        return usuarioRN.buscar(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void adicionarUsuario(Usuario u, @Context final HttpServletResponse response) {
        usuarioRN.salvar(u);
        //Alterar o codigo para 201 (Created)
        
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            //Erro 500
            throw new InternalServerErrorException();
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void alterarUsuario(@PathParam("id") Integer id, Usuario usuario) {
        Usuario u =usuarioRN.buscar(id);
        u.setNome(usuario.getNome());
        u.setSexo(usuario.getSexo());
        u.setUsuario(usuario.getUsuario());
        u.setSenha(usuario.getSenha());
        u.setAdministrador(usuario.getAdministrador());
        u.setProfessor(usuario.getProfessor());
        u.setAluno(usuario.getAluno());
        u.setEmail(usuario.getEmail());
        usuarioRN.atualizar(u);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Usuario removerUsuario(@PathParam("id") Integer id) {
        Usuario u =usuarioRN.buscar(id);
        usuarioRN.remover(u);
        return u;
    }
}
