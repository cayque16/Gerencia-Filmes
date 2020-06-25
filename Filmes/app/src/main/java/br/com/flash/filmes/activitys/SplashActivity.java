package br.com.flash.filmes.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import br.com.flash.filmes.R;
import br.com.flash.filmes.preferences.FilmesPreferences;

public class SplashActivity extends SuperActivity {

    private static final int TEMPO_EXIBICAO = 3000;
    private FilmesPreferences filmesPreferences = new FilmesPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (filmesPreferences.temLogin()) buscaToken();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (filmesPreferences.temLogin()) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
                finish();
            }
        }, TEMPO_EXIBICAO);
    }
}
