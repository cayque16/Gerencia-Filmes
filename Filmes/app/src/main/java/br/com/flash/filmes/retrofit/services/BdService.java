package br.com.flash.filmes.retrofit.services;

import java.util.List;

import br.com.flash.filmes.dto.AnoMetaBd;
import br.com.flash.filmes.dto.FilmeAssistidoBd;
import br.com.flash.filmes.dto.FilmeBd;
import br.com.flash.filmes.models.Filme;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BdService {

    @GET("/filmes/api/filmes/{ano}")
    Call<List<FilmeAssistidoBd>> buscaListaFilmesAssistidos(@Path("ano") int ano);

    @GET("/filmes/api/anosmeta/")
    Call<List<AnoMetaBd>> buscaAnosMeta();

    @GET("/filmes/api/anosmeta/{ano}")
    Call<AnoMetaBd> getAnoMeta(@Path("ano") int ano);

    @GET("filmes/api/filmes/getfilme/{id}")
    Call<FilmeBd> getFilmeById(@Path("id") int id);
}