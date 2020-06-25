package br.com.flash.filmes.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.flash.filmes.models.Login;

public class FilmesPreferences {
    private static final String PREFERENCES = "br.com.flash.filmes.preferences.FilmesPreferences";
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NULL = "null";
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
        return  preferences.getString(TOKEN, NULL);
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
        String username = preferences.getString(USERNAME,NULL);
        String password = preferences.getString(PASSWORD,NULL);
        if (username.equals(NULL) || password.equals(NULL))
            return null;
        return new Login(username,password);
    }

    public boolean temLogin() {
        return !(getLogin() == null);
    }

    public void limparPreferences() {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        //Limpando o login
        editor.putString(USERNAME, NULL);
        editor.putString(PASSWORD, NULL);
        //Limpando o token
        editor.putString(TOKEN,NULL);
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
    }
}




