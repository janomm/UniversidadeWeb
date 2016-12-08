/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import model.Curso;
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
import rn.CursoRN;


/**
 * REST Web Service
 *
 * @author mi
 */
@Path("curso")
public class CursoWS {

    @EJB
    private CursoRN cursoRN;

    @Context
    private UriInfo context;

    public CursoWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Curso> getCursos() {
        return cursoRN.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Curso getCurso(@PathParam("id") Integer id) {
        return cursoRN.buscar(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void adicionarCurso(Curso c, @Context final HttpServletResponse response) {
        cursoRN.salvar(c);
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
    public void alterarCurso(@PathParam("id") Integer id, Curso curso) {
        Curso c =cursoRN.buscar(id);
        c.setCargaHoraria(curso.getCargaHoraria());
        c.setNome(curso.getNome());
        c.setSemestres(curso.getSemestres());
        c.setTipoCurso(curso.getTipoCurso());
        cursoRN.atualizar(c);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Curso removerCurso(@PathParam("id") Integer id) {
        Curso c =cursoRN.buscar(id);
        cursoRN.remover(c);
        return c;
    }
}
