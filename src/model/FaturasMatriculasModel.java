package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class FaturasMatriculasModel {

    private Integer id;
    private Integer codigoMatricula;
    private Date dataVencimento;
    private Double valor;
    private Timestamp dataPagamento;
    private Date dataCancelamento;

    public FaturasMatriculasModel() {

    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id;  }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) { this.codigoMatricula = codigoMatricula; }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Timestamp getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Timestamp dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }
}
