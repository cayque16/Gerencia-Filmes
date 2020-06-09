package br.com.flash.filmes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "idFilme",
        "posano",
        "titulo",
        "ano",
        "duracao",
        "nota",
        "poster",
        "dataDia",
        "dataMes",
        "dataAno",
        "inedito"
})
public class FilmeAssistidoBd {
    @JsonProperty("idFilme")
    private int idFilme;
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
    @JsonProperty("dataDia")
    private int dataDia;
    @JsonProperty("dataMes")
    private int dataMes;
    @JsonProperty("dataAno")
    private int dataAno;
    @JsonProperty("inedito")
    private int inedito;

    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

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

    public int getInedito() {
        return inedito;
    }

    public void setInedito(int inedito) {
        this.inedito = inedito;
    }

    public FilmesAssistidos getFilmeAssistido() {
        FilmesAssistidos filmesAssistidos = new FilmesAssistidos();
        Filme filme = new Filme();

        filmesAssistidos.setInedito(inedito);
        filmesAssistidos.setPosAno(posano);
        filmesAssistidos.setDataDia(dataDia);
        filmesAssistidos.setDataMes(dataMes);
        filmesAssistidos.setDataAno(dataAno);

        filme.setId(idFilme);
        filme.setTitulo(titulo);
        filme.setAno(ano);
        filme.setPoster(poster);
        filme.setDuracao(duracao);
        filme.setNota(Double.parseDouble(nota));

        filmesAssistidos.setFilme(filme);

        return filmesAssistidos;
    }
}
