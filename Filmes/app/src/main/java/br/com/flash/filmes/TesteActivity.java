package br.com.flash.filmes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.flash.filmes.dao.AnoMetaDAO;
import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.SuperModel;

public class TesteActivity extends AppCompatActivity {

    EditText edtAno, edtMeta;
    ListView lista;
    Button btnSalvar;
    private AnoMetaDAO anoMetaDAO = new AnoMetaDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        edtAno = findViewById(R.id.teste_edt_ano);
        edtMeta = findViewById(R.id.teste_edt_meta);
        lista = findViewById(R.id.teste_list);
        btnSalvar = findViewById(R.id.teste_btn_salvar);

        atualizaLista();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturaForm();
            }
        });


    }

    private void capturaForm() {
        AnoMeta anoMeta = new AnoMeta();

        anoMeta.setAno(Integer.parseInt(edtAno.getText().toString()));
        anoMeta.setMeta(Integer.parseInt(edtMeta.getText().toString()));

        anoMetaDAO.insere(anoMeta);

        edtMeta.setText("");
        edtAno.setText("");

        atualizaLista();

        Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
    }

    private void atualizaLista() {
        List<SuperModel> teste = new AnoMetaDAO(this).buscaTodos();
        ArrayAdapter<SuperModel> arrayAdapter = new ArrayAdapter<SuperModel>(
                this,
                android.R.layout.simple_list_item_1,
                teste
        );
        lista.setAdapter(arrayAdapter);
    }
}
