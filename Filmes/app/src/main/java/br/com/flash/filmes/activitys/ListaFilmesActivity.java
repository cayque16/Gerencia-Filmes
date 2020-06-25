package br.com.flash.filmes.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.flash.filmes.R;
import br.com.flash.filmes.activitys.add.AddFilmeActivity;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;

public class ListaFilmesActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        lista = findViewById(R.id.lista_filmes_lista);
        atualizaLista();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Filme filme = (Filme) lista.getItemAtPosition(i);
                Intent vaiProFormulario = new Intent(
                        ListaFilmesActivity.this, AddFilmeActivity.class);
                vaiProFormulario.putExtra("filme", filme);
                startActivity(vaiProFormulario);
            }
        });
    }

    private void atualizaLista() {
        ArrayAdapter<Filme> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new FilmeDAO(this).buscaFilmes());
        lista.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_filmes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_lista_filmes_add:
                Intent vaiParaAddFilme = new Intent(this, AddFilmeActivity.class);
                startActivity(vaiParaAddFilme);
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
