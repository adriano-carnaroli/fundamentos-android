package br.com.carnaroli.adriano.agenda.model.persistence;

import java.util.List;

import br.com.carnaroli.adriano.agenda.model.entities.Client;

/**
 * Created by Administrador on 21/07/2015.
 */
public interface ClientRepository {

    public abstract void save(Client client);
    public abstract List<Client> getAll();
    public abstract void delete(Client client);


}
