package br.com.flash.filmes.retrofit.services;

import java.util.List;

import br.com.flash.filmes.dto.AnoMetaBd;
import br.com.flash.filmes.dto.FilmeAssistidoBd;
import br.com.flash.filmes.dto.FilmeBd;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BdService {

    @GET("/filmes/api/filmes/{ano}")
    Call<List<FilmeAssistidoBd>> buscaListaFilmesAssistidos(@Path("ano") int ano, @Header("Authorization") String token);

    @GET("/filmes/api/anosmeta/")
    Call<List<AnoMetaBd>> buscaAnosMeta(@Header("Authorization") String token);

    @GET("/filmes/api/anosmeta/{ano}")
    Call<AnoMetaBd> getAnoMeta(@Path("ano") int ano, @Header("Authorization") String token);

    @GET("filmes/api/filmes/getfilme/{id}")
    Call<FilmeBd> getFilmeById(@Path("id") int id, @Header("Authorization") String token);

    @POST("/filmes/api/filmesassistido/post/")
    Call<ResponseBody> insereFilmeAssistido(@Body RequestBody dados, @Header("Authorization") String token);

    @PUT("/filmes/api/anosmeta/put/")
    Call<ResponseBody> alteraAnoMeta(@Body RequestBody dados, @Header("Authorization") String token);

    @POST("/filmes/api/token/")
    Call<Token> getToken(@Body Login login);
}