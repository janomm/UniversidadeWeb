/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsuarioRN extends AbstractRN<Usuario>{
    @PersistenceContext(unitName="UniversidadeWebPU")
    private EntityManager manager;
    
    public UsuarioRN() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getManager() {
        return manager;
    }
    
    public void salvar(Usuario u)
    {
        //validar parâmetros
        if(u.getId()==null)
            super.adicionar(u);
        else
            super.atualizar(u);
    }   
}