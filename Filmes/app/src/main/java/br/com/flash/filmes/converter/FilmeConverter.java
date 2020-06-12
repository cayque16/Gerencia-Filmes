package br.com.flash.filmes.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

public class FilmeConverter {

    public String convertParaJson(Filme filme, FilmesAssistidos filmeAssistido){
        JSONStringer js = new JSONStringer();

        try {
            js.object().
                    key("id").value((filmeAssistido.getId())).
                    key("imdbId").value(filme.getImdbID()).
                    key("inedito").value(filmeAssistido.getInedito()).
                    key("data").value(filmeAssistido.getDataConcatenada()).
                    key("titulo").value(filme.getTitulo()).
                    key("ano").value(filme.getAno()).
                    key("duracao").value(filme.getDuracao()).
                    key("nota").value(filme.getNota()).
                    key("poster").value(filme.getPoster()).
            endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  js.toString();
    }
}
