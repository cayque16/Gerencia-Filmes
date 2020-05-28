package br.com.flash.filmes.retrofit.services;

import br.com.flash.filmes.dto.DadosBd;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BdService {

    @GET("/filmes/api/filmes/{ano}")
    Call<DadosBd> buscaFilme(@Path("ano") int ano);
}