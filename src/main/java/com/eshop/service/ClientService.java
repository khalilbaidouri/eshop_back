package com.eshop.service;

import com.eshop.entity.Client;
import com.eshop.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author $ {USERS}
 **/
@Service
public class ClientService {
    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public List<Client> findAll() {
        return repo.findAll();
    }

    public Client save(Client client) {
        return repo.save(client);
    }
}