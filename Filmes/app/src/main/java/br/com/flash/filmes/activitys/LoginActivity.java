package br.com.flash.filmes.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;

import br.com.flash.filmes.R;
import br.com.flash.filmes.models.Login;
import br.com.flash.filmes.models.Token;
import br.com.flash.filmes.retrofit.RetrofitInicializadorBd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends SuperActivity  {

    private EditText edtPassword,edtUsername;
    private View progressBar, scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.login_username);
        edtPassword = findViewById(R.id.login_password);
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    realizaLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnEntrar = findViewById(R.id.login_button_entrar);
        btnEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                realizaLogin();
            }
        });

        scrollView = findViewById(R.id.login_form_scroll);
        progressBar = findViewById(R.id.login_progressbar);
    }

    private void realizaLogin() {
        // Reset errors.
        edtUsername.setError(null);
        edtPassword.setError(null);

        // Pega os valores dos campos
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Testa se foi digitada uma senha e se a mesma é válida.
        if (!isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            focusView = edtPassword;
            cancel = true;
        }

        // Testa se foi digitado um nome de usuário e se o mesmo é válido
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError(getString(R.string.error_field_required));
            focusView = edtUsername;
            cancel = true;
        }

        if (cancel) {
            // Algum erro ocorreu, o primeiro campo com
            // erro vai receber o foco.
            focusView.requestFocus();
        } else {
            // Exibe um progress bar, e inicia uma tarefa em
            // segundo plano com a tentativa de login.
            showProgress(true);
            realizaLogin(new Login(username, password));
        }
    }

    private void realizaLogin(Login login) {
        Call<Token> call = new RetrofitInicializadorBd().getBdService().getToken(login);

        call.enqueue(new Callback<Token>() {
//            private final Context context = getBaseContext();

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    filmesPreferences.setToken(response.body().getTokenJwt());
                    filmesPreferences.setLogin(new Login(edtUsername.getText().toString(),edtPassword.getText().toString()));
                    showProgress(false);
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    getAlertaErroAutenticacao();
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Não foi possível connectar!!!", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }

    private void getAlertaErroAutenticacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro!!!");
        builder.setMessage("Não foi possível realizar login com as credenciais " +
                "fornecidas!!!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            scrollView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

