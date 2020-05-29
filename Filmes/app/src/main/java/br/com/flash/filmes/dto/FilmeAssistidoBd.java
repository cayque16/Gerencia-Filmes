package br.com.flash.filmes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.flash.filmes.models.FilmesAssistidos;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "posano",
        "titulo",
        "ano",
        "duracao",
        "nota",
        "poster",
        "data",
        "inedito"
})
public class FilmeAssistidoBd {
    @JsonProperty("posano")
    private int posano;
    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("ano")
    private int ano;
    @JsonProperty("duracao")
    private int duracao;
    @JsonProperty("nota")
    private String nota;
    @JsonProperty("poster")
    private String poster;
    @JsonProperty("data")
    private String data;
    @JsonProperty("inedito")
    private int inedito;

    public int getPosano() {
        return posano;
    }

    public void setPosano(int posano) {
        this.posano = posano;
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

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getInedito() {
        return inedito;
    }

    public void setInedito(int inedito) {
        this.inedito = inedito;
    }

    public FilmesAssistidos getFilme() {
        FilmesAssistidos filme = new FilmesAssistidos();

        filme.setImdbID(titulo);
        filme.setInedito(inedito);
        filme.setPosAno(posano);
        filme.setData(data);

        return filme;
    }
}
