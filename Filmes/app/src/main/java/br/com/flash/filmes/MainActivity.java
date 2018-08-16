package br.com.flash.filmes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.com.flash.filmes.adapters.FilmesAdapter;
import br.com.flash.filmes.models.FilmesAssistidos;

public class MainActivity extends AppCompatActivity {

    private ArrayList<FilmesAssistidos> filmes;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.main_lista);

        filmes = new ArrayList();
        filmes.add(new FilmesAssistidos("tt5362988", 1, 1, 8,1,2018));
        filmes.add(new FilmesAssistidos("tt1843866", 0, 10, 5,1,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 1, 11, 15,8,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 1, 100, 15,8,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 1, 101, 15,8,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 0, 50, 15,8,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 0, 55, 15,8,2018));
        filmes.add(new FilmesAssistidos("tt4881806", 0, 999, 15,8,2018));

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
                Toast.makeText(this, "Adicionar!", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
