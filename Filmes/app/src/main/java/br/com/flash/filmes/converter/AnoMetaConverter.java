package br.com.flash.filmes.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.flash.filmes.models.AnoMeta;

public class AnoMetaConverter {

    public String convertParaJson(AnoMeta anoMeta){
        JSONStringer js = new JSONStringer();

        try {
            js.object().
                    key("ano").value(anoMeta.getAno()).
                    key("meta").value(anoMeta.getMeta()).
                    endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  js.toString();
    }
}
