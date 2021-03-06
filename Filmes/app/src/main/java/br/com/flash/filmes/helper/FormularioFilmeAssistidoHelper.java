package br.com.flash.filmes.helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import br.com.flash.filmes.R;
import br.com.flash.filmes.activitys.add.AddFilmeActivity;
import br.com.flash.filmes.models.FilmesAssistidos;

public class FormularioFilmeAssistidoHelper {
    public Switch campoInedito;
    private EditText campoImdbId;
    private EditText campoPosAno, campoData;
    private Context context;
    private Calendar calendar;
    private DatePickerDialog dialogDatePicker;
    private DateFormat dateFormat;

    private FilmesAssistidos filmesAssistidos;

    public FormularioFilmeAssistidoHelper(AddFilmeActivity activity) {
        context = activity;

        campoImdbId = activity.findViewById(R.id.add_filme_imdbID);
        campoInedito = activity.findViewById(R.id.add_filme_assistido_inedito);

        campoInedito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campoInedito.isChecked())
                    new FormularioFilmeHelper((AddFilmeActivity) context)
                            .campoInedito
                            .setText(R.string.inedito);
                else
                    new FormularioFilmeHelper((AddFilmeActivity) context)
                            .campoInedito
                            .setText(R.string.jaVisto);
            }
        });

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
        try {
            if (campoInedito.isChecked())
                filmesAssistidos.tornaInedito();
            else
                filmesAssistidos.tiraInedito();
            filmesAssistidos.setDataDia(calendar.get(Calendar.DAY_OF_MONTH));
            filmesAssistidos.setDataMes(calendar.get(Calendar.MONTH) + 1);
            filmesAssistidos.setDataAno(calendar.get(Calendar.YEAR));
            return filmesAssistidos;
        } catch (Exception e) {
            return null;
        }
    }

    public void preencheFormulario(FilmesAssistidos filmesAssistidos) {
        calendar.set(Calendar.YEAR, filmesAssistidos.getDataAno());
        calendar.set(Calendar.MONTH, filmesAssistidos.getDataMes() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, filmesAssistidos.getDataDia());

        campoData.setText(dateFormat.format(calendar.getTime()));
        campoInedito.setChecked(filmesAssistidos.ehInedito());
        campoImdbId.setEnabled(false);
        campoPosAno.setText(Integer.toString(filmesAssistidos.getPosAno()));
        this.filmesAssistidos = filmesAssistidos;
    }
}