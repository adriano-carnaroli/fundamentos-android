package br.com.carnaroli.adriano.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PrincipalActivity extends AppCompatActivity {

    public static final String TAG = PrincipalActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ArrayList<Object> clientNames = new ArrayList<>();
        clientNames.add("Renan");
        clientNames.add("Venilton");
        clientNames.add("Valdeco");
        clientNames.add("Robinho");

        ListView listViewClients = (ListView) findViewById(R.id.listViewClients);

        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(PrincipalActivity.this,
                android.R.layout.simple_list_item_1,
                clientNames.toArray(new String[]{}));

        listViewClients.setAdapter(clientAdapter);

    }

}
