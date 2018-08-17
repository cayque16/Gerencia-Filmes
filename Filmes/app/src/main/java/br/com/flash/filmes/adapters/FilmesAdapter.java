package br.com.flash.filmes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.R;
import br.com.flash.filmes.R.*;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 13/08/2018.
 */

public class FilmesAdapter extends BaseAdapter {
    private List<FilmesAssistidos> list;
    private Context context;

    public FilmesAdapter(List<FilmesAssistidos> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        FilmesAssistidos filmesAssistidos = list.get(i);
        Filme filme = new FilmeDAO(context).retornaUmFilme(filmesAssistidos.getImdbID());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_adapter_filmes_assistidos, null);

        TextView tv1 = v.findViewById(R.id.list_adapter_titulo);
        tv1.setText(filme.getTitulo());

        TextView tv2 = v.findViewById(R.id.list_adapter_inedito);
        LinearLayout caixa = v.findViewById(R.id.list_adapter_caixa_posicao);

        if (filmesAssistidos.ehInedito()) {
            tv2.setText("Inédito");
            caixa.setBackgroundResource(R.color.inedito);
        } else {
            tv2.setText("Repetido");
            caixa.setBackgroundResource(color.jaVisto);
        }

        TextView tv3 = v.findViewById(R.id.list_adapter_posicao_ano);
        tv3.setText(filmesAssistidos.getPosAnoFormatado());

        TextView tv4 = v.findViewById(R.id.list_adapter_data);
        tv4.setText(filmesAssistidos.getDataFormatada());

        TextView tv5 = v.findViewById(R.id.list_adapter_ano);
        tv5.setText(Integer.toString(filme.getAno()));

        TextView tv6 = v.findViewById(id.list_adapter_duracao);
        tv6.setText(filme.getDuracao() + "min");

        TextView tv7 = v.findViewById(id.list_adapter_nota);
        tv7.setText("IMDB: " + filme.getNota());

        return v;
    }
}
