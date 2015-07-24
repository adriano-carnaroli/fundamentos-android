package br.com.carnaroli.adriano.agenda.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.carnaroli.adriano.agenda.R;
import br.com.carnaroli.adriano.agenda.model.entities.Client;


public class ClientListActivity extends AppCompatActivity {

    private ListView listViewClients;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        bindClientList();
    }

    private void bindClientList() {
        List<Client> clients = getClients();
        listViewClients = (ListView) findViewById(R.id.listViewClients);
        ClientListAdapter clientAdapter = new ClientListAdapter(ClientListActivity.this, clients);
        listViewClients.setAdapter(clientAdapter);

        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });

        registerForContextMenu(listViewClients);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setClients(Client.getAll());
        adapter.notifyDataSetChanged();
    }

    public List<Client> getClients() {
        return Client.getAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_clients, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent goToPrincipal = new Intent(ClientListActivity.this, ClientPersistActivity.class);
        startActivity(goToPrincipal);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuEdit){
            Intent goToClientPersist = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            goToClientPersist.putExtra(ClientPersistActivity.CLIENT_PARAM, (Parcelable) client);
            startActivity(goToClientPersist);
        }else if(item.getItemId() == R.id.menuDelete){

            new AlertDialog.Builder(ClientListActivity.this)
                .setMessage(getString(R.string.confirm_progress))
                .setTitle(getString(R.string.confirm))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client.delete();
                        refreshClientList();
                        Toast.makeText(ClientListActivity.this, R.string.msgSuccess, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                })
                .create()
                .show();
        }
        return super.onContextItemSelected(item);
    }
}
