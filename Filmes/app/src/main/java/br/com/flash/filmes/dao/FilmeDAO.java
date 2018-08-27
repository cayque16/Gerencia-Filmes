package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 15/08/2018.
 */

public class FilmeDAO extends SQLiteOpenHelper {

    public FilmeDAO(Context context) {
        super(context, "Filmes", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Filmes (id INTEGER PRIMARY KEY, " +
                "imdbID TEXT NOT NULL, " +
                "titulo TEXT NOT NULL, " +
                "ano INTEGER, " +
                "duracao INTEGER, " +
                "nota REAL, " +
                "poster TEXT, " +
                "posterBytes);";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE Filmes_Assistidos (id INTEGER PRIMARY KEY, " +
                "imdbID TEXT NOT NULL, " +
                "inedito INTEGER, " +
                "posAno INTEGER, " +
                "dataDia INTEGER, " +
                "dataMes INTEGER, " +
                "dataAno INTEGER);";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "";
        switch (i) {
            case 1: //indo para versão 2
                sql = "ALTER TABLE Filmes ADD COLUMN poster TEXT";
                sqLiteDatabase.execSQL(sql);
            case 2: //indo para versão 3
                sql = "ALTER TABLE Filmes ADD COLUMN posterBytes TEXT";
                sqLiteDatabase.execSQL(sql);
        }
    }

    public void insereFilme(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoFilme(filme);
        db.insert("Filmes", null, dados);
    }

    public void insereFilmeAssistido(FilmesAssistidos filmesAssistidos) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoFilmeAssistido(filmesAssistidos);
        db.insert("Filmes_Assistidos", null, dados);
    }

    private ContentValues pegaDadosDoFilmeAssistido(FilmesAssistidos filmesAssistidos) {
        ContentValues dados = new ContentValues();
        dados.put("imdbID", filmesAssistidos.getImdbID());
        dados.put("inedito", filmesAssistidos.getInedito());
        dados.put("posAno", filmesAssistidos.getPosAno());
        dados.put("dataDia", filmesAssistidos.getDataDia());
        dados.put("dataMes", filmesAssistidos.getDataMes());
        dados.put("dataAno", filmesAssistidos.getDataAno());

        return dados;
    }

    private ContentValues pegaDadosDoFilme(Filme filme) {
        ContentValues dados = new ContentValues();
        dados.put("imdbID", filme.getImdbID());
        dados.put("titulo", filme.getTitulo());
        dados.put("ano", filme.getAno());
        dados.put("duracao", filme.getDuracao());
        dados.put("nota", filme.getNota());
        dados.put("poster", filme.getPoster());
        dados.put("posterBytes", filme.getPosterBytes());
        return dados;
    }

    public List<Filme> buscaFilmes() {
        String sql = "SELECT * FROM Filmes";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Filme> filmes = populaFilmes(c);
        c.close();

        return filmes;
    }

    public List<FilmesAssistidos> buscaFilmesAssistidosNoAnoDe(int ano) {
        String sql = "SELECT * FROM Filmes_Assistidos WHERE dataAno = ?";

        SQLiteDatabase db = getReadableDatabase();

        String[] params = {Integer.toString(ano)};

        Cursor c = db.rawQuery(sql, params);
        List<FilmesAssistidos> filmesAssistidos = populaFilmesAssistidos(c);
        c.close();

        return filmesAssistidos;
    }

    private List<FilmesAssistidos> populaFilmesAssistidos(Cursor c) {
        List<FilmesAssistidos> filmesAssistidos = new ArrayList<FilmesAssistidos>();

        while (c.moveToNext()) {
            FilmesAssistidos filme = new FilmesAssistidos();
            filme.setId(c.getInt(c.getColumnIndex("id")));
            filme.setImdbID(c.getString(c.getColumnIndex("imdbID")));
            filme.setInedito(c.getInt(c.getColumnIndex("inedito")));
            filme.setPosAno(c.getInt(c.getColumnIndex("posAno")));
            filme.setDataDia(c.getInt(c.getColumnIndex("dataDia")));
            filme.setDataMes(c.getInt(c.getColumnIndex("dataMes")));
            filme.setDataAno(c.getInt(c.getColumnIndex("dataAno")));

            filmesAssistidos.add(filme);
        }
        return filmesAssistidos;
    }

    private List<Filme> populaFilmes(Cursor c) {
        List<Filme> filmes = new ArrayList<Filme>();

        while (c.moveToNext()) {
            Filme filme = new Filme();
            filme.setId(c.getLong(c.getColumnIndex("id")));
            filme.setImdbID(c.getString(c.getColumnIndex("imdbID")));
            filme.setTitulo(c.getString(c.getColumnIndex("titulo")));
            filme.setAno(c.getInt(c.getColumnIndex("ano")));
            filme.setDuracao(c.getInt(c.getColumnIndex("duracao")));
            filme.setNota(c.getDouble(c.getColumnIndex("nota")));
            filme.setPoster(c.getString(c.getColumnIndex("poster")));
            filme.setPosterBytes(c.getBlob(c.getColumnIndex("posterBytes")));

            filmes.add(filme);
        }
        return filmes;
    }

    public void deletaFilme(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {Long.toString(filme.getId())};

        db.delete("Filmes", "id = ?", params);
    }

    public void deletaFilmeAssistidoErrado(int posAno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {Integer.toString(posAno)};

        db.delete("Filmes_Assistidos", "posAno = ?", params);
    }

    public void deletaFilmeAssistido(FilmesAssistidos filmesAssistidos) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {Integer.toString(filmesAssistidos.getId())};

        db.delete("Filmes_Assistidos", "id = ?", params);
    }

    public void alteraFilme(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoFilme(filme);

        String[] params = {Long.toString(filme.getId())};
        db.update("Filmes", dados, "id = ?", params);
    }

    public void alteraFilmeAssistido(FilmesAssistidos filmesAssistidos) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoFilmeAssistido(filmesAssistidos);

        String[] params = {Integer.toString(filmesAssistidos.getId())};
        db.update("Filmes_Assisitdos", dados, "id = ?", params);
    }

    public boolean existeFilme(String imdbID) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT imdbID FROM Filmes WHERE imdbID = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{imdbID});
        int resultados = cursor.getCount();
        cursor.close();
        return resultados > 0;
    }

    public boolean filmeInedito(String imdbID) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT imdbID FROM Filmes_Assistidos WHERE imdbID = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{imdbID});
        int resultados = cursor.getCount();
        cursor.close();
        return resultados == 0;
    }

    public Filme retornaUmFilme(String imdbID) {
        String sql = "SELECT * FROM Filmes WHERE imdbID = ?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{imdbID});

        List<Filme> filmes = populaFilmes(c);

        return filmes.get(0);
    }
}
