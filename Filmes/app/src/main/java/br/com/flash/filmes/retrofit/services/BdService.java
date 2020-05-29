package br.com.flash.filmes.retrofit.services;

import java.util.List;

import br.com.flash.filmes.dto.AnoMetaBd;
import br.com.flash.filmes.dto.FilmeAssistidoBd;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BdService {

    @GET("/filmes/api/filmes/{ano}")
    Call<List<FilmeAssistidoBd>> buscaListaFilmesAssistidos(@Path("ano") int ano);

    @GET("/filmes/api/anosmeta/")
    Call<List<AnoMetaBd>> buscaAnosMeta();
}