package br.com.carnaroli.adriano.agenda;

import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Principal extends AppCompatActivity {

    public static final String TAG = Principal.class.getSimpleName();
    public List<String> nomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        nomes = new ArrayList<>();
        Button botao = (Button) findViewById(R.id.botao);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editNome = (EditText) findViewById(R.id.nome);
                nomes.add(editNome.getText().toString());
                Toast.makeText(Principal.this, "Nome Digitado: " + editNome.getText().toString(), Toast.LENGTH_LONG).show();
                editNome.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView label = (TextView) findViewById(R.id.textView);
        String juncaoNomes = "";
        for (String nome : nomes){
            juncaoNomes += nome+"; ";
        }
        label.setText(juncaoNomes);
    }
}
