package br.com.flash.filmes.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.flash.filmes.models.Login;

public class LoginPreferences {
    private static final String LOGIN_PREFERENCES = "br.com.flash.filmes.preferences.LoginPreferences";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private Context context;

    public LoginPreferences(Context context) {
        this.context = context;
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
        return  context.getSharedPreferences(LOGIN_PREFERENCES, context.MODE_PRIVATE);
    }
}
