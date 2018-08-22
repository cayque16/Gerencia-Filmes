package br.com.flash.filmes.helper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

import br.com.flash.filmes.R;
import br.com.flash.filmes.add.AddFilmeAssistidoActivity;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 16/08/2018.
 */

public class FormularioFilmeAssistidoHelper {
    private AutoCompleteTextView campoImdbId;
    private Switch campoInedito;
    private EditText campoPosAno, campoDia;
    private EditText campoMes, campoAno;
    private ArrayList<String> listaAutoComplete = new ArrayList<String>();
    private Context context;

    private FilmesAssistidos filmesAssistidos;

    public FormularioFilmeAssistidoHelper(AddFilmeAssistidoActivity activity) {
        context = activity;

        campoImdbId = activity.findViewById(R.id.add_filme_assistido_imdbID);
        preencheListaAutoComplete();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, listaAutoComplete);
        campoImdbId.setAdapter(adapter);

        campoInedito = activity.findViewById(R.id.add_filme_assistido_inedito);
        campoPosAno = activity.findViewById(R.id.add_filme_assistido_posAno);
        campoDia = activity.findViewById(R.id.add_filme_assistido_dataDia);
        campoMes = activity.findViewById(R.id.add_filme_assistido_dataMes);
        campoAno = activity.findViewById(R.id.add_filme_assistido_dataAno);
        filmesAssistidos = new FilmesAssistidos();
    }

    public FilmesAssistidos pegaFilmeAssistido() {
        filmesAssistidos.setImdbID(campoImdbId.getText().toString());
        if (campoInedito.isChecked())
            filmesAssistidos.tornaInedito();
        else
            filmesAssistidos.tiraInedito();
        filmesAssistidos.setPosAno(Integer.valueOf(campoPosAno.getText().toString()));
        filmesAssistidos.setDataDia(Integer.valueOf(campoDia.getText().toString()));
        filmesAssistidos.setDataMes(Integer.valueOf(campoMes.getText().toString()));
        filmesAssistidos.setDataAno(Integer.valueOf(campoAno.getText().toString()));

        return filmesAssistidos;
    }

    public void preencheListaAutoComplete() {
        FilmeDAO dao = new FilmeDAO(context);
        for (Filme filme : dao.buscaFilmes()) {
            listaAutoComplete.add(filme.toString());
        }
    }
}