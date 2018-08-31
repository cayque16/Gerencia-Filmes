package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 15/08/2018.
 */

public class FilmeDAO extends SQLiteOpenHelper {

    public FilmeDAO(Context context) {
        super(context, "Filmes", null, 4);
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

        sql = "CREATE TABLE Ano_Meta (id INTEGER PRIMARY KEY, " +
                "ano INTEGER, " +
                "meta INTEGER);";

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
            case 3: //indo para versão 4
                sql = "CREATE TABLE Ano_Meta (id INTEGER PRIMARY KEY, " +
                        "ano INTEGER, " +
                        "meta INTEGER);";
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

    public void insereAnoMeta(AnoMeta anoMeta) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAnoMeta(anoMeta);
        db.insert("Ano_Meta", null, dados);
    }

    private ContentValues pegaDadosDoAnoMeta(AnoMeta anoMeta) {
        ContentValues dados = new ContentValues();
        dados.put("ano", anoMeta.getAno());
        dados.put("meta", anoMeta.getMeta());

        return dados;
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

    public List<AnoMeta> buscaAnoMetaDoAno(int ano) {
        String sql = "SELECT * FROM Ano_Meta WHERE ano = ?";
        SQLiteDatabase db = getReadableDatabase();

        String[] params = {Integer.toString(ano)};
        Cursor c = db.rawQuery(sql, params);

        List<AnoMeta> anoMetas = populaAnoMeta(c);

        return anoMetas;
    }

    public List<AnoMeta> buscaAnoMeta() {
        String sql = "SELECT * FROM Ano_Meta";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        List<AnoMeta> anoMetas = populaAnoMeta(c);

        return anoMetas;
    }

    private List<AnoMeta> populaAnoMeta(Cursor c) {
        List<AnoMeta> anoMetas = new ArrayList<AnoMeta>();

        while (c.moveToNext()) {
            AnoMeta anoMeta = new AnoMeta();
            anoMeta.setAno(c.getInt(c.getColumnIndex("ano")));
            anoMeta.setMeta(c.getInt(c.getColumnIndex("meta")));

            anoMetas.add(anoMeta);
        }
        return anoMetas;
    }

    private List<FilmesAssistidos> populaFilmesAssistidos(Cursor c) {
        List<FilmesAssistidos> filmesAssistidos = new ArrayList<FilmesAssistidos>();

        while (c.moveToNext()) {
            FilmesAssistidos filme = new FilmesAssistidos();
            filme.setId(c.getLong(c.getColumnIndex("id")));
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

        String[] params = {Long.toString(filmesAssistidos.getId())};

        db.delete("Filmes_Assistidos", "id = ?", params);
    }

    public void deletaAnoMeta(AnoMeta anoMeta) {
        SQLiteDatabase db = getReadableDatabase();

        String[] params = {Long.toString(anoMeta.getId())};
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

        String[] params = {Long.toString(filmesAssistidos.getId())};
        db.update("Filmes_Assistidos", dados, "id = ?", params);
    }

    public void alteraAnoMeta(AnoMeta anoMeta) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAnoMeta(anoMeta);

        String[] params = {Long.toString(anoMeta.getId())};
        db.update("Ano_Meta", dados, "id = ?", params);
    }

    public boolean existeFilme(String imdbID) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT imdbID FROM Filmes WHERE imdbID = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{imdbID});
        int resultados = cursor.getCount();
        cursor.close();
        return resultados > 0;
    }

    public boolean existeAnoMeta(int ano){
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT * FROM Ano_Meta WHERE ano = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{Integer.toString(ano)});
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
