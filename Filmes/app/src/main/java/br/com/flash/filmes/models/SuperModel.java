package br.com.flash.filmes.models;

public abstract class SuperModel {

    protected String idString;

    public static final String DB_COLUNA_ID = "id";
    public static final String DB_COLUNA_SINCRONIZADO = "sincronizado";

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
}
