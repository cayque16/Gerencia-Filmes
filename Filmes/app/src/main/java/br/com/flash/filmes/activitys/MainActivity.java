package br.com.flash.filmes.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import br.com.flash.filmes.R;
import br.com.flash.filmes.adapters.FilmesAdapter;
import br.com.flash.filmes.activitys.add.AddFilmeActivity;
import br.com.flash.filmes.converter.AnoMetaConverter;
import br.com.flash.filmes.dto.AnoMetaBd;
import br.com.flash.filmes.dto.FilmeAssistidoBd;
import br.com.flash.filmes.models.AnoMeta;
import br.com.flash.filmes.models.FilmesAssistidos;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SuperActivity {

    private List<FilmesAssistidos> filmes = new ArrayList<>();
    private ListView listaFilmesAssistidos;
    private TextView totalAssistidos, percentualAssistidos;
    private TextView metaDoAno;
    private AnoMeta anoMetaAtual = new AnoMeta();
    private ArrayList<AnoMeta> listaAnoMeta = new ArrayList<AnoMeta>();
    private Spinner spinnerAnoMeta;
    private Dialog dialog;
    private SwipeRefreshLayout swipeMain;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token.setToken(getTokenRefresh());
        swipeMain = findViewById(R.id.swipe_lista_main);

        swipeMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                atualizaLista();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeMain.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        listaFilmesAssistidos = findViewById(R.id.main_lista);

        listaFilmesAssistidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FilmesAssistidos filme = filmes.get(i);
                Intent vaiProFormulario = new Intent(
                        MainActivity.this, AddFilmeActivity.class);
                vaiProFormulario.putExtra("filme", filme);
                startActivity(vaiProFormulario);
            }
        });

        totalAssistidos = findViewById(R.id.main_assistidos);
        percentualAssistidos = findViewById(R.id.main_percentual_assistido);
        metaDoAno = findViewById(R.id.main_meta);
        spinnerAnoMeta = findViewById(R.id.main_spinner_ano_meta);
        btnAdd = findViewById(R.id.main_btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddFilmeActivity.class));
            }
        });

        dialog = new Dialog(this);
        dialog = criaDialog();
        atualizaAnoMeta();

        registerForContextMenu(listaFilmesAssistidos);
    }

    private void criaSpinnerAnoMeta() {
        preencheListaAnoMeta();
        ArrayAdapter<AnoMeta> adapter = new ArrayAdapter<AnoMeta>(this, R.layout.adapter_spinner_ano_meta, listaAnoMeta);
        spinnerAnoMeta.setAdapter(adapter);

        //captura o clique do spinner que altera o ano
        spinnerAnoMeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AnoMeta anoMeta = listaAnoMeta.get(i);
                atualizaAnoMeta(anoMeta);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private Dialog criaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_meta_ano, null))
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText valorInserido = dialog.findViewById(R.id.dialog_add_meta_valor);
                        int valorMetaNova;
                        String mensagemErro = "Valor inválido, sem alteração!";
                        try {
                            valorMetaNova = Integer.valueOf(valorInserido.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (valorMetaNova <= 0) {
                            Toast.makeText(MainActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                        } else if (valorMetaNova > 999) {
                            Toast.makeText(MainActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                        } else {
                            anoMetaAtual.setMeta(valorMetaNova);
                            String json = new AnoMetaConverter().convertParaJson(anoMetaAtual);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

                            Call<ResponseBody> call = new RetrofitInicializadorBd().getBdService().alteraAnoMeta(requestBody, token.getToken());

                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        atualizaAnoMeta(anoMetaAtual);
                                        criaSpinnerAnoMeta();
                                        Toast.makeText(MainActivity.this, "Meta alterada!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }

                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    private void preencheListaAnoMeta() {
        listaAnoMeta.clear();
        Call<List<AnoMetaBd>> call = new RetrofitInicializadorBd().getBdService().buscaAnosMeta(token.getToken());

        call.enqueue(new Callback<List<AnoMetaBd>>() {
            @Override
            public void onResponse(Call<List<AnoMetaBd>> call, Response<List<AnoMetaBd>> response) {
                for (AnoMetaBd i : response.body()) {
                    listaAnoMeta.add(i.getAnoMeta());
                }
                ArrayAdapter<AnoMeta> adapter = new ArrayAdapter<AnoMeta>(MainActivity.this, R.layout.adapter_spinner_ano_meta, listaAnoMeta);
                spinnerAnoMeta.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AnoMetaBd>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizaAnoMeta(AnoMeta anoMeta) {
        anoMetaAtual = anoMeta;
        atualizaLista();
    }

    private void atualizaAnoMeta() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        final AnoMeta[] anoMetaAux = {new AnoMeta()};
        Call<AnoMetaBd> call = new RetrofitInicializadorBd().getBdService().getAnoMeta(calendar.get(Calendar.YEAR), token.getToken());

        anoMetaAtual.setAno(calendar.get(Calendar.YEAR));

        call.enqueue(new Callback<AnoMetaBd>() {
            @Override
            public void onResponse(Call<AnoMetaBd> call, Response<AnoMetaBd> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.body().getAnoMeta() != null) {
                        anoMetaAtual = anoMetaAux[0] = response.body().getAnoMeta();
                        criaSpinnerAnoMeta();
                        atualizaLista();
                    }
                }
            }

            @Override
            public void onFailure(Call<AnoMetaBd> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizaLista() {

        Call<List<FilmeAssistidoBd>> call = new RetrofitInicializadorBd().getBdService().buscaListaFilmesAssistidos(anoMetaAtual.getAno(), token.getToken());

        call.enqueue(new Callback<List<FilmeAssistidoBd>>() {
            @Override
            public void onResponse(Call<List<FilmeAssistidoBd>> call, Response<List<FilmeAssistidoBd>> response) {
                filmes.clear();
                if (response.body() != null) {
                    for (FilmeAssistidoBd i : response.body()) {
                        filmes.add(i.getFilmeAssistido());
                    }
                }
                listaFilmesAssistidos.setAdapter(new FilmesAdapter(filmes, MainActivity.this));
                //ATUALIZA O CABECALHO
                int quantidadeAssistidos = filmes.size();
                float percentual = (quantidadeAssistidos / (float) anoMetaAtual.getMeta()) * 100;
                DecimalFormat df = new DecimalFormat("0.00");
                totalAssistidos.setText(Integer.toString(quantidadeAssistidos));
                percentualAssistidos.setText("(" + df.format(percentual) + "%)");
                metaDoAno.setText(Integer.toString(anoMetaAtual.getMeta()));
            }

            @Override
            public void onFailure(Call<List<FilmeAssistidoBd>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
            }
        });
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
            case R.id.menu_main_altera_meta:
                dialog.show();
                return true;
            case R.id.menu_main_sair:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Atenção!!!");
                builder.setMessage("Tem certeza que deseja sair?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        filmesPreferences.limparPreferences();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alerta = builder.create();
                alerta.show();
                return true;
            case R.id.menu_main_sobre:
                startActivity(new Intent(this, SobreActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        atualizaLista();
        super.onResume();
    }

    //CRIA UM MENU AO PRESSIONAR E SEGURAR UM ITEM DA LISTA
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //captura o filme que foi clicado
        final FilmesAssistidos filme = (FilmesAssistidos) listaFilmesAssistidos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        MenuItem informacoes = menu.add("Informacoes");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        informacoes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
    }
}
