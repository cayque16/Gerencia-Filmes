package br.com.flash.filmes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import br.com.flash.filmes.preferences.TokenPreferences;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private TokenPreferences tokenPreferences = new TokenPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buscarToken();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 5000);
    }

    private void buscarToken() {
        Call<Token> call = new RetrofitInicializadorBd().getBdService().getToken(new Login());

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    tokenPreferences.setToken(response.body().getTokenJwt());
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
