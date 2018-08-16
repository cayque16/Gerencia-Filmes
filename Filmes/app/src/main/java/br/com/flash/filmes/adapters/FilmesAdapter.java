package br.com.flash.filmes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.flash.filmes.MainActivity;
import br.com.flash.filmes.R;
import br.com.flash.filmes.R.*;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 13/08/2018.
 */

public class FilmesAdapter extends BaseAdapter {
    private ArrayList<FilmesAssistidos> list;
    private Context context;

    public FilmesAdapter(ArrayList<FilmesAssistidos> list, Context context) {
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

        FilmesAssistidos filme = list.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_adapter_filme, null);

        TextView tv1 = v.findViewById(R.id.list_adapter_titulo);
        tv1.setText(filme.getImdbID());

        TextView tv2 = v.findViewById(R.id.list_adapter_inedito);
        LinearLayout caixa = v.findViewById(R.id.list_adapter_caixa_posicao);

        if (filme.ehInedito()) {
            tv2.setText("Inedito");
            caixa.setBackgroundResource(R.color.inedito);
        } else {
            tv2.setText("Repetido");
            caixa.setBackgroundResource(color.jaVisto);
        }

        TextView tv3 = v.findViewById(R.id.list_adapter_posicao_ano);
        tv3.setText(filme.getPosAnoFormatado());

        TextView tv4 = v.findViewById(id.list_adapter_data);
        tv4.setText(filme.getDataFormatada());

        return v;
    }
}
