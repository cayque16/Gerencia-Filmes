package br.com.flash.filmes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.flash.filmes.models.AnoMeta;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "ano",
        "meta"
})
public class AnoMetaBd {
    @JsonProperty("id")
    private int id;
    @JsonProperty("ano")
    private int ano;
    @JsonProperty("meta")
    private int meta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public AnoMeta getAnoMeta() {
        AnoMeta anoMeta = new AnoMeta();

        anoMeta.setId(id);
        anoMeta.setAno(ano);
        anoMeta.setMeta(meta);

        return anoMeta;
    }
}


