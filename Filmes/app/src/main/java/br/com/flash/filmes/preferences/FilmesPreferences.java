package br.com.flash.filmes.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.flash.filmes.models.Login;

public class FilmesPreferences {
    private static final String PREFERENCES = "br.com.flash.filmes.preferences.FilmesPreferences";
    private static final String TOKEN = "token";
    private static final String USERNAME = "usernam";
    private static final String PASSWORD = "password";
    private Context context;

    public FilmesPreferences(Context context) {
        this.context = context;
    }

    public void setToken(String token) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences();
        return  preferences.getString(TOKEN, "");
    }

    public boolean temToken() {
        return !getToken().isEmpty();
    }

    public void setLogin(Login login) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME, login.getUsername());
        editor.putString(PASSWORD, login.getPassword());
        editor.commit();
    }

    public Login getLogin(){
        SharedPreferences preferences = getSharedPreferences();
        String username = preferences.getString(USERNAME,"");
        String password = preferences.getString(PASSWORD,"");
        if (username.equals("") || password.equals(""))
            return null;
        return new Login(username,password);
    }

    public boolean temLogin() {
        return !(getLogin() == null);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
    }
}




