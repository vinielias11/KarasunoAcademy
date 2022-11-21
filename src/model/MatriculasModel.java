package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatriculasModel extends EntidadesModel{
    private Integer codigoMatricula;
    private Integer codigoAluno;
    private Date dataMatricula;
    private Integer diaVencimento;
    private Date dataEncerramento;
    private String nomeAluno;

    public MatriculasModel(){

    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }


    public Integer getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(Integer codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getDataMatriculaFormatada() {
        Date data = new Date();
        data.setTime(getDataMatricula().getTime());

        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    public String getDataEncerramentoFormatada() {
        Date data = new Date();
        data.setTime(getDataEncerramento().getTime());

        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
