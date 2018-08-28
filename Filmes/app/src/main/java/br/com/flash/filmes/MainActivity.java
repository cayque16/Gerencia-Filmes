package br.com.flash.filmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import br.com.flash.filmes.adapters.FilmesAdapter;
import br.com.flash.filmes.add.AddFilmeActivity;
import br.com.flash.filmes.add.AddFilmeAssistidoActivity;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

public class MainActivity extends AppCompatActivity {

    private List<FilmesAssistidos> filmes;
    private ListView lista;
    private TextView totalAssistidos, percentualAssistidos;
    private TextView metaDoAno;
    private int anoAtual, metaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.main_lista);
        totalAssistidos = findViewById(R.id.main_assistidos);
        percentualAssistidos = findViewById(R.id.main_percentual_assistido);
        metaDoAno = findViewById(R.id.main_meta);

        atualizaAnoMeta();
        atualizaLista();
        atualizaDadosCabecalho();
    }

    private void atualizaAnoMeta() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        FilmeDAO dao = new FilmeDAO(this);

        anoAtual = calendar.get(Calendar.YEAR);
        if (dao.existeAnoMeta(anoAtual)) {
            metaAtual = dao
                    .buscaAnoMetaDoAno(anoAtual)
                    .get(0)
                    .getMeta();
        } else {
            anoAtual = 0;
            metaAtual = 0;
        }
    }

    private void atualizaDadosCabecalho() {
        int quantidadeAssistidos = new FilmeDAO(this).buscaFilmesAssistidosNoAnoDe(anoAtual).size();
        float percentual = (quantidadeAssistidos / (float) metaAtual) * 100;
        DecimalFormat df = new DecimalFormat("0.00");
        totalAssistidos.setText(Integer.toString(quantidadeAssistidos));
        percentualAssistidos.setText("(" + df.format(percentual) + "%)");
        metaDoAno.setText(Integer.toString(metaAtual));
    }

    private void atualizaLista() {
        filmes = new FilmeDAO(this).buscaFilmesAssistidosNoAnoDe(anoAtual);
        Collections.sort(filmes);
        lista.setAdapter(new FilmesAdapter(filmes, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_add:
                Intent vaiParaAddFilme = new Intent(this, AddFilmeAssistidoActivity.class);
                startActivity(vaiParaAddFilme);
//                FilmeDAO dao = new FilmeDAO(this);
//                dao.deletaFilmeAssistidoErrado(14);
//                Toast.makeText(this, "Apagou", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_main_ano:
                alteraAno();
                break;
            case R.id.menu_main_filmes:
                Intent vaiParaListaFilmes = new Intent(this, ListaFilmesActivity.class);
                startActivity(vaiParaListaFilmes);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        atualizaLista();
        atualizaDadosCabecalho();
        super.onResume();
    }

    public void alteraAno() {
        Toast.makeText(this, "Filé", Toast.LENGTH_SHORT).show();
    }
}
