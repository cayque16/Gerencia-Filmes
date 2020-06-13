package br.com.flash.filmes.models;

public class Token {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenJwt() {
        return "JWT " + token;
    }
}
