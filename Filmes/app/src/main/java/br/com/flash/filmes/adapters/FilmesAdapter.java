package br.com.flash.filmes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.com.flash.filmes.R;
import br.com.flash.filmes.R.color;
import br.com.flash.filmes.R.id;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

/**
 * Created by cayqu on 13/08/2018.
 */

public class FilmesAdapter extends BaseAdapter {
    private List<FilmesAssistidos> list;
    private Context context;
    private Handler handler = new Handler();

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
//        Filme filme = new FilmeDAO(context).retornaUmFilme(filmesAssistidos.getImdbID());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_adapter_filmes_assistidos, null);

        TextView tv1 = v.findViewById(R.id.list_adapter_titulo);
        tv1.setText(filmesAssistidos.getImdbID());

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

        //para visualizar o id de um filme assistido
//        tv3.setText(Long.toString(filmesAssistidos.getId()));

        TextView tv4 = v.findViewById(R.id.list_adapter_data);
        tv4.setText(filmesAssistidos.getData());

//        TextView tv5 = v.findViewById(R.id.list_adapter_ano);
//        tv5.setText(Integer.toString(filme.getAno()));

//        TextView tv6 = v.findViewById(id.list_adapter_duracao);
//        if (filme.getDuracao() > 0)
//            tv6.setText(filme.getDuracao() + "min");
//        else
//            tv6.setText("N/A");

//        TextView tv7 = v.findViewById(id.list_adapter_nota);
//        if (filme.getNota() > 0)
//            tv7.setText("IMDB: " + filme.getNota());
//        else
//            tv7.setText("N/A");

//        ImageView poster = v.findViewById(id.list_adapter_poster);
//
//        if (filme.getPosterBytes() == null)
//            carregaPosterDaWeb(poster, filme);
//        else
//            carregaPosterDoBanco(poster, filme);

        return v;
    }

    private void carregaPosterDoBanco(ImageView poster, Filme filme) {
        Bitmap raw = BitmapFactory.decodeByteArray(filme.getPosterBytes(), 0,
                filme.getPosterBytes().length);
        poster.setImageBitmap(raw);
    }

    private void carregaPosterDaWeb(final ImageView poster, final Filme filme) {
        new Thread() {
            public void run() {
                Bitmap img = null;

                try {
                    URL url = new URL(filme.getPoster());
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                } catch (IOException e) {

                }
                final Bitmap imgAux = img;
                handler.post(new Runnable() {
                    public void run() {
                        if (imgAux != null) {
                            poster.setImageBitmap(imgAux);
                            ByteArrayOutputStream saida = new ByteArrayOutputStream();
                            imgAux.compress(Bitmap.CompressFormat.JPEG, 100, saida);
                            filme.setPosterBytes(saida.toByteArray());
                            new FilmeDAO(context).alteraFilme(filme);
                        }
                    }
                });
            }
        }.start();
    }
}
