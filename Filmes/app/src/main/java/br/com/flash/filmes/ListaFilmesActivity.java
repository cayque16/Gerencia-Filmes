package br.com.flash.filmes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.models.Filme;

public class ListaFilmesActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        lista = findViewById(R.id.lista_filmes_lista);
        ArrayAdapter<Filme> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new FilmeDAO(this).buscaFilmes());
        lista.setAdapter(adapter);
        Toast.makeText(this, Integer.toString(new FilmeDAO(this).buscaFilmes().size()), Toast.LENGTH_SHORT).show();
    }
}
