package br.com.flash.filmes.models;

public class AnoMeta {
    private int id;
    private int ano, meta;

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
        return "Ano: " + ano + " Meta: " + meta;
    }

    @Override
    public boolean equals(Object obj) {
        AnoMeta anoMeta = (AnoMeta) obj;
        return this.ano == anoMeta.ano;
    }
}
