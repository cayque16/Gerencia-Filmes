package br.com.flash.filmes.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by cayqu on 14/08/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filme implements Serializable{
    private Long id;
    private String imdbID;
    private String Title;
    private int Year;
    private int Runtime; //em minutos
    private double imdbRating; //imdbRating do IMDB
    private String Poster;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public int getRuntime() {
        return Runtime;
    }

    public void setRuntime(int runtime) {
        this.Runtime = runtime;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }



    @Override
    public String toString() {
        return imdbID + ": " + Title;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
