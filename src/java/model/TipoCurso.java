/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mi
 */
@Entity
public class TipoCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String tipoCurso;

    public String getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoCurso != null ? tipoCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCurso)) {
            return false;
        }
        TipoCurso other = (TipoCurso) object;
        if ((this.tipoCurso == null && other.tipoCurso != null) || (this.tipoCurso != null && !this.tipoCurso.equals(other.tipoCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoCurso[ tipoCurso=" + tipoCurso + " ]";
    }
    
}
