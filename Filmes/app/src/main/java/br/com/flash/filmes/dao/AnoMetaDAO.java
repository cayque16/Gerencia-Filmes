package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.SuperModel;

public class AnoMetaDAO extends CriaBanco {
    public AnoMetaDAO(Context context) {
        super(context);
    }

    @Override
    public void insere(SuperModel anoMeta) {
        SQLiteDatabase db = getWritableDatabase();
        insereIdSeNecessario(anoMeta);
        ContentValues dados = pegaDados(anoMeta);
        try {
            db.insert(AnoMeta.DB_TABELA, null, dados);
        } catch (SQLException e) {
            Log.d(TAG_LOG_BD, e.toString());
        }
    }

    @Override
    public List<SuperModel> buscaTodos() {
        String sql = "SELECT * FROM ano_meta";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<SuperModel> anoMetas = populaDados(c);
        c.close();

        return anoMetas;
    }

    @Override
    protected List<SuperModel> populaDados(Cursor c) {
        List<SuperModel> anoMetas = new ArrayList<SuperModel>();

        while(c.moveToNext()) {
            AnoMeta anoMeta = new AnoMeta();
            anoMeta.setIdString(c.getString(c.getColumnIndex(AnoMeta.DB_COLUNA_ID)));
            anoMeta.setMeta(c.getInt(c.getColumnIndex(AnoMeta.DB_COLUNA_META)));
            anoMeta.setAno(c.getInt(c.getColumnIndex(AnoMeta.DB_COLUNA_ANO)));
            anoMeta.setSincronizado(c.getInt(c.getColumnIndex(AnoMeta.DB_COLUNA_SINCRONIZADO)));

            anoMetas.add(anoMeta);
        }

        return anoMetas;
    }

    @Override
    protected ContentValues pegaDados(SuperModel model) {
        ContentValues dados = new ContentValues();
        AnoMeta anoMeta = (AnoMeta) model;

        dados.put(AnoMeta.DB_COLUNA_ID, anoMeta.getIdString());
        dados.put(AnoMeta.DB_COLUNA_ANO, anoMeta.getAno());
        dados.put(AnoMeta.DB_COLUNA_SINCRONIZADO, anoMeta.getId());
        dados.put(AnoMeta.DB_COLUNA_META, anoMeta.getMeta());

        return dados;
    }
}
