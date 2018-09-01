package br.com.flash.filmes.models;

/**
 * Created by cayqu on 27/08/2018.
 */

public class AnoMeta {
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ano: " + ano + "\nMeta: " + meta;
    }

    @Override
    public boolean equals(Object obj) {
        AnoMeta anoMeta = (AnoMeta) obj;
        return this.ano == anoMeta.ano;
    }
}
