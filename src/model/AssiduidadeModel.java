package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getDataEntradaFormatada() {
        Date date = new Date();
        date.setTime(getDataEntrada().getTime());

        return new SimpleDateFormat("dd/MM/yyyy H:m").format(date);
    }

    public void setDataEntrada(Timestamp dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
