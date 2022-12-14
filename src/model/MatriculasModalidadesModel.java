package model;

import java.util.Date;

public class MatriculasModalidadesModel {
    private Integer codigoMatricula;
    private Integer modalidade;
    private Integer graduacao;
    private Integer plano;
    private Date dataInicio;
    private Date dataFim;
    private String nomeModalidade;
    private String nomeGraduacao;
    private String nomePlano;

    public MatriculasModalidadesModel() {

    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Integer getModalidade() {
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }

    public Integer getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(Integer graduacao) {
        this.graduacao = graduacao;
    }

    public Integer getPlano() {
        return plano;
    }

    public void setPlano(Integer plano) {
        this.plano = plano;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomeModalidade() {
        return nomeModalidade;
    }

    public void setNomeModalidade(String nomeModalidade) {
        this.nomeModalidade = nomeModalidade;
    }

    public String getNomeGraduacao() {
        return nomeGraduacao;
    }

    public void setNomeGraduacao(String nomeGraduacao) {
        this.nomeGraduacao = nomeGraduacao;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }
}
