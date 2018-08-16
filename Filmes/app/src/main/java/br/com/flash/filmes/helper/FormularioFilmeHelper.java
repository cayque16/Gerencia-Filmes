package br.com.flash.filmes.helper;

import android.widget.EditText;


import br.com.flash.filmes.add.AddFilmeActivity;
import br.com.flash.filmes.R;
import br.com.flash.filmes.models.Filme;

/**
 * Created by cayqu on 16/08/2018.
 */

public class FormularioFilmeHelper {

    private EditText campoTitulo, campoImdbID;
    private EditText campoAno, campoDuracao;
    private EditText campoNota;

    private Filme filme;

    public FormularioFilmeHelper(AddFilmeActivity activity) {
        campoTitulo = activity.findViewById(R.id.add_filme_titulo);
        campoImdbID = activity.findViewById(R.id.add_filme_imdbID);
        campoAno = activity.findViewById(R.id.add_filme_ano);
        campoDuracao = activity.findViewById(R.id.add_filme_duracao);
        campoNota = activity.findViewById(R.id.add_filme_nota);
        filme = new Filme();
    }

    public Filme pegaFilme() {
        filme.setTitulo(campoTitulo.getText().toString());
        filme.setImdbID(campoImdbID.getText().toString());
        filme.setDuracao(Integer.parseInt(campoDuracao.getText().toString()));
        filme.setAno(Integer.valueOf(campoAno.getText().toString()));
        filme.setNota(Double.valueOf(campoNota.getText().toString()));

        return filme;
    }
}
