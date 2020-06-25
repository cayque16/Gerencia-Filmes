package br.com.flash.filmes;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import br.com.flash.filmes.preferences.FilmesPreferences;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperActivity extends AppCompatActivity {
    protected FilmesPreferences filmesPreferences = new FilmesPreferences(this);
    protected Token token = new Token();

    protected void buscaToken() {
        Login login =  filmesPreferences.getLogin();
        Call<Token> call = new RetrofitInicializadorBd().getBdService().getToken(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    filmesPreferences.setToken(response.body().getTokenJwt());
                    Toast.makeText(getBaseContext(), "Token Salvo!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected String getTokenRefresh() {
        String token = filmesPreferences.getToken();

        buscaToken();

        return token;
    }
}
