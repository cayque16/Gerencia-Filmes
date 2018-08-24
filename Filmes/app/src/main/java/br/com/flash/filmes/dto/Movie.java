package br.com.flash.filmes.dto;

import android.content.Intent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.flash.filmes.models.Filme;

/**
 * Created by cayqu on 22/08/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "Title",
        "Year",
        "Runtime",
        "Poster",
        "imdbRating",
        "imdbID",
        "Response"
})
public class Movie {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Response")
    private String response;

    @Override
    public String toString() {
        return title + "\n" + year + "\n" + runtime + "\n" + poster + "\n" + imdbRating + "\n" + imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public Filme getFilme() {
        Filme filme = new Filme();

        filme.setImdbID(this.imdbID);
        filme.setTitulo(this.title);
        filme.setAno(Integer.parseInt(this.year));
        filme.setDuracao(getRuntimeEmInt());
        filme.setNota(Double.valueOf(this.imdbRating));
        filme.setPoster(this.poster);

        return filme;
    }

    private int getRuntimeEmInt() {
        String concatena = "";

        for (int i = 0; i < runtime.length(); i++) {
            if (runtime.charAt(i) == ' ')
                break;
            concatena += String.valueOf(runtime.charAt(i));
        }
        return Integer.valueOf(concatena);
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
