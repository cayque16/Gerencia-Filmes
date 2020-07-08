package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;
import br.com.flash.filmes.models.SuperModel;

public abstract class AbstractDAO extends SQLiteOpenHelper {

    protected static final String NOME_BANCO = "Filmes.db";
    protected static final int VERSAO_BANCO = 2;
    protected static final String TAG_LOG_BD = "DB_LOG";
    protected String nomeTabela;

    public AbstractDAO(Context context) {
        super(context, NOME_BANCO, null,VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE ano_meta (" +
                AnoMeta.DB_COLUNA_ID + " CHAR(36) PRIMARY KEY, " +
                AnoMeta.DB_COLUNA_ANO + " INTEGER, " +
                AnoMeta.DB_COLUNA_SINCRONIZADO + " INTEGER DEFAULT 0, " +
                AnoMeta.DB_COLUNA_DESATIVADO + " INTEGER DEFAULT 0, " +
                AnoMeta.DB_COLUNA_META + " INTEGER);";
        try {
            sqLiteDatabase.execSQL(sql);
        } catch (SQLException e) {
            Log.d(TAG_LOG_BD,e.toString());
        }

        sql = "CREATE TABLE filmes (" +
                Filme.DB_COLUNA_ID + " CHAR(36) PRIMARY KEY, " +
                Filme.DB_COLUNA_IMDB + " TEXT NOT NULL, " +
                Filme.DB_COLUNA_TITULO + " TEXT NOT NULL, " +
                Filme.DB_COLUNA_ANO + " INTEGER, " +
                Filme.DB_COLUNA_DURACAO + " INTEGER, " +
                Filme.DB_COLUNA_NOTA + " NUMERIC, " +
                Filme.DB_COLUNA_POSTER + " TEXT, " +
                Filme.DB_COLUNA_SINCRONIZADO + " INTEGER DEFAULT 0, " +
                Filme.DB_COLUNA_DESATIVADO + " INTEGER DEFAULT 0, " +
                Filme.DB_COLUNA_POSTER_BYTES + " TEXT);";
        try {
            sqLiteDatabase.execSQL(sql);
        } catch (SQLException e) {
            Log.d(TAG_LOG_BD,e.toString());
        }

        sql = "CREATE TABLE filme_assistido (" +
                FilmesAssistidos.DB_COLUNA_ID + " CHAR(36) PRIMARY KEY, " +
                FilmesAssistidos.DB_COLUNA_ID_FILME + " CHAR(36) NOT NULL, " +
                FilmesAssistidos.DB_COLUNA_ID_ANO_META + " CHAR(36) NOT NULL, " +
                FilmesAssistidos.DB_COLUNA_POS_ANO + " INETEGER, " +
                FilmesAssistidos.DB_COLUNA_DATA_DIA + " INTEGER, " +
                FilmesAssistidos.DB_COLUNA_DATA_MES + " INTEGER, " +
                FilmesAssistidos.DB_COLUNA_DATA_ANO + " INTEGER, " +
                FilmesAssistidos.DB_COLUNA_SINCRONIZADO + " INTEGER DEFAULT 0, " +
                FilmesAssistidos.DB_COLUNA_DESATIVADO + " INTEGER DEFAULT 0, " +
                FilmesAssistidos.DB_COLUNA_INEDITO + " INTEGER);";
        try {
            sqLiteDatabase.execSQL(sql);
        } catch (SQLException e) {
            Log.d(TAG_LOG_BD,e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "";
        switch (i) {
            case 1: //indo para a versao 2
                sql = "ALTER TABLE ano_meta ADD COLUMN desativado INTEGER DEFAULT 0";
                sqLiteDatabase.execSQL(sql);
                sql = "ALTER TABLE filmes ADD COLUMN desativado INTEGER DEFAULT 0";
                sqLiteDatabase.execSQL(sql);
                sql = "ALTER TABLE filme_assistido ADD COLUMN desativado INTEGER DEFAULT 0";
                sqLiteDatabase.execSQL(sql);
        }
    }

    public void insere(SuperModel model) {
        SQLiteDatabase db = getWritableDatabase();
        insereIdSeNecessario(model);
        ContentValues dados = pegaDados(model);
        db.insert(getNomeTabela(), null, dados);
    }

    public List<SuperModel> buscaTodos() {
        String sql ="SELECT * FROM " + getNomeTabela();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<SuperModel> model = populaDados(c);
        c.close();

        return model;
    }

    protected abstract ContentValues pegaDados(SuperModel model);

    protected abstract List<SuperModel> populaDados(Cursor c);

    protected void insereIdSeNecessario(SuperModel model) {
        if (model.getIdString() == null) {
            model.setIdString(geraUUID());
        }
    }

    protected String geraUUID() {
        return UUID.randomUUID().toString();
    }

    protected String getNomeTabela() {
        return nomeTabela;
    }

    protected void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }
}
