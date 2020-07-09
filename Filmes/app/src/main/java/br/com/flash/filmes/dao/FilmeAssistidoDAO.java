package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.FilmesAssistidos;
import br.com.flash.filmes.models.SuperModel;

public class FilmeAssistidoDAO extends AbstractDAO{
    public FilmeAssistidoDAO(Context context) {
        super(context);
        setNomeTabela(FilmesAssistidos.DB_TABELA);
    }

    @Override
    protected ContentValues pegaDados(SuperModel model) {
        ContentValues dados = new ContentValues();
        FilmesAssistidos filmesAssistidos = (FilmesAssistidos) model;

        dados.put(FilmesAssistidos.DB_COLUNA_ID, filmesAssistidos.getIdString());
        dados.put(FilmesAssistidos.DB_COLUNA_ID_FILME, filmesAssistidos.getIdFilme());
        dados.put(FilmesAssistidos.DB_COLUNA_ID_ANO_META, filmesAssistidos.getIdAnoMeta());
        dados.put(FilmesAssistidos.DB_COLUNA_POS_ANO, filmesAssistidos.getPosAno());
        dados.put(FilmesAssistidos.DB_COLUNA_DATA_DIA, filmesAssistidos.getDataDia());
        dados.put(FilmesAssistidos.DB_COLUNA_DATA_MES, filmesAssistidos.getDataMes());
        dados.put(FilmesAssistidos.DB_COLUNA_DATA_ANO, filmesAssistidos.getDataAno());
        dados.put(FilmesAssistidos.DB_COLUNA_SINCRONIZADO, filmesAssistidos.getSincronizado());
        dados.put(FilmesAssistidos.DB_COLUNA_DESATIVADO, filmesAssistidos.getDesativado());
        dados.put(FilmesAssistidos.DB_COLUNA_INEDITO, filmesAssistidos.getInedito());

        return dados;
    }

    @Override
    protected List<SuperModel> populaDados(Cursor c) {
        List<SuperModel> filmeAssistidos = new ArrayList<>();

        while (c.moveToNext()) {
            FilmesAssistidos filmeAssistido = new FilmesAssistidos();
            filmeAssistido.setIdString(c.getString(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_ID)));
            filmeAssistido.setIdFilme(c.getString(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_ID_FILME)));
            filmeAssistido.setIdAnoMeta(c.getString(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_ID_ANO_META)));
            filmeAssistido.setPosAno(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_POS_ANO)));
            filmeAssistido.setDataDia(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_DATA_DIA)));
            filmeAssistido.setDataMes(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_DATA_MES)));
            filmeAssistido.setDataAno(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_DATA_ANO)));
            filmeAssistido.setSincronizado(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_SINCRONIZADO)));
            filmeAssistido.setDesativado(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_DESATIVADO)));
            filmeAssistido.setInedito(c.getInt(c.getColumnIndex(FilmesAssistidos.DB_COLUNA_INEDITO)));

            filmeAssistidos.add(filmeAssistido);
        }

        return filmeAssistidos;
    }

    @Override
    public String insereSeNaoExiste(SuperModel model) {
        return null;
    }

    @Override
    public Long insere(SuperModel model) {
        criaPosAno(model);
        return super.insere(model);
    }

    private void criaPosAno(SuperModel model) {
        String sql = "SELECT * FROM " + getNomeTabela() + " WHERE (" +
                FilmesAssistidos.DB_COLUNA_POS_ANO + " = " +
                "SELECT( MAX("+FilmesAssistidos.DB_COLUNA_POS_ANO+") FROM "
                + getNomeTabela() + " WHERE " + FilmesAssistidos.DB_COLUNA_DATA_ANO + " = ?) " +
                "AND ("+FilmesAssistidos.DB_COLUNA_DATA_ANO + " = ?))";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(((FilmesAssistidos) model).getDataAno()),Integer.toString(((FilmesAssistidos) model).getDataAno())});

        List<SuperModel> superModels = populaDados(c);

        try {
            ((FilmesAssistidos) model).setPosAno(((FilmesAssistidos) superModels.get(0)).getPosAno() + 1);
        } catch (IndexOutOfBoundsException e) {
            ((FilmesAssistidos) model).setPosAno(0);
        }
    }
}
