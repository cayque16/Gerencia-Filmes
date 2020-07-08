package br.com.flash.filmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.SuperModel;

public class AnoMetaDAO extends AbstractDAO {
    public AnoMetaDAO(Context context) {
        super(context);
        setNomeTabela(AnoMeta.DB_TABELA);
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
        dados.put(AnoMeta.DB_COLUNA_SINCRONIZADO, anoMeta.getSincronizado());
        dados.put(AnoMeta.DB_COLUNA_META, anoMeta.getMeta());

        return dados;
    }
}
