package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.SuperModel;

public class FilmesDAO extends AbstractDAO {
    public FilmesDAO(Context context) {
        super(context);
        setNomeTabela(Filme.DB_TABELA);
    }

    @Override
    protected ContentValues pegaDados(SuperModel model) {
        ContentValues dados = new ContentValues();
        Filme filme = (Filme) model;

        dados.put(Filme.DB_COLUNA_ID, filme.getIdString());
        dados.put(Filme.DB_COLUNA_IMDB, filme.getImdbID());
        dados.put(Filme.DB_COLUNA_TITULO, filme.getTitulo());
        dados.put(Filme.DB_COLUNA_ANO, filme.getAno());
        dados.put(Filme.DB_COLUNA_DURACAO, filme.getDuracao());
        dados.put(Filme.DB_COLUNA_NOTA, filme.getNota());
        dados.put(Filme.DB_COLUNA_POSTER, filme.getPoster());
        dados.put(Filme.DB_COLUNA_SINCRONIZADO, filme.getSincronizado());
        dados.put(Filme.DB_COLUNA_DESATIVADO, filme.getDesativado());
        dados.put(Filme.DB_COLUNA_POSTER_BYTES, filme.getPosterBytes());

        return dados;
    }

    @Override
    protected List<SuperModel> populaDados(Cursor c) {
        List<SuperModel> filmes = new ArrayList<>();

        while(c.moveToNext()) {
            Filme filme = new Filme();
            filme.setIdString(c.getString(c.getColumnIndex(Filme.DB_COLUNA_ID)));
            filme.setImdbID(c.getString(c.getColumnIndex(Filme.DB_COLUNA_IMDB)));
            filme.setTitulo(c.getString(c.getColumnIndex(Filme.DB_COLUNA_TITULO)));
            filme.setAno(c.getInt(c.getColumnIndex(Filme.DB_COLUNA_ANO)));
            filme.setDuracao(c.getInt(c.getColumnIndex(Filme.DB_COLUNA_DURACAO)));
            filme.setNota(c.getDouble(c.getColumnIndex(Filme.DB_COLUNA_NOTA)));
            filme.setPoster(c.getString(c.getColumnIndex(Filme.DB_COLUNA_POSTER)));
            filme.setSincronizado(c.getInt(c.getColumnIndex(Filme.DB_COLUNA_SINCRONIZADO)));
            filme.setDesativado(c.getInt(c.getColumnIndex(Filme.DB_COLUNA_DESATIVADO)));
            filme.setPosterBytes(c.getBlob(c.getColumnIndex(Filme.DB_COLUNA_POSTER_BYTES)));

            filmes.add(filme);
        }

        return filmes;
    }
}
