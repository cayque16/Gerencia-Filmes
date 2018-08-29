package br.com.flash.filmes.helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
    private EditText campoPosAno, campoData;
    private ArrayList<String> listaAutoComplete = new ArrayList<String>();
    private Context context;
    private Calendar calendar;
    private DatePickerDialog dialogDatePicker;
    private DateFormat dateFormat;

    private FilmesAssistidos filmesAssistidos;

    public FormularioFilmeAssistidoHelper(AddFilmeAssistidoActivity activity) {
        context = activity;

        campoImdbId = activity.findViewById(R.id.add_filme_assistido_imdbID);
        preencheListaAutoComplete();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, listaAutoComplete);
        campoImdbId.setAdapter(adapter);

        campoInedito = activity.findViewById(R.id.add_filme_assistido_inedito);
        campoPosAno = activity.findViewById(R.id.add_filme_assistido_posAno);
        campoData = activity.findViewById(R.id.add_filme_assistido_data);
        filmesAssistidos = new FilmesAssistidos();

        calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        dateFormat = DateFormat.getDateInstance();

        campoData.setText(dateFormat.format(calendar.getTime()));

        dialogDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        campoData.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker.show();
            }
        });
    }

    public FilmesAssistidos pegaFilmeAssistido() {
        filmesAssistidos.setImdbID(pegaImdb(campoImdbId.getText().toString()));
        if (campoInedito.isChecked())
            filmesAssistidos.tornaInedito();
        else
            filmesAssistidos.tiraInedito();
        filmesAssistidos.setPosAno(Integer.valueOf(campoPosAno.getText().toString()));
        filmesAssistidos.setDataDia(calendar.get(Calendar.DAY_OF_MONTH));
        filmesAssistidos.setDataMes(calendar.get(Calendar.MONTH) + 1);
        filmesAssistidos.setDataAno(calendar.get(Calendar.YEAR));

        return filmesAssistidos;
    }

    public void preencheListaAutoComplete() {
        FilmeDAO dao = new FilmeDAO(context);
        for (Filme filme : dao.buscaFilmes()) {
            listaAutoComplete.add(filme.toString());
        }
    }

    public String pegaImdb(String campoImdbID) {
        String concatenado = "";

        for (int i = 0; i < campoImdbID.length(); i++) {
            if (campoImdbID.charAt(i) == ':')
                break;
            concatenado += String.valueOf(campoImdbID.charAt(i));
        }

        return concatenado;
    }
}