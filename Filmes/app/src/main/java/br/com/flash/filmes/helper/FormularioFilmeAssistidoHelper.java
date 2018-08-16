package br.com.flash.filmes.helper;

import android.widget.EditText;
import android.widget.Switch;

import br.com.flash.filmes.R;
import br.com.flash.filmes.add.AddFilmeAssistidoActivity;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 16/08/2018.
 */

public class FormularioFilmeAssistidoHelper {
    private EditText campoImdbId;
    private Switch campoInedito;
    private EditText campoPosAno, campoDia;
    private EditText campoMes, campoAno;

    private FilmesAssistidos filmesAssistidos;

    public FormularioFilmeAssistidoHelper(AddFilmeAssistidoActivity activity) {
        campoImdbId = activity.findViewById(R.id.add_filme_assistido_imdbID);
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
}