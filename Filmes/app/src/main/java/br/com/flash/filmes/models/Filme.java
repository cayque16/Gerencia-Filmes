package br.com.flash.filmes.models;

import java.io.Serializable;

public class Filme extends SuperModel implements Serializable {
    private int id;
    private String imdbID;
    private String titulo;
    private int ano;
    private int duracao; //em minutos
    private double nota; //nota do IMDB
    private String poster;
    private byte[] posterBytes;

    public static final String DB_COLUNA_IMDB = "imdbID";
    public static final String DB_COLUNA_TITULO = "titulo";
    public static final String DB_COLUNA_ANO = "ano";
    public static final String DB_COLUNA_DURACAO = "duracao";
    public static final String DB_COLUNA_NOTA = "nota";
    public static final String DB_COLUNA_POSTER = "poster";
    public static final String DB_COLUNA_POSTER_BYTES = "posterBytes";

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPosterBytes() {
        return posterBytes;
    }

    public void setPosterBytes(byte[] posterBytes) {
        this.posterBytes = posterBytes;
    }
}
