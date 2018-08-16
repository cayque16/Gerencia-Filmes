package br.com.flash.filmes.add;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.flash.filmes.R;
import br.com.flash.filmes.dao.FilmeDAO;
import br.com.flash.filmes.helper.FormularioFilmeAssistidoHelper;
import br.com.flash.filmes.models.Filme;
import br.com.flash.filmes.models.FilmesAssistidos;

public class AddFilmeAssistidoActivity extends AppCompatActivity {

    private FormularioFilmeAssistidoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme_assistido);

        helper = new FormularioFilmeAssistidoHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_filme_assistido, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_filme_assistido_ok:
                FilmesAssistidos filmesAssistidos = helper.pegaFilmeAssistido();

                FilmeDAO dao = new FilmeDAO(this);

                dao.insereFilmeAssistido(filmesAssistidos);
                dao.close();

                Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
