/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import model.Curso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CursoRN extends AbstractRN<Curso>{
    @PersistenceContext(unitName="UniversidadeWebPU")
    private EntityManager manager;
    
    public CursoRN() {
        super(Curso.class);
    }

    @Override
    protected EntityManager getManager() {
        return manager;
    }
    
    public void salvar(Curso u)
    {
        //validar parâmetros
        if(u.getId()==null)
            super.adicionar(u);
        else
            super.atualizar(u);
    }   
}