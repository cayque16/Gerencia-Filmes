package br.com.flash.filmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import br.com.flash.filmes.adapters.FilmesAdapter;
import br.com.flash.filmes.add.AddFilmeActivity;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.FilmesAssistidos;

public class MainActivity extends AppCompatActivity {

    private List<FilmesAssistidos> filmes;
    private ListView listaFilmesAssistidos;
    private TextView totalAssistidos, percentualAssistidos;
    private TextView metaDoAno;
    private int anoAtual, metaAtual;
    private ArrayList<String> listaAnoMeta = new ArrayList<String>();
    private Spinner spinnerAnoMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaFilmesAssistidos = findViewById(R.id.main_lista);

        listaFilmesAssistidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FilmesAssistidos filme = (FilmesAssistidos) listaFilmesAssistidos.getItemAtPosition(i);
                Intent vaiProFormulario = new Intent(
                        MainActivity.this, AddFilmeActivity.class);
                vaiProFormulario.putExtra("filme", filme);
                startActivity(vaiProFormulario);
            }
        });

        totalAssistidos = findViewById(R.id.main_assistidos);
        percentualAssistidos = findViewById(R.id.main_percentual_assistido);
        metaDoAno = findViewById(R.id.main_meta);
        spinnerAnoMeta = findViewById(R.id.main_spinner_ano_meta);

        preencheListaAnoMeta();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.adapter_spinner_ano_meta, listaAnoMeta);
        spinnerAnoMeta.setAdapter(adapter);

//        anoAtual = 2017;
//        metaAtual = 50;

        //captura o clique do spinner que altera o ano
        spinnerAnoMeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AnoMeta anoMeta = new FilmeDAO(MainActivity.this)
                        .buscaAnoMeta().get(i);
                atualizaAnoMeta(anoMeta);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        atualizaAnoMeta();
        atualizaLista();
        atualizaDadosCabecalho();
        registerForContextMenu(listaFilmesAssistidos);
    }

    private void atualizaAnoMeta(AnoMeta anoMeta) {
        anoAtual = anoMeta.getAno();
        metaAtual = anoMeta.getMeta();
        atualizaLista();
        atualizaDadosCabecalho();
    }

    private void preencheListaAnoMeta() {
        FilmeDAO dao = new FilmeDAO(this);
        for (AnoMeta anoMeta : dao.buscaAnoMeta()) {
            listaAnoMeta.add(anoMeta.toString());
        }
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
            AnoMeta anoMetaAux = new AnoMeta();
            anoMetaAux.setAno(anoAtual);
            anoMetaAux.setMeta(metaAtual);
            for (int i = 0; i < dao.buscaAnoMeta().size(); i++) {
                if (dao.buscaAnoMeta().get(i).equals(anoMetaAux)) {
                    spinnerAnoMeta.setSelection(i);
                }
            }

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
        listaFilmesAssistidos.setAdapter(new FilmesAdapter(filmes, this));
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
                Intent vaiParaAddFilme = new Intent(this, AddFilmeActivity.class);
                startActivity(vaiParaAddFilme);
//                FilmeDAO dao = new FilmeDAO(this);
//
//                FilmesAssistidos filme = new FilmesAssistidos();
//                filme.setPosAno(22);
//                dao.deletaFilmeAssistidoErrado(80);
//                filme.setDataAno(2017);
//                filme.setDataMes(4);
//                filme.setDataDia(10);
//                filme.setPosAno(3);
//                filme.setImdbID("tt1921064");
//                filme.setId(93);
//                filme.setInedito(1);
//                new FilmeDAO(this).alteraFilmeAssistido(filme);
//
//                Toast.makeText(this, "Apagou", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.menu_main_filmes:
//                Intent vaiParaListaFilmes = new Intent(this, ListaFilmesActivity.class);
//                startActivity(vaiParaListaFilmes);
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        atualizaLista();
        atualizaDadosCabecalho();
        super.onResume();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final FilmesAssistidos filme = (FilmesAssistidos) listaFilmesAssistidos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                FilmeDAO dao = new FilmeDAO(MainActivity.this);
                dao.deletaFilmeAssistido(filme);
                dao.close();
                atualizaLista();

                return false;
            }
        });
    }
}
