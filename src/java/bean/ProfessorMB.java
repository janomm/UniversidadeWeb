/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import data.TurmaMatriculaOP;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import view.TurmaConsulta;

/**
 *
 * @author mi
 */
@Named(value = "professorMB")
@SessionScoped
public class ProfessorMB implements Serializable {

    /**
     * Creates a new instance of ProfessorMB
     */
    private List<TurmaConsulta> listaTurmaConsulta;
    private TurmaMatriculaOP turmaMatriculaOP;
    private MatriculaMB matriculaMB;
    
    public ProfessorMB() {
        matriculaMB = retornaMatriculaMB();
        turmaMatriculaOP = new TurmaMatriculaOP();
        listaTurmaConsulta = retornaListaTurmaConsulta();
    }

    public List<TurmaConsulta> getListaTurmaConsulta() {
        return listaTurmaConsulta;
    }

    public void setListaTurmaConsulta(List<TurmaConsulta> listaTurmaConsulta) {
        this.listaTurmaConsulta = listaTurmaConsulta;
    }

    public TurmaMatriculaOP getTurmaMatriculaOP() {
        return turmaMatriculaOP;
    }

    public void setTurmaMatriculaOP(TurmaMatriculaOP turmaMatriculaOP) {
        this.turmaMatriculaOP = turmaMatriculaOP;
    }

    public MatriculaMB getMatriculaMB() {
        return matriculaMB;
    }

    public void setMatriculaMB(MatriculaMB matriculaMB) {
        this.matriculaMB = matriculaMB;
    }
    
    
    
    public List<TurmaConsulta> retornaListaTurmaConsulta(){
        return turmaMatriculaOP.retornaListaTurmaConsulta(matriculaMB.retornaCodigoUsuario());
    }
    
    public MatriculaMB retornaMatriculaMB(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();   
        //MatriculaMB matriculaMB = (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
        return (MatriculaMB) resolver.getValue(context.getELContext(), null, "matriculaMB");
    }
}
