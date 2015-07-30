package br.com.carnaroli.adriano.agenda.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private EditText editTextTipoDeLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
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
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
        editTextAge = (EditText) findViewById(R.id.edtTextAge);
        editTextAddress = (EditText) findViewById(R.id.edtTextAddress);
        editTextPhone = (EditText) findViewById(R.id.edtTextPhone);
        editTextCep = (EditText) findViewById(R.id.edtTextCep);
        editTextTipoDeLogradouro = (EditText) findViewById(R.id.edtTextTipoDeLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.edtTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.edtTextBairro);
        editTextCidade = (EditText) findViewById(R.id.edtTextCidade);
        editTextEstado = (EditText) findViewById(R.id.edtTextEstado);
        bindButtonFindCep();
    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

            if (FormHelper.requiredValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextAddress, editTextPhone,
                    editTextCep, editTextTipoDeLogradouro, editTextLogradouro, editTextBairro, editTextCidade, editTextEstado)) {
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
        client.setBairro(editTextBairro.getText().toString());
        client.setCep(editTextCep.getText().toString());
        client.setCidade(editTextCidade.getText().toString());
        client.setEstado(editTextEstado.getText().toString());
        client.setLogradouro(editTextLogradouro.getText().toString());
        client.setTipoDeLogradouro(editTextTipoDeLogradouro.getText().toString());
        return client;
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextAddress.setText(client.getAddress());
        editTextPhone.setText(client.getPhone());
        editTextTipoDeLogradouro.setText(client.getTipoDeLogradouro());
        editTextLogradouro.setText(client.getLogradouro());
        editTextBairro.setText(client.getBairro());
        editTextCidade.setText(client.getCidade());
        editTextEstado.setText(client.getEstado());
        editTextCep.setText(client.getCep());
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress>{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            editTextCidade.setText(clientAddress.getCidade().toString());
            editTextBairro.setText(clientAddress.getBairro().toString());
            editTextLogradouro.setText(clientAddress.getLogradouro().toString());
            editTextTipoDeLogradouro.setText(clientAddress.getTipoDeLogradouro().toString());
            editTextEstado.setText(clientAddress.getEstado().toString());

            progressDialog.dismiss();
        }
    }

}
