package br.com.flash.filmes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.flash.filmes.models.Filme;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "imdbid",
        "titulo",
        "ano",
        "duracao",
        "nota",
        "poster"
})

public class FilmeBd {
    @JsonProperty("imdbid")
    private String imdbid;
    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("ano")
    private int ano;
    @JsonProperty("duracao")
    private int duracao;
    @JsonProperty("nota")
    private Double nota;
    @JsonProperty("poster")
    private String poster;

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Filme getFilme() {
        Filme filme = new Filme();

        filme.setAno(ano);
        filme.setDuracao(duracao);
        filme.setImdbID(imdbid);
        filme.setNota(nota);
        filme.setPoster(poster);
        filme.setTitulo(titulo);

        return filme;
    }
}
