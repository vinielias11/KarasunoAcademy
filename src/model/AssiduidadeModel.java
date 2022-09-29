package model;

import java.sql.Timestamp;

public class AssiduidadeModel {
    private Integer codigoMatricula;
    private Timestamp dataEntrada;

    public AssiduidadeModel() {

    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Timestamp getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Timestamp dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
