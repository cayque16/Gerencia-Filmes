package br.com.flash.filmes.models;

public class AnoMeta extends SuperModel{
    private int id;
    private int ano, meta;

    public static final String DB_TABELA = "ano_meta";
    public static final String DB_COLUNA_ANO = "ano";
    public static final String DB_COLUNA_META = "meta";

    public AnoMeta() {
    }

    public AnoMeta(int ano, int meta) {
        this.ano = ano;
        this.meta = meta;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: "+ idString +"Ano: " + ano + " Meta: " + meta;
    }

    @Override
    public boolean equals(Object obj) {
        AnoMeta anoMeta = (AnoMeta) obj;
        return this.ano == anoMeta.ano;
    }
}
