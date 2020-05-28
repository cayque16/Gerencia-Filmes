package br.com.flash.filmes.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by cayqu on 14/08/2018.
 */

public class FilmesAssistidos implements Comparable<FilmesAssistidos>, Serializable {
    private Long id;
    private String imdbID;
    private int inedito; //1 para sim e 0 para nao
    private int posAno; //posicao do filme no ano
    private int dataDia;
    private int dataMes;
    private int dataAno;
    private String data;

    @Override
    public String toString() {
        return " " + id + " " + imdbID + " " +
                " " + inedito + " " + posAno + " " + dataDia + " " + dataMes + " " + dataAno;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public int getInedito() {
        return inedito;
    }

    public void setInedito(int inedito) {
        this.inedito = inedito;
    }

    public int getDataDia() {
        return dataDia;
    }

    public void setDataDia(int dataDia) {
        this.dataDia = dataDia;
    }

    public int getDataMes() {
        return dataMes;
    }

    public void setDataMes(int dataMes) {
        this.dataMes = dataMes;
    }

    public int getDataAno() {
        return dataAno;
    }

    public void setDataAno(int dataAno) {
        this.dataAno = dataAno;
    }

    public int getPosAno() {
        return posAno;
    }

    public void setPosAno(int posAno) {
        this.posAno = posAno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPosAnoFormatado() {
        if (posAno < 10)
            return "00" + posAno;
        else if (posAno < 100)
            return "0" + posAno;
        return Integer.toString(posAno);
    }

    public boolean ehInedito() {
        return inedito == 1;
    }

    public String getDataFormatada() {

        return "assistido em " + dataDia + " de " + numToMes(dataMes)
                + " de " + dataAno;
    }

    public String numToMes(int num) {
        switch (num) {
            case 1:
                return "jan";
            case 2:
                return "fev";
            case 3:
                return "mar";
            case 4:
                return "abr";
            case 5:
                return "maio";
            case 6:
                return "jun";
            case 7:
                return "jul";
            case 8:
                return "ago";
            case 9:
                return "set";
            case 10:
                return "out";
            case 11:
                return "nov";
            default:
                return "dez";
        }
    }

    @Override
    public int compareTo(@NonNull FilmesAssistidos filmesAssistidos) {

        if (this.posAno > filmesAssistidos.getPosAno())
            return -1;
        if (this.posAno < filmesAssistidos.getPosAno())
            return 1;
        return 0;
    }


    public void tornaInedito() {
        inedito = 1;
    }

    public void tiraInedito() {
        inedito = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
