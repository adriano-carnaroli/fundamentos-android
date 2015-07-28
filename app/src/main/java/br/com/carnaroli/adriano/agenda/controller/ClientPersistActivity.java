package br.com.carnaroli.adriano.agenda.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.carnaroli.adriano.agenda.R;
import br.com.carnaroli.adriano.agenda.model.entities.Client;
import br.com.carnaroli.adriano.agenda.model.entities.ClientAddress;
import br.com.carnaroli.adriano.agenda.model.services.CepService;
import br.com.carnaroli.adriano.agenda.util.FormHelper;

/**
 * Created by Administrador on 21/07/2015.
 */
public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextCep;
    private Button btnFindCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_persist);

        bindFields();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            client = (Client) extras.getParcelable(CLIENT_PARAM);
            if(client == null){
                throw new IllegalArgumentException();
            }
            bindForm(client);
        }
    }

    private void bindFields() {
        editTextName = (EditText) findViewById(R.id.edtTextName);
        editTextAge = (EditText) findViewById(R.id.edtTextAge);
        editTextAddress = (EditText) findViewById(R.id.edtTextAddress);
        editTextPhone = (EditText) findViewById(R.id.edtTextPhone);
        editTextCep = (EditText) findViewById(R.id.edtTextCep);
        bindButtonFindCep();
    }

    private void bindButtonFindCep() {
        btnFindCep = (Button) findViewById(R.id.buttonFindCep);
        btnFindCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAddressByCep().execute(editTextCep.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {

            if (FormHelper.requiredValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextAddress, editTextPhone)) {
                Client client = bindClient();
                client.save();
                Toast.makeText(ClientPersistActivity.this, R.string.msgSuccess, Toast.LENGTH_LONG).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Client bindClient(){
        if(client == null){
            client = new Client();
        }
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setAddress(editTextAddress.getText().toString());
        client.setPhone(editTextPhone.getText().toString());
        return client;
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextAddress.setText(client.getAddress());
        editTextPhone.setText(client.getPhone());
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
