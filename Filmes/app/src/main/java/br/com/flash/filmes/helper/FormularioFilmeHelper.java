package br.com.flash.filmes.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.flash.filmes.R;
import br.com.flash.filmes.add.AddFilmeActivity;
import br.com.flash.filmes.models.Filme;

/**
 * Created by cayqu on 16/08/2018.
 */

public class FormularioFilmeHelper {

    private TextView campoTitulo, campoImdbID;
    private TextView campoAno, campoDuracao;
    private TextView campoNota, campoPoster;
    public TextView campoInedito;
    public static ImageView imagemPoster;
    private Handler handler = new Handler();

    private Filme filme;

    public FormularioFilmeHelper(AddFilmeActivity activity) {
        campoTitulo = activity.findViewById(R.id.add_filme_titulo);
        campoImdbID = activity.findViewById(R.id.add_filme_imdbID);
        campoAno = activity.findViewById(R.id.add_filme_ano);
        campoDuracao = activity.findViewById(R.id.add_filme_duracao);
        campoNota = activity.findViewById(R.id.add_filme_nota);
        campoPoster = activity.findViewById(R.id.add_filme_poster);
        imagemPoster = activity.findViewById(R.id.add_filme_poster_img);
        campoInedito = activity.findViewById(R.id.add_filme_inedito);
        filme = new Filme();
    }

    public Filme pegaFilme() {
        try {
            filme.setTitulo(campoTitulo.getText().toString());
            filme.setImdbID(campoImdbID.getText().toString());
            filme.setDuracao(Integer.parseInt(campoDuracao.getText().toString()));
            filme.setAno(Integer.valueOf(campoAno.getText().toString()));
            filme.setNota(Double.valueOf(campoNota.getText().toString()));
            filme.setPoster(campoPoster.getText().toString());
            return filme;
        } catch (Exception e) {
            return null;
        }
    }

    public void preencheFormulario(Filme filme) {
        campoTitulo.setText(filme.getTitulo());
        campoImdbID.setText(filme.getImdbID());
//        campoImdbID.setEnabled(false);
        campoAno.setText(Integer.toString(filme.getAno()));
        campoDuracao.setText(Integer.toString(filme.getDuracao()));
        campoNota.setText(Double.toString(filme.getNota()));
        campoPoster.setText(filme.getPoster());
        carregaPosterDaWeb(imagemPoster, filme);
        this.filme = filme;
    }

    private void carregaPosterDaWeb(final ImageView poster, final Filme filme) {
        new Thread() {
            public void run() {
                Bitmap img = null;
                final Filme filmeAux = filme;

                try {
                    URL url = new URL(filmeAux.getPoster());
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
                            filmeAux.setPosterBytes(saida.toByteArray());
//                            new FilmeDAO(AddFilmeActivity.this).alteraFilme(filmeAux);
                        }
                    }
                });
            }
        }.start();
    }
}
