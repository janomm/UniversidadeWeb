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
    
    public void salvar(Curso c)
    {
        //validar parâmetros
        if(c.getId()==null)
            super.adicionar(c);
        else
            super.atualizar(c);
    }   
}