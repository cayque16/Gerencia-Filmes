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
    private static final int TEMPO_EXIBICAO = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, TEMPO_EXIBICAO);
    }
}
