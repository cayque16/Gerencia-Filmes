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
import br.com.flash.filmes.converter.FilmeConverter;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.dto.FilmeBd;
import br.com.flash.filmes.dto.Movie;
import br.com.flash.filmes.helper.FormularioFilmeAssistidoHelper;
import br.com.flash.filmes.helper.FormularioFilmeHelper;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;
import br.com.flash.filmes.preferences.TokenPreferences;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import br.com.flash.filmes.retrofit.RetrofitInicializadorFilmes;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFilmeActivity extends AppCompatActivity {

    private FormularioFilmeHelper helperFilme;
    private FormularioFilmeAssistidoHelper helperFilmeAssistido;
    private TextView imdb;
    private String chave;
    private FilmesAssistidos filmesAssistidos = new FilmesAssistidos();
    private TokenPreferences tokenPreferences = new TokenPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);

        imdb = findViewById(R.id.add_filme_imdbID);

        Intent intent = getIntent();
        filmesAssistidos = (FilmesAssistidos) intent.getSerializableExtra("filme");

        helperFilme = new FormularioFilmeHelper(this);
        helperFilmeAssistido = new FormularioFilmeAssistidoHelper(this);

        if (filmesAssistidos != null) {

            Call<FilmeBd> call = new RetrofitInicializadorBd().getBdService().getFilmeById(filmesAssistidos.getFilme().getId(),tokenPreferences.getToken());

            call.enqueue(new Callback<FilmeBd>() {
                @Override
                public void onResponse(Call<FilmeBd> call, Response<FilmeBd> response) {
                    helperFilmeAssistido.preencheFormulario(filmesAssistidos);
                    helperFilme.preencheFormulario(response.body().getFilme());
                }

                @Override
                public void onFailure(Call<FilmeBd> call, Throwable t) {

                }
            });
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
                Filme filme = helperFilme.pegaFilme();
                FilmesAssistidos filmeAssistido = helperFilmeAssistido.pegaFilmeAssistido();

                if ((filme == null) || (filmeAssistido == null)) {
                    Toast.makeText(AddFilmeActivity.this, "Erro!!! Confira os dados.", Toast.LENGTH_SHORT).show();
                    break;
                }

                String json = new FilmeConverter().convertParaJson(filme,filmeAssistido);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

                Call<ResponseBody> call = new RetrofitInicializadorBd().getBdService().insereFilmeAssistido(requestBody,tokenPreferences.getToken());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            Toast.makeText(AddFilmeActivity.this, "Filme salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buscaIMDB(View view) {
        chave = imdb.getText().toString();

        if (!new FilmeDAO(this).existeFilme(chave)) {

            Call<Movie> call = new RetrofitInicializadorFilmes().getFilmeService().buscaFilme(chave);

            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    String resposta = response.body().getResponse();
                    if (resposta.equals("True")) {
                        Filme filme = response.body().getFilme();

                        FormularioFilmeHelper helper = new FormularioFilmeHelper(AddFilmeActivity.this);
                        helper.preencheFormulario(filme);
                    } else {
                        Toast.makeText(AddFilmeActivity.this, "Filme não encontrado!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(AddFilmeActivity.this, "Não foi possível carregar os dados!!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            FormularioFilmeHelper helper = new FormularioFilmeHelper(AddFilmeActivity.this);
            helper.preencheFormulario(new FilmeDAO(this).retornaUmFilme(chave));
//            helper.campoInedito.setText(R.string.jaVisto);
//            new FormularioFilmeAssistidoHelper(AddFilmeActivity.this).campoInedito.setChecked(false);
//            new FormularioFilmeAssistidoHelper(AddFilmeActivity.this).campoInedito.setEnabled(false);
        }
    }
}
