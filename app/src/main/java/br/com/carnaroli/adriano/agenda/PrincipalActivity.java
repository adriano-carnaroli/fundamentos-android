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
import java.util.List;


public class PrincipalActivity extends AppCompatActivity {

    public static final String TAG = PrincipalActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        List<Client> clients = getClients();

        ListView listViewClients = (ListView) findViewById(R.id.listViewClients);

        ClientListAdapter clientAdapter = new ClientListAdapter(PrincipalActivity.this, clients);

        listViewClients.setAdapter(clientAdapter);

    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        Client renan = new Client();
        renan.setName("Renan");
        renan.setAge(23);

        Client valdeco = new Client();
        valdeco.setName("Valdeco");
        renan.setAge(26);

        clients.add(renan);
        clients.add(valdeco);

        return clients;
    }

}
