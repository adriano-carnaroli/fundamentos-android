package br.com.carnaroli.adriano.agenda.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.carnaroli.adriano.agenda.R;
import br.com.carnaroli.adriano.agenda.model.entities.Client;
import br.com.carnaroli.adriano.agenda.model.entities.User;
import br.com.carnaroli.adriano.agenda.model.services.AuthenticationService;
import br.com.carnaroli.adriano.agenda.util.FormHelper;

/**
 * Created by Administrador on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText edtUsername;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindFields();
        bindLoginButton();
    }

    private void bindFields() {
        edtUsername = (EditText) findViewById(R.id.editTextUserName);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private User bindUser(){
        User user = new User();
        user.setUsername(edtUsername.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        return user;
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.butonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FormHelper.requiredValidate(LoginActivity.this, edtUsername, edtPassword)) {
                    if(AuthenticationService.isAuthenticationValid(bindUser())){
                        Intent goToPrincipal = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(goToPrincipal);
                    }else{
                        Toast.makeText(LoginActivity.this, getString(R.string.messageAuthenticationError), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}
