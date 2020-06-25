package br.com.flash.filmes.activitys.add;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.flash.filmes.R;
import br.com.flash.filmes.helper.FormularioFilmeAssistidoHelper;

public class AddFilmeAssistidoActivity extends AppCompatActivity {

    private FormularioFilmeAssistidoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme_assistido);

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

            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
