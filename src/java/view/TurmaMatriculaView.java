/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author mi
 */
public class TurmaMatriculaView {

    private Integer id;
    private Integer codTurma;
    private String nomeCadeira;
    
    public TurmaMatriculaView() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(Integer codTurma) {
        this.codTurma = codTurma;
    }

    public String getNomeCadeira() {
        return nomeCadeira;
    }

    public void setNomeCadeira(String nomeCadeira) {
        this.nomeCadeira = nomeCadeira;
    }
    
}
