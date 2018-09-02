package br.com.flash.filmes.retrofit.services;

import br.com.flash.filmes.dto.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cayqu on 21/08/2018.
 */

public interface FilmeService {

    @GET("/?apikey=9eb1dfef")
    Call<Movie> buscaFilme(@Query("i") String imdbId);
}
