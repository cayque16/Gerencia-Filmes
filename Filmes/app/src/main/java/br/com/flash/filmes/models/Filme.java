package br.com.flash.filmes.models;

import java.io.Serializable;

/**
 * Created by cayqu on 14/08/2018.
 */
public class Filme implements Serializable {
    private Long id;
    private String imdbID;
    private String titulo;
    private int ano;
    private int duracao; //em minutos
    private double nota; //nota do IMDB
    private String poster;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }


    @Override
    public String toString() {
        return imdbID + ": " + titulo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
