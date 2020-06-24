package br.com.flash.filmes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import br.com.flash.filmes.interfaces.BuscarToken;
import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import br.com.flash.filmes.preferences.FilmesPreferences;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements BuscarToken {

    private static final int TEMPO_EXIBICAO = 3000;
    private Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new FilmesPreferences(getBaseContext()).temLogin()) {
                    buscaToken();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
                finish();
            }
        }, TEMPO_EXIBICAO);
    }

    @Override
    public void buscaToken() {
        Login login = new FilmesPreferences(this).getLogin();
        Call<Token> call = new RetrofitInicializadorBd().getBdService().getToken(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    token.setToken(response.body().getTokenJwt());
                    Toast.makeText(getBaseContext(), "Token Salvo!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
