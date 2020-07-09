package br.com.flash.filmes.models;

public abstract class SuperModel {

    protected String idString;
    protected int sincronizado;
    protected int desativado;

    public static final String DB_COLUNA_ID = "id";
    public static final String DB_COLUNA_SINCRONIZADO = "sincronizado";
    public static final String DB_COLUNA_DESATIVADO = "desativado";

    @Override
    public String toString() {
        return "SuperModel{" +
                "idString='" + idString + '\'' +
                ", sincronizado=" + sincronizado +
                ", desativado=" + desativado +
                '}';
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public int getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }

    public int getDesativado() {
        return desativado;
    }

    public void setDesativado(int desativado) {
        this.desativado = desativado;
    }
}
