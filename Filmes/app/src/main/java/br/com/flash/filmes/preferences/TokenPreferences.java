package br.com.flash.filmes.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenPreferences {
    private static final String TOKEN_PREFERENCES = "br.com.flash.filmes.preferences.TokenPreferences";
    private static final String TOKEN = "token";
    private Context context;

    public TokenPreferences(Context context) {
        this.context = context;
    }

    public void setToken(String token) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(TOKEN,"");
    }

    public boolean temToken() {
        return !getToken().isEmpty();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(TOKEN_PREFERENCES,context.MODE_PRIVATE);
    }
}
