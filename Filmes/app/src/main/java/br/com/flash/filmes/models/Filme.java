package br.com.flash.filmes.models;

import java.io.Serializable;

/**
 * Created by cayqu on 14/08/2018.
 */

public class Filme implements Serializable{
    private Long id;
    private String imdbID;
    private String title;
    private int year;
    private int runtime; //em minutos
    private double imdbRating; //imdbRating do IMDB
    private String poster;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }



    @Override
    public String toString() {
        return imdbID + ": " + title;
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
