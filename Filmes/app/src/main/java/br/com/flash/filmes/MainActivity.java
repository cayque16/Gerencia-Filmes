package br.com.flash.filmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.flash.filmes.adapters.FilmesAdapter;
import br.com.flash.filmes.add.AddFilmeActivity;
import br.com.flash.filmes.add.AddFilmeAssistidoActivity;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

public class MainActivity extends AppCompatActivity {

    private List<FilmesAssistidos> filmes;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.main_lista);

        atualizaLista();
    }

    private void atualizaLista() {
        filmes = new FilmeDAO(this).buscaFilmesAssistidos();
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
//                dao.deletaFilmeAssistidoErrado(55);
//                Toast.makeText(this, "Apagou", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_main_ano:
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
        super.onResume();
    }
}
