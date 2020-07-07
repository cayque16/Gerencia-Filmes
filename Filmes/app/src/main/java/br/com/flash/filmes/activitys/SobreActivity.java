package br.com.flash.filmes.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.flash.filmes.R;

public class SobreActivity extends AppCompatActivity {

    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Sobre");

        tvEmail = findViewById(R.id.sobre_email);

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaEmail(getString(R.string.email_contato));
            }
        });
    }

   public void vaiParaEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        email = "mailto:"+Uri.encode(email) +
                "?subject=" + Uri.encode("My Movie");
        Uri uri = Uri.parse(email);
        emailIntent.setData(uri);
        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
