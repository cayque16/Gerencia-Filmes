package br.com.flash.filmes.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenPreferences {
    private static final String TOKEN_PREFERENCES = "br.com.flash.filmes.preferences.TokenPreferences";
    private static final String TOKEN = "token";
    private Context context;

    public TokenPreferences(Context context) {
        this.context = context;
        buscarToken();
    }

    private Context getContext() {
        return this.context;
    }

    private void buscarToken() {
        Login login = new LoginPreferences(getContext()).getLogin();
        Call<Token> call = new RetrofitInicializadorBd().getBdService().getToken(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    setToken(response.body().getTokenJwt());
                    Toast.makeText(getContext(), "Token Salvo!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getContext(), "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setToken(String token) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(TOKEN, "");
    }

    public boolean temToken() {
        return !getToken().isEmpty();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(TOKEN_PREFERENCES, context.MODE_PRIVATE);
    }
}
