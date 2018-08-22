package br.com.flash.filmes.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.flash.filmes.R;
import br.com.flash.filmes.RetrofitInicializador;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.dto.Movie;
import br.com.flash.filmes.helper.FormularioFilmeHelper;
import br.com.flash.filmes.models.Filme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFilmeActivity extends AppCompatActivity {

    private FormularioFilmeHelper helper;
    private TextView imdb;
    private String chave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);

        imdb = findViewById(R.id.add_filme_imdbID);

        Intent intent = getIntent();
        Filme filme = (Filme) intent.getSerializableExtra("filme");

        helper = new FormularioFilmeHelper(this);

        if (filme != null) {
            helper.preencheFormulario(filme);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_filme, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_filme_ok:
                Filme filme = helper.pegaFilme();

                FilmeDAO dao = new FilmeDAO(this);

                if (filme.getId() != null) {
                    dao.alteraFilme(filme);
                } else {
                    dao.insereFilme(filme);
                }
                dao.close();

                Toast.makeText(this, "Filme " + filme.getTitulo() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buscaIMDB(View view) {
        chave = imdb.getText().toString();
        Call<Movie> call = new RetrofitInicializador().getFilmeService().buscaFilme(chave);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Filme filme = response.body().getFilme();

                FormularioFilmeHelper helper = new FormularioFilmeHelper(AddFilmeActivity.this);
                helper.preencheFormulario(filme);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(AddFilmeActivity.this, "Não foi possível carregar os dados!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
