package model;

public class GraduacoesModel {
    private Integer id;
    private String modalidade;
    private String graduacao;

    public GraduacoesModel(){

    }

    public String getModalidade() {
        return modalidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }
}
