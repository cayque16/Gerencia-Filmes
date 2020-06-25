package br.com.flash.filmes.retrofit;

import br.com.flash.filmes.retrofit.services.FilmeService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializadorFilmes {
    private final Retrofit retrofit;

    public RetrofitInicializadorFilmes() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public FilmeService getFilmeService() {
        return retrofit.create(FilmeService.class);
    }
}
