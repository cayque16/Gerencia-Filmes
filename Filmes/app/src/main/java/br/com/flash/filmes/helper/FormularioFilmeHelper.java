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
    private EditText campoNota, campoPoster;

    private Filme filme;

    public FormularioFilmeHelper(AddFilmeActivity activity) {
        campoTitulo = activity.findViewById(R.id.add_filme_titulo);
        campoImdbID = activity.findViewById(R.id.add_filme_imdbID);
        campoAno = activity.findViewById(R.id.add_filme_ano);
        campoDuracao = activity.findViewById(R.id.add_filme_duracao);
        campoNota = activity.findViewById(R.id.add_filme_nota);
        campoPoster = activity.findViewById(R.id.add_filme_poster);
        filme = new Filme();
    }

    public Filme pegaFilme() {
        filme.setTitle(campoTitulo.getText().toString());
        filme.setImdbID(campoImdbID.getText().toString());
        filme.setRuntime(Integer.parseInt(campoDuracao.getText().toString()));
        filme.setYear(Integer.valueOf(campoAno.getText().toString()));
        filme.setImdbRating(Double.valueOf(campoNota.getText().toString()));
        filme.setPoster(campoPoster.getText().toString());
        return filme;
    }

    public void preencheFormulario(Filme filme) {
        campoTitulo.setText(filme.getTitle());
        campoImdbID.setText(filme.getImdbID());
        campoAno.setText(Integer.toString(filme.getYear()));
        campoDuracao.setText(Integer.toString(filme.getRuntime()));
        campoNota.setText(Double.toString(filme.getImdbRating()));
        campoPoster.setText(filme.getPoster());
        this.filme = filme;
    }
}
